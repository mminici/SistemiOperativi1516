package it.sisop1516.semafori;

import java.util.concurrent.Semaphore;

public class SyncSequenceSemaph extends Thread {
	private boolean precedenza;
	private char toPrint;
	private static Semaphore s1=new Semaphore(1);
	private static Semaphore s2=new Semaphore(0);
	public SyncSequenceSemaph(boolean precedenza,char toPrint){
		this.precedenza=precedenza;
		this.toPrint=toPrint;		
	}
	
	public void run(){
		if(precedenza)
		{	
			//il thread ha la precedenza nella sequenza temporale
			try {
				s1.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Il thread #"+this.getId()+" stampa "+String.valueOf(toPrint));
//			try {
//				//debug purpose
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {/*handling exception*/}
			s2.release();
		}
		else{
			//il thread non ha la precedenza nella sequenza temporale
			try {
				s2.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Il thread #"+this.getId()+" stampa "+String.valueOf(toPrint));
//			try {
//				//debug purpose
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {/*handling exception*/}
			s1.release();
		}
	}
	public static void main(String[] args) throws InterruptedException {
		int cont=10;
		System.out.println("Gestione caso ordine temporale-sincronizzazione");
		SyncSequenceSemaph[] noPre=new SyncSequenceSemaph[cont];
		SyncSequenceSemaph[] pre=new SyncSequenceSemaph[cont];
		while(cont>0){
		pre[cont-1]=new SyncSequenceSemaph(true,'A');
		noPre[cont-1]=new SyncSequenceSemaph(false,'B');
		cont--;
		}
		for(Thread t:noPre){t.start();}
		for(Thread t:pre){t.start();}
	}

}
