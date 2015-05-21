package com.teste.buscacep;

import android.view.View;
import android.view.View.OnClickListener;

public class GUIElements implements OnClickListener
{
	private MainActivity mContext;
	
	private int mErrorCode;
	
	
	public GUIElements(MainActivity context)
	{
		this.mContext = context;
	}
	
	public void activeListeners() 
	{
		
	}

	public boolean callValidator(String text)
	{
		CEPValidator validator = CEPValidator.getInstance();
		int result = validator.validate(text);
		if(result != CEPValidator.VALID)
		{
			mErrorCode = result;
			return false;
		}
		if(mContext.get_enableSearch())
			mContext.requestCEP();
		return true;
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
	}
	
	//Getters
	public MainActivity get_mContext() 
	{
		return mContext;
	}
	
	public int get_mErrorCode()
	{
		return mErrorCode;
	}	
}
