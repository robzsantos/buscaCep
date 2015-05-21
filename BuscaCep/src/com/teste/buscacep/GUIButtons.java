package com.teste.buscacep;

import com.teste.buscacep.R;

import android.view.View;
import android.widget.Button;

public class GUIButtons extends GUIElements
{
	private Button btnHistotic;
	private Button btnSearch;
	private GUIText mUserInterface;
	
	public GUIButtons(MainActivity context,GUIText gUserInterface)
	{
		super(context);
		
		mUserInterface = gUserInterface;
		btnHistotic = (Button) context.findViewById(R.id.btnHistorico);
		btnSearch = (Button) context.findViewById(R.id.btnBuscar);
		
		activeListeners();
	}

	@Override
	public void activeListeners() 
	{
		btnHistotic.setOnClickListener(this);
		btnSearch.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) 
	{
		switch (v.getId()) 
		{
			case R.id.btnHistorico:
				super.get_mContext().showHistoric();
				break;
			case R.id.btnBuscar:
				boolean isValid = super.callValidator(mUserInterface.get_mEdtCep().getText().toString());
				if(!isValid)
					mUserInterface.showMessage(super.get_mErrorCode());
			break;
	    }
	}
}