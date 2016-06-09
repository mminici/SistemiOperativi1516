package it.sisop1516.appelli.camerieri;

import java.util.concurrent.TimeUnit;

public class Asciugapiatti extends Thread {
		private Buffer scolapiatti;
		private final int TEMPO_RACCOLTA=10; //sec
		
		public void run(){
			while(true)
			{
				try
				{
					scolapiatti.get();
					TimeUnit.SECONDS.sleep(TEMPO_RACCOLTA);
				}catch(InterruptedException e){}
			}
		}
}
