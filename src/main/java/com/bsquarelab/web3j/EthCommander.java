package com.bsquarelab.web3j;

/*
이더리움 블록체인 API
BlockchainInterface.transaction(명령인수);
명령인수
getartinfo : 미술품 정보 해시값 조회
getownerinfo : 소유주 정보 해시값 조회
getsumtx : 트랜잭션 해시최종값 조회
settxhash: 트랜잭션 해시값 input (return 해시최종값)
setartinfo : 미술품 정보 해시값 입력
setownerinfo : 소유주 정보 해시값 입력
*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class EthCommander{

    public String sendTransaction(String[] args) throws Exception{
		String [] nodecom = null;
		String result = null;
		if(args != null && args.length == 2){
//			System.out.println(args[0]+" "+args[1]);
			
			nodecom = new String[]{"/usr/local/bin/node","/Users/techny/Development/ArtProject/artchain",args[0],args[1]};
			result = makeTransaction(nodecom);
		}else if (args != null && args.length == 3){
//			System.out.println(args[0]+" "+args[1]+" "+args[2]);
			nodecom = new String[]{"/usr/local/bin/node","/Users/techny/Development/ArtProject/artchain",args[0], args[1], args[2]};
			result = makeTransaction(nodecom);			
		}
		return result;
	}

	private String makeTransaction(String[] command) throws Exception {
//		for(int a = 0 ;a < command.length; a++)
//			System.out.println(command[a]);
		
		Process process = Runtime.getRuntime().exec(command);
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;

		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//			TimeUnit.SECONDS.sleep(1);
			return line;
		}

		return null;
	}
}
