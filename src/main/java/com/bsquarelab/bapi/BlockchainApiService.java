package com.bsquarelab.bapi;

import java.util.ArrayList;

import com.bsquarelab.eosj.EosApiService;
import com.bsquarelab.eosj.impl.EosApiServiceImpl;
import com.bsquarelab.web3j.EthCommander;

public class BlockchainApiService implements BlockchainApi {
    public enum Blockchain{ NONE, ETH, EOS, ALL }

    private Blockchain  blockchain;
    
    //Ethereum
    private EthCommander _ethApi = null;
    
    //EOS    
    private EosApiService _eosApi = null;
    
    public BlockchainApiService(Blockchain bc){
    	blockchain = bc;
    }
    
    public boolean connectEOS(String wallet, String password, String account, String address) {
    	_eosApi = new EosApiServiceImpl(wallet, password, account, address );
    	return true;
    }
    
    public boolean connectEthereum() {
    	_ethApi = new EthCommander();
    	return true;
    }

	public String setETHArtHash(String info) {
		if(_ethApi == null) return "";
        ArrayList<String> args = new ArrayList<String>();
        args.add("setartinfo");
        args.add("user03");
        String txID = "";  
        try {
        	txID = _ethApi.sendTransaction(args.toArray(new String[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return txID;
	}
	
	public String setETHHoldersHash (String holders) {
		if(_ethApi == null) return "";
        ArrayList<String> args = new ArrayList<String>();
        args.add("setownerinfo");
        args.add("txs03");
        String txID = "";  
        try {
        	txID = _ethApi.sendTransaction(args.toArray(new String[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return txID;
	}
	
	public String addETHTransactionsHash(String Txs) {
		if(_ethApi == null) return "";
        ArrayList<String> args = new ArrayList<String>();
        args.add("settxhash");
        args.add("hash03");
        String txID = "";  
        try {
        	txID = _ethApi.sendTransaction(args.toArray(new String[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return txID;
	}
	
	public String getETHArtHash() throws Exception {
		if(_ethApi == null) return "";
        String[] getartinfo = {"getartinfo"};
        return _ethApi.sendTransaction(getartinfo);

	}
	
	public String getETHHoldersHash() throws Exception {
		if(_ethApi == null) return "";
        String[] getownerinfo = {"getownerinfo"};
        return _ethApi.sendTransaction(getownerinfo);
	}
	
	public String getETHTransactionsHash() throws Exception{
		if(_ethApi == null) return "";
        String[] getsumtx = {"getsumtx"};
        return _ethApi.sendTransaction(getsumtx);
	}
	
	public String setEOSArtHash(String info) {
		if(_eosApi == null) return "";
		return _eosApi.setArtHash(info);
	}
	
	public String setEOSHoldersHash (String holders) {
		if(_eosApi == null) return "";
		return _eosApi.setHoldersHash(holders);
	}
	
	public String addEOSTransactionsHash(String Txs) {
		if(_eosApi == null) return "";
		return _eosApi.addTransactionsHash(Txs);
	}
	
	public String getEOSArtHash() {
		if(_eosApi == null) return "";
		return _eosApi.getArtHash();
	}
	
	public String getEOSHoldersHash() {
		if(_eosApi == null) return "";
		return _eosApi.getHoldersHash();
	}
	
	public String getEOSTransactionsHash() {
		if(_eosApi == null) return "";
		return _eosApi.getTransactionsHash();	
	}	
}