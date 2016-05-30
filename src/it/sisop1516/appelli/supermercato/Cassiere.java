package it.sisop1516.appelli.supermercato;

import java.util.concurrent.TimeUnit;

public class Cassiere extends Thread {
		private int id,p;
		private Cassa c;
		public Cassiere(int id,Cassa c){
			this.id=id;
			this.c=c;
		}
		
		public int getP(){return p;}
		public void setP(int p){this.p=p;}
		
		public void run(){
			while(!c.finito())
			{	
				System.out.println("Cassa #"+this.getId()+" QUI");
				p=c.segnalaCassaLibera(id);
				try {
					scansiona();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				c.congedaCliente(id);
			}
		}
		private void scansiona() throws InterruptedException {
			TimeUnit.SECONDS.sleep(p);
			
		}
}
