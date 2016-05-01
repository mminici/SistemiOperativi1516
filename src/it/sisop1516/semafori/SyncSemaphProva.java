package it.sisop1516.semafori;

import java.util.concurrent.Semaphore;

public class SyncSemaphProva extends Thread {
	private boolean precedenza;
	private char toPrint;
	private static Semaphore s=new Semaphore(0);
	
	public SyncSemaphProva(boolean precedenza,char toPrint){
		this.precedenza=precedenza;
		this.toPrint=toPrint;		
	}
	
	public void run(){
		if(precedenza)
		{	
			//il thread ha la precedenza nella sequenza temporale
			System.out.println("Il thread #"+this.getId()+" stampa "+String.valueOf(toPrint));
//			try {
//				//debug purpose
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {/*handling exception*/}
			s.release();
		}
		else{
			//il thread non ha la precedenza nella sequenza temporale
			try {
				s.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Il thread #"+this.getId()+" stampa "+String.valueOf(toPrint));
//			try {
//				//debug purpose
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {/*handling exception*/}
		}
	}
	public static void main(String[] args) throws InterruptedException {
		int cont=10;
		System.out.println("Gestione caso ordine temporale-sincronizzazione");
		while(cont>0){
		SyncSemaphProva t1=new SyncSemaphProva(true,'A');
		SyncSemaphProva t2=new SyncSemaphProva(false,'B');
		t2.start();  t1.start(); 
		cont--;
		t2.join();
		}
	}

}
