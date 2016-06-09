package it.sisop1516.appelli.camerieri;

import java.util.concurrent.TimeUnit;

public class Lavapiatti extends Thread {
		private Buffer contenitore,scolapiatti;
		private final int TEMPO_RACCOLTA=15; //sec
		
		public void run(){
			while(true)
			{
				try
				{
					contenitore.get();
					TimeUnit.SECONDS.sleep(TEMPO_RACCOLTA);
					scolapiatti.put();
				}catch(InterruptedException e){}
			}
		}

}
