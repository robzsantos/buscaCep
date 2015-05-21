package com.teste.buscacep;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;

import com.teste.buscacep.R;

@SuppressLint({ "InlinedApi", "InflateParams" })
public class MainActivity extends ActionBarActivity
{
	private GUIText userInterfaceText;
	private GUIButtons userInterfaceButtons;
	private CEPDatabase cepDatabase;
	private PopUpView popUpView;
	private StorageAppData storage;
		
	public static final String TAG = "BuscaCEP";
	
	private boolean enableSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		//Log.v(TAG, "onCreate() executado.");
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_main);				
		
		userInterfaceText = new GUIText(this);
		userInterfaceButtons = new GUIButtons(this,userInterfaceText);
		cepDatabase = new CEPDatabase();
		popUpView = new PopUpView(this);
		enableSearch = true;
		storage = new StorageAppData(this);
		storage.openFile("data");
		storage.writeInteger("screenWidth", getWidthWindow());
		updateDataScreen();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) 
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void requestCEP()
	{
		String cep = userInterfaceText.get_mEdtCep().getText().toString().replace("-", "");
				
		GetJsonAsyncTask getJson = new GetJsonAsyncTask(this);
		getJson.execute("http://correiosapi.apphb.com/cep/"+cep);
		enableSearch = false;
	}
	
	public void dispatchJSONMessages(String text)
	{
		userInterfaceText.showMessage(text);
	}
		
	public void showHistoric()
	{
		String historic = storage.readStringValue("popup");
		if (historic != "" && historic.contains("CEP: "))
		{
			String[] valueSeparated = historic.split("CEP: ");
			for(int i=1;i<valueSeparated.length;i++)
			{
				String[] cepSeparated = valueSeparated[i].split("\n");
				cepDatabase.addDatabase(cepSeparated[0], cepSeparated[1]);
			}
			storage.writeString("popup","");
		}
		popUpView.populatePopUp(cepDatabase.getCepData(),cepDatabase.getAddressData());
		popUpView.showPopUp();	
	}
	
	public void filterJSONResult(String json)
	{
		try 
		{ 
			JSONObject parentObject = new JSONObject(json);
		    if(parentObject.has("message"))
		    {
		    	dispatchJSONMessages("O CEP digitado não foi encontrado.");
		    }
		    else
		    {
				String tipoDeLogradouro = parentObject.getString("tipoDeLogradouro");
				String logradouro = parentObject.getString("logradouro");
				String bairro = parentObject.getString("bairro");
				String cidade = parentObject.getString("cidade");
				String estado = parentObject.getString("estado");
				
				String completeAddress = "Endereço: "+tipoDeLogradouro+" "+logradouro+" - "+bairro+" - "+cidade+" - "+estado;
				
				userInterfaceText.showResultFromJSON(completeAddress);
				cepDatabase.addDatabase(userInterfaceText.get_mEdtCep().getText().toString(),completeAddress);
		    }
		} 
		catch (JSONException e) 
		{ 
			Log.e(TAG, "Erro no parsing do JSON", e); 
		}
	}	
	
	@SuppressLint("NewApi")
	public int getWidthWindow()
	{
		Display display = getWindowManager().getDefaultDisplay();	
		Point size = new Point();
		int width;

		if(android.os.Build.VERSION.SDK_INT >= 13) 
		{
			display.getSize(size);
			width = size.x;
		}
		else
		{
			width = display.getWidth();  // deprecated
		}
		return width;
	}
	
	public boolean get_enableSearch()
	{
		return enableSearch;
	}

	public void set_enableSearch(boolean value)
	{
		enableSearch = value;
	}
	
	@Override
	public void onBackPressed() 
	{
		//Log.e(TAG, "Cliquei em return");
	}
	
	@Override
	protected void onStart() 
	{
		super.onStart();
		//Log.v(TAG, "onStart() executado.");
	}
	@Override
	protected void onResume() 
	{
		super.onResume();
		//Log.v(TAG, "onResume() executado.");
	}
	@Override
	protected void onPause() 
	{
		super.onPause();
		//Log.v(TAG, "onPause() executado.");
	}
	@Override
	protected void onRestart() 
	{
		super.onRestart();
		//Log.v(TAG, "onRestart() executado.");
	}
	@Override
	protected void onStop() 
	{
		super.onStop();
		int currentScreenWidth = getWidthWindow();
		
		if(currentScreenWidth == storage.readIntegerValue("screenWidth"))
			saveData(false);
		else
			saveData(true);
		//Log.v(TAG, "onStop() executado.");
	}
	@Override
	protected void onDestroy() 
	{
		super.onDestroy();	
		//Log.v(TAG, "onDestroy() executado.");
	}
	
	private void updateDataScreen() 
	{
		userInterfaceText.get_mTxtResult().setText(storage.readStringValue("address"));
		userInterfaceText.get_mTxtFeedback().setText(storage.readStringValue("feedback"));
		popUpView.set_mTextPopUp(storage.readStringValue("popup"));
		
		int codeColor = storage.readIntegerValue("txtColorFeedback");
		if(codeColor != -1)
			userInterfaceText.set_mTxtFeedbackColor(codeColor);
		
		if(storage.readBooleanValue("txtResultVisibility"))
			userInterfaceText.set_mTxtResultVisibility(true);
		
		//Log.v(TAG, "V1 = "+storage.readStringValue("address")+" - V2 = "+storage.readStringValue("feedback")+" - V3 = "+storage.readStringValue("popup"));
	}

	private void saveData(boolean changeOrientation)
	{
		String textFeedback = "";
		int textColorFeedback = 0;
		String lastAddress = userInterfaceText.get_mTxtResult().getText().toString();
		boolean lastAddressVisibility = userInterfaceText.get_mTxtResultVisibility();
		String popUpText = popUpView.get_mTextPopUp();
		
		if(changeOrientation)
		{
			textFeedback = userInterfaceText.get_mTxtFeedback().getText().toString();
			textColorFeedback = userInterfaceText.get_mTxtFeedbackCurrentColor();
			//Log.v(TAG, "Salvando cor = "+textColorFeedback);
		}
		
		storage.writeBoolean("txtResultVisibility", lastAddressVisibility);
		storage.writeString("address", lastAddress);
		storage.writeString("feedback", textFeedback);
		storage.writeString("popup", popUpText);
		if(textColorFeedback != 0)
			storage.writeInteger("txtColorFeedback", textColorFeedback);
	}
}