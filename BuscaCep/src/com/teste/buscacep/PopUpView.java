package com.teste.buscacep;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;


public class PopUpView extends GUIElements
{
	private Button btnDismiss;
	private PopupWindow popupWindow;
	private View popupView;
	private TextView textPopUp;

	private String labelTxtPopup;	
	
	@SuppressLint({ "InlinedApi", "InflateParams" })
	public PopUpView(MainActivity context)
	{
		super(context);
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		popupView = layoutInflater.inflate(R.layout.popup, null);
		popupWindow = new PopupWindow(popupView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		textPopUp = (TextView)popupView.findViewById(R.id.textPopup);
		labelTxtPopup = textPopUp.getText().toString();
		btnDismiss = (Button)popupView.findViewById(R.id.closePopup);
		activeListeners();		
	}
	
	public void populatePopUp(ArrayList<String> cepList, ArrayList<String> adrressList)
	{
		int limit = cepList.size();
		String newTextPopUp = "";
		
		for(int i = 0; i < limit; i++) 
		{
			newTextPopUp = newTextPopUp +"\nCEP: "+ cepList.get(i)+"\n"+adrressList.get(i)+"\n";
		}
		
		textPopUp.setText(labelTxtPopup +"\n"+ newTextPopUp);
	}
	
	public void showPopUp()
	{
		popupWindow.showAsDropDown((Button) super.get_mContext().findViewById(R.id.btnHistorico), 50, -30);
	}
	
	@Override
	public void activeListeners() 
	{
		btnDismiss.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) 
	{
		popupWindow.dismiss();
	}
	
	//Getters
	public String get_mTextPopUp()
	{
		return textPopUp.getText().toString();
	}
	
	//Setters
	public void set_mTextPopUp(String text)
	{
		textPopUp.setText(text);
	}
}