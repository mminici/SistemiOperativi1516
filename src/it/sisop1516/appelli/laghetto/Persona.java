package it.sisop1516.appelli.laghetto;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public abstract class Persona extends Thread {
		protected Laghetto l;
		protected int minTempo,maxTempo,pausa;
		private Random r=new Random();
		
		protected void opera() throws InterruptedException
		{
			TimeUnit.MILLISECONDS.sleep(r.nextInt(maxTempo-minTempo+1)+minTempo);
		}
		public void pausa() throws InterruptedException{TimeUnit.MILLISECONDS.sleep(pausa);}
}
