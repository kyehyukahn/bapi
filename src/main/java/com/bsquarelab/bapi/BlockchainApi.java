package com.bsquarelab.bapi;

public interface BlockchainApi {
	boolean connectEOS(String wallet, String password, String account, String address);
	boolean connectEthereum(String commander, String location);
	
	String setETHArtHash(String conAddr, String info);
	String setETHHoldersHash (String conAddr, String holders);
	String addETHTransactionsHash(String conAddr, String Txs);
	String getETHArtHash(String conAddr) throws Exception;
	String getETHHoldersHash(String conAddr) throws Exception;
	String getETHTransactionsHash(String conAddr) throws Exception;
	
	String setEOSArtHash(String info);
	String setEOSHoldersHash (String holders);
	String addEOSTransactionsHash(String Txs);
	String getEOSArtHash();
	String getEOSHoldersHash();
	String getEOSTransactionsHash();	
}