package com.bsquarelab.eosj.impl;


import com.bsquarelab.eosj.EosApiClientFactory;
import com.bsquarelab.eosj.EosApiRestClient;
import com.bsquarelab.eosj.EosApiService;
import com.bsquarelab.eosj.domain.common.WalletKeyType;
import com.bsquarelab.eosj.domain.common.transaction.SignedPackedTransaction;
import com.bsquarelab.eosj.domain.common.transaction.TransactionAction;
import com.bsquarelab.eosj.domain.common.transaction.TransactionAuthorization;
import com.bsquarelab.eosj.domain.request.chain.AbiJsonToBinRequest;
import com.bsquarelab.eosj.domain.request.chain.RequiredKeysRequest;
import com.bsquarelab.eosj.domain.request.chain.transaction.PushTransactionRequest;
import com.bsquarelab.eosj.domain.request.wallet.transaction.SignTransactionRequest;
import com.bsquarelab.eosj.domain.response.chain.*;
import com.bsquarelab.eosj.domain.response.chain.account.Account;
import com.bsquarelab.eosj.domain.response.chain.abi.Abi;
import com.bsquarelab.eosj.domain.response.chain.code.Code;
import com.bsquarelab.eosj.domain.common.transaction.PackedTransaction;
import com.bsquarelab.eosj.domain.response.chain.currencystats.CurrencyStats;
import com.bsquarelab.eosj.domain.response.chain.transaction.PushedTransaction;
import com.bsquarelab.eosj.domain.response.chain.transaction.ScheduledTransactionResponse;
import com.bsquarelab.eosj.domain.response.history.action.Actions;
import com.bsquarelab.eosj.domain.response.history.controlledaccounts.ControlledAccounts;
import com.bsquarelab.eosj.domain.response.history.keyaccounts.KeyAccounts;
import com.bsquarelab.eosj.domain.response.history.transaction.Transaction;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class EosApiServiceImpl implements EosApiService {

    private String _wallet;
    private String _wallet_password;
    private String _account;
    private String _account_address;
    
    private EosApiRestClient _eosApiRestClient;

    public EosApiServiceImpl(String wallet, String password, String account, String address){
    	_wallet = wallet;
    	_wallet_password = password;
    	_account = account;
    	_account_address = address;
    	prepareEOSApi(_wallet, _wallet_password, _account);
    }

    // eosio::action void modifyart(string artHash)
    public String setArtHash(String info) {
    	return applyTx("modifyart", "artHash", info);
	}
    
	// void modifyown(string owHash)
	public String setHoldersHash (String holdersList) {
		return applyTx("modifyown", "owHash", holdersList);
	}

	// eosio::action void modifytx(string txHash)
	public String addTransactionsHash(String Txs) {
		return applyTx("modifytx", "txHash", Txs);
	}

	public String getArtHash() {
		return getTable("artHash");
    }
	
	public String getHoldersHash() {
		return getTable("ownerHash");
	}
	
	public String getTransactionsHash() {
		return getTable("currentTxHash");
	}
	
	private EosApiRestClient prepareEOSApi(String wallet, String password, String account) {
		//_eosApiRestClient = EosApiClientFactory.newInstance("http://127.0.0.1:8899", "http://127.0.0.1:8888", "http://127.0.0.1:8888").newRestClient();
		_eosApiRestClient = EosApiClientFactory.newInstance("http://127.0.0.1:8899", "http://api.kylin.alohaeos.com", "http://api.kylin.alohaeos.com").newRestClient();
		_eosApiRestClient.openWallet(wallet);
		_eosApiRestClient.unlockWallet(wallet, password);
		
		return _eosApiRestClient;
	}
	
	private String getTable(String column) {
		 EosApiRestClient eosApiRestClient = _eosApiRestClient;
	     TableRow tr = eosApiRestClient.getTableRows(_account, _account, "artinfo");
	     
	     List<Map<String, ?>> list;
	     list = tr.getRows();
	     
	     //String[] row = list.get(0).values().toString().split(",");
	     
	     if(column.equals("ownerHash")) {
	    	 return (String)list.get(0).get("ownerHash");
	     }else if(column.equals("currentTxHash")) {
	    	 return (String)list.get(0).get("currentTxHash");
	     }else if(column.equals("artHash")) {
	    	 return (String)list.get(0).get("artHash");
	     }else {
	    	 return "";
	     }
	}
	
	private String applyTx(String func, String param, String value) {
        // Set the client
        EosApiRestClient eosApiRestClient = _eosApiRestClient;
        
        
        /* Create the json array of arguments */
        Map<String, String> args = new HashMap<String, String>(4);
        args.put(param, value);
        AbiJsonToBin data = eosApiRestClient.abiJsonToBin(_account, func, args);

        /* Get the head block */
        ChainInfo chainInfo = eosApiRestClient.getChainInfo();
        Block block = eosApiRestClient.getBlock(chainInfo.getHeadBlockId());
        String ts = block.getTimeStamp();
        String chain_id = chainInfo.getChainId();

        /* Create Transaction Action Authorization */
        TransactionAuthorization transactionAuthorization = new TransactionAuthorization();
        transactionAuthorization.setActor(_account);
        transactionAuthorization.setPermission("active");

        /* Create Transaction Action */
        TransactionAction transactionAction = new TransactionAction();
        transactionAction.setAccount(_account);
        transactionAction.setName(func);
        transactionAction.setData(data.getBinargs());
        transactionAction.setAuthorization(Collections.singletonList(transactionAuthorization));
        
        /* Create a transaction */
        PackedTransaction packedTransaction = new PackedTransaction();
        packedTransaction.setRefBlockPrefix(block.getRefBlockPrefix().toString());
        packedTransaction.setRefBlockNum(block.getBlockNum().toString());
        // expired after 3 minutes
        String expiration = ZonedDateTime.now(ZoneId.of("GMT")).plusMinutes(3).truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        packedTransaction.setExpiration(expiration);
        packedTransaction.setRegion("0");
        packedTransaction.setMax_net_usage_words("0");
        packedTransaction.setActions(Collections.singletonList(transactionAction));

        /* Sign the Transaction */
        
        SignedPackedTransaction signedPackedTransaction 
        = eosApiRestClient.signTransaction(packedTransaction, 
        								Collections.singletonList(_account_address), 
        								chain_id);
        /* Push the transaction */
        PushedTransaction pushedTransaction = eosApiRestClient.pushTransaction("none", signedPackedTransaction);  
    	
		return pushedTransaction.getTransactionId();	
	}
}
