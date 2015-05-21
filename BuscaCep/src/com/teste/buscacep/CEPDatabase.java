package com.teste.buscacep;

import java.util.ArrayList;

public class CEPDatabase 
{
	private ArrayList<String> arCepSearched;
	private ArrayList<String> arAddress;

	public CEPDatabase()
	{
		arCepSearched = new ArrayList<String>();
		arAddress = new ArrayList<String>();
	}
	
	public void addDatabase(String dataCep, String dataAddress)
	{
		boolean isStored = checkCepIsRepeated(dataCep);
		if(!isStored)
		{
			arCepSearched.add(dataCep);
			arAddress.add(dataAddress);
		}
	}
	
	private boolean checkCepIsRepeated(String cep)
	{		
		for(int i = 0; i < arCepSearched.size(); i++) 
		{
			boolean compare = arCepSearched.get(i).equals(cep);
			if (compare)
				return true;
		}
		return false;
	}
	
	public ArrayList<String> getCepData()
	{
		return arCepSearched;
	}
	
	public ArrayList<String> getAddressData()
	{
		return arAddress;
	}
}