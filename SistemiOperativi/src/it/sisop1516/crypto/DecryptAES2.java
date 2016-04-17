package it.sisop1516.crypto;

public class DecryptAES2 extends Thread {
		private int infBound,supBound,keyLength;
		private String currentKey;
		private boolean found=false;
		private byte[] encrypted,output;
		private byte[] goal;
		private byte[] goal2;
		
		public boolean getBool(){return found;}
		
		public String getKey(){return currentKey;}
		
		public int getKeyLength(){return keyLength;}
		
		public DecryptAES2(int infBound,int supBound,int keyLength,String textToDecrypt,byte[] encrypted,byte[] output)
		{
			this.infBound=infBound;
			this.supBound=supBound;
			this.keyLength=keyLength;
			this.goal=textToDecrypt.getBytes();
			textToDecrypt=textToDecrypt+"\n";
			this.goal2=textToDecrypt.getBytes();
			this.encrypted=encrypted;
			this.output=output;
		}
		
		public void run()
		{	
			
			ciclo:for(int i=infBound;i<=supBound && !found;i++)
			{
//				if(i>=infBound+1){
//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}}
			currentKey=String.valueOf(i);
			while(currentKey.length()<16)
			{
				currentKey="0"+currentKey;
			}
			//key has been generated
			System.out.println();
			System.out.println("checking... "+currentKey+" key");
			System.out.println();
			try {
				CryptoUtils.decryptSISOP(currentKey, encrypted, output);
			} catch (CryptoException e) {/* possible comments here */ }
			
			if(output.length==goal.length){
			for(int j=0;j<output.length;j++){
				if(output[j]!=goal[j]){continue ciclo;}
			}}
			else if(output.length==goal2.length){
				for(int l=0;l<output.length;l++){
					if(output[l]!=goal2[l]){continue ciclo;}
				}}
			else{continue ciclo;}
			found=true; this.interrupt();
			
			}
		}
		
}
