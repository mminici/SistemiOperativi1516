package it.sisop1516.semafori.copy;

import java.util.concurrent.Semaphore;

public class SemaphMatrix extends Thread {
		private int[] vector;
		private boolean inc;
		private int cont=10;
		private static Semaphore s=new Semaphore(1);
		
		public SemaphMatrix(int []vector,boolean b){
			this.vector=vector;
			inc=b;
		}
		
		public void run(){
			
			while(cont>0)
			{
			for(int i=0;i<vector.length;i++)
			{	
			try {
				s.acquire();
			} catch (InterruptedException e) {/*e.printStackTrace();*/}
				if(inc){vector[i]++;}
				else{vector[i]--;}
				s.release();
			}
			cont--;
			}
		}
}
