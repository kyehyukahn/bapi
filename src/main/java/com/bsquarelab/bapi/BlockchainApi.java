package com.bsquarelab.bapi;

public interface BlockchainApi {
	boolean connectEOS(String wallet, String password, String account, String address);
	boolean connectEthereum();
	
	String setETHArtHash(String info);
	String setETHHoldersHash (String holders);
	String addETHTransactionsHash(String Txs);
	String getETHArtHash() throws Exception;
	String getETHHoldersHash() throws Exception;
	String getETHTransactionsHash() throws Exception;
	
	String setEOSArtHash(String info);
	String setEOSHoldersHash (String holders);
	String addEOSTransactionsHash(String Txs);
	String getEOSArtHash();
	String getEOSHoldersHash();
	String getEOSTransactionsHash();	
}