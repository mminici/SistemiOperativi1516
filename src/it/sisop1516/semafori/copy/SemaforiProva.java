package it.sisop1516.semafori.copy;

import java.util.concurrent.Semaphore;

public class SemaforiProva extends Thread {
	private char toPrint; //è il carattere da stampare
	private static Semaphore s=new Semaphore(1); 
	public SemaforiProva(char toPrint){this.toPrint=toPrint;}
	public void run(){
		try {
			s.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Thread #"+this.getId()+" stampa "+String.valueOf(toPrint));
		try {
			//debug purpose
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.release();
	}
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Prova mutua esclusione di stampa");
		Thread.sleep(1000);
		SemaforiProva t1=new SemaforiProva('A');
		SemaforiProva t2=new SemaforiProva('B');
		t2.start(); t1.start(); 

	}

}
