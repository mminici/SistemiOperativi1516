package it.sisop1516.semafori.filosofi;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class Filosofo implements Runnable{
		
		protected static final int NUM_FILOSOFI=5;
		protected static Semaphore[] bacchetta=new Semaphore[NUM_FILOSOFI];
		private static boolean inizializzato=false;
		private static final int MIN_MANGIARE=2;
		private static final int MAX_MANGIARE=5;
		private static final int MIN_PENSARE=10;
		private static final int MAX_PENSARE=25;
		private Random r=new Random();
		protected int ID;
		private static int currentID=0;
		
		public Filosofo(){
			if(!inizializzato)
			{
				for(int i=0;i<NUM_FILOSOFI;i++)
				{
					bacchetta[i]=new Semaphore(1);
				}
				inizializzato=true;
			}
			
			if(currentID>NUM_FILOSOFI-1){throw new IllegalStateException();}
			this.ID=currentID;
			currentID++;
		}
		
		public static int getFilosofi(){return NUM_FILOSOFI;}
		
		@Override
		public void run() {
			while(true/*finchè non schiattano*/)
			{	
				try {
					System.out.println("Filosofo #"+ID+" sta pensando");
					pensa();
					System.out.println("Filosofo #"+ID+" ha fame");
					if(ID==NUM_FILOSOFI-1)
					{	
						//per evitare deadlock
						bacchetta[(ID+1)%NUM_FILOSOFI].acquire();
						bacchetta[ID].acquire();
					}
					else
					{
					bacchetta[ID].acquire();
					bacchetta[(ID+1)%NUM_FILOSOFI].acquire();
					}
					mangia();
					System.out.println("Filosofo #"+ID+" ha mangiato");
					bacchetta[ID].release();
					bacchetta[(ID+1)%NUM_FILOSOFI].release();
				}catch(InterruptedException e){}
			}	
		}
		
		protected void pensa() throws InterruptedException{
			attendi(MIN_PENSARE,MAX_PENSARE);
		}
		
		protected void mangia() throws InterruptedException{
			attendi(MIN_MANGIARE,MAX_MANGIARE);
		}
		
		private void attendi(int min,int max) throws InterruptedException{
			TimeUnit.SECONDS.sleep(r.nextInt(max-min+1)+min);
		}
}
