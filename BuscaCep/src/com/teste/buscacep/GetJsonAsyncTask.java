package com.teste.buscacep;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

@SuppressLint("NewApi")
public class GetJsonAsyncTask extends AsyncTask<String, Void, String> 
{
	private MainActivity activity;

	public GetJsonAsyncTask(MainActivity activity)
	{
		this.activity = activity;
	}
	
	@Override protected void onPreExecute() 
	{ 
		super.onPreExecute();
		activity.dispatchJSONMessages("Aguarde, enquanto é feita a consulta...");
	}

	@Override
	protected String doInBackground(String... params) 
	{
		String urlString = params[0]; 
		
		HttpClient httpclient = new DefaultHttpClient(); 
		HttpGet httpget = new HttpGet(urlString); 
		try 
		{ 
			HttpResponse response = httpclient.execute(httpget); 
			HttpEntity entity = response.getEntity(); 
			if (entity != null) 
			{ 
				InputStream instream = entity.getContent(); 
				String json = toString(instream); 
				instream.close(); 
				return json; 
			} 
		} 
		catch (Exception e) 
		{
		} 
		return "404";
	}
	
	
	@Override 
	protected void onPostExecute(String result) 
	{ 
		super.onPostExecute(result); 
		if (result.length() != 0 && result.compareTo("404") != 0) 
		{ 
			//Log.v(MainActivity.TAG, "Conteúdo foi coletado do JSON!");
			activity.dispatchJSONMessages("");
			activity.filterJSONResult(result);
		} 
		else if(result.compareTo("404") == 0) 
		{ 
			activity.dispatchJSONMessages("Falha de conexão, verifique se seu aparelho está conectado a internet corretamente");
		} 
		else
		{
			activity.dispatchJSONMessages("Serviço indisponível, tente mais tarde.");
		}
		activity.set_enableSearch(true);
	}
	
	private String toString(InputStream is) throws IOException 
	{ 
		byte[] bytes = new byte[1024]; 
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
		int lidos; 
		while ((lidos = is.read(bytes)) > 0) 
		{ 
			baos.write(bytes, 0, lidos); 
		} 
		return new String(baos.toByteArray()); 
	}
}
