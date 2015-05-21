package com.teste.buscacep;

import com.teste.buscacep.R;
import com.teste.buscacep.framework.MaskedWatcher;

import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class GUIText extends GUIElements implements OnEditorActionListener
{
	private TextView mTxtFeedback;
	private EditText mEdtCep;
	private TextView mTxtResult;
	
	private String labelTxtResult;
	private int txtFeedbackCurrentColor;

	public GUIText(MainActivity context)
	{
		super(context);
		
		mTxtFeedback = (TextView) context.findViewById(R.id.txtFeedback);
		mEdtCep = (EditText) context.findViewById(R.id.edtCep);
		mTxtResult = (TextView) context.findViewById(R.id.txtResult);
		
		labelTxtResult = mTxtResult.getText().toString();
		txtFeedbackCurrentColor = 0;
		
		activeListeners();
	}
	
	@Override
	public void activeListeners() 
	{
		mEdtCep.addTextChangedListener( new MaskedWatcher("#####-###") );
		mEdtCep.setOnClickListener(this);
		mEdtCep.setOnEditorActionListener(this);
	}
	
	public void showMessage(int code)
	{
		txtFeedbackCurrentColor = Color.RED;
		mTxtFeedback.setTextColor(txtFeedbackCurrentColor);
		
		if(code == CEPValidator.EMPTY)
			mTxtFeedback.setText("Você não digitou nenhum número!");
		else if(code == CEPValidator.INCOMPLETE)
			mTxtFeedback.setText("O CEP digitado está incompleto!");
	}
	
	public void showMessage(String text)
	{
		txtFeedbackCurrentColor = Color.BLACK;
		mTxtFeedback.setTextColor(txtFeedbackCurrentColor);
		mTxtFeedback.setText(text);
	}
	
	public void showResultFromJSON(String text)
	{
		if(mTxtResult.getVisibility() == View.INVISIBLE)
			mTxtResult.setVisibility(View.VISIBLE);
		else
			mTxtResult.setText("");
		mTxtResult.setText(labelTxtResult+"\nCEP: "+mEdtCep.getText().toString()+"\n"+text);
	}
	
	@Override
	public void onClick(View v) 
	{
		mEdtCep.getText().clear();
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) 
	{
		if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) 
        {    		
    		boolean isValid = super.callValidator(mEdtCep.getText().toString());
    		
    		if(!isValid)
				showMessage(super.get_mErrorCode());
            
    		return true;
        }
        return false;
	}
	
	//Getters
	public EditText get_mEdtCep()
	{
		return mEdtCep;
	}
	
	public TextView get_mTxtResult()
	{
		return mTxtResult;
	}
	
	public TextView get_mTxtFeedback()
	{
		return mTxtFeedback;
	}	
	
	public int get_mTxtFeedbackCurrentColor()
	{
		return txtFeedbackCurrentColor;
	}
	
	public boolean get_mTxtResultVisibility()
	{
		if(mTxtResult.getVisibility() == View.INVISIBLE)
			return false;
		else
			return true;
	}
	
	//Setters
	public void set_mTxtFeedbackColor(int color)
	{
		mTxtFeedback.setTextColor(color);
	}

	public void set_mTxtResultVisibility(Boolean value)
	{
		if(value)
			mTxtResult.setVisibility(View.VISIBLE);
		else
			mTxtResult.setVisibility(View.INVISIBLE);
	}
}