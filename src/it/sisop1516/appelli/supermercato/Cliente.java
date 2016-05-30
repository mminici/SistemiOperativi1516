package it.sisop1516.appelli.supermercato;

import java.util.concurrent.TimeUnit;

public class Cliente extends Thread {
		public static final int MAX_PRODOTTI=50;
		private Cassa c;
		private int p;
		public Cliente(Cassa c,int p){
			this.c=c;
			this.p=p;
		}
		public void run(){
			try {
				spesa();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			int id=c.getID();
			c.consegnaProdotti(id,p);
		}
		
		private void spesa() throws InterruptedException{
			TimeUnit.SECONDS.sleep(p);
		}
}
