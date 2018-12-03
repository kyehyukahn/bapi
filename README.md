# bapi
blockchain light node



'''
    BlockchainApi bApi = new BlockchainApiService(Blockchain.EOS);
    String wallet = "default";
    String wallet_password = "PW5KhdSnrn1ubnUqg3xUNJ7NzjWNcaaM31Qdr2MgAmwrpj7rdLsoV";
    String account = "prorata";
    String account_address = "EOS8f3xTQz16gM51GnmFWoE2Mwwyg7rVRq691sjbYfHTRrJYqX1cG";
    bApi.connectEOS(wallet, wallet_password, account, account_address);
    System.out.println(bApi.setEOSArtHash("hash03"));
    System.out.println(bApi.setEOSHoldersHash("Users03"));
    System.out.println(bApi.addEOSTransactionsHash("Txs03"));
    System.out.println(bApi.getEOSArtHash());
    System.out.println(bApi.getEOSHoldersHash());
    System.out.println(bApi.getEOSTransactionsHash());              		

    bApi = new BlockchainApiService(Blockchain.ETH);
    bApi.connectEthereum();
    System.out.println(bApi.setETHArtHash("hash03"));
    System.out.println(bApi.setETHHoldersHash("Users03"));
    System.out.println(bApi.addETHTransactionsHash("Txs03"));
    System.out.println(bApi.getETHArtHash());
    System.out.println(bApi.getETHHoldersHash());
    System.out.println(bApi.getETHTransactionsHash());
'''              	
