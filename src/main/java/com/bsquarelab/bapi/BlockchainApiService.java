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

	public String setETHArtHash(String conAddr, String artinfo) {
		if(_ethApi == null) return "";
        ArrayList<String> args = new ArrayList<String>();
        args.add(conAddr); 
        args.add("setartinfo");          
        args.add(artinfo);
        String txID = "";
        try {
        	txID = _ethApi.sendTransaction(args.toArray(new String[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return txID;
	}
	
	public String setETHHoldersHash (String conAddr, String owners) {
		if(_ethApi == null) return "";
        ArrayList<String> args = new ArrayList<String>();
        args.add(conAddr);  
        args.add("setownerinfo");         
        args.add(owners);
        String txID = "";  
        try {
        	txID = _ethApi.sendTransaction(args.toArray(new String[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return txID;
	}
	
	public String addETHTransactionsHash(String conAddr, String txs) {
		if(_ethApi == null) return "";
        ArrayList<String> args = new ArrayList<String>();
        args.add(conAddr); 
        args.add("settxhash");          
        args.add(txs);
        String txID = "";  
        try {
        	txID = _ethApi.sendTransaction(args.toArray(new String[0]));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return txID;
	}
	
	public String getETHArtHash(String conAddr) throws Exception {
		if(_ethApi == null) return "";
        String[] getartinfo = {conAddr,"getartinfo"};
        return _ethApi.sendTransaction(getartinfo);

	}
	
	public String getETHHoldersHash(String conAddr) throws Exception {
		if(_ethApi == null) return "";
        String[] getownerinfo = {conAddr,"getownerinfo"};
        return _ethApi.sendTransaction(getownerinfo);
	}
	
	public String getETHTransactionsHash(String conAddr) throws Exception{
		if(_ethApi == null) return "";
        String[] getsumtx = {conAddr,"getsumtx"};
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