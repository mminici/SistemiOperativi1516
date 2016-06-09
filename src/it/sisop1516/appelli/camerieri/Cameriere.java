package it.sisop1516.appelli.camerieri;

import java.util.concurrent.TimeUnit;

public class Cameriere extends Thread {
		private Buffer contenitore;
		private final int PIATTI_ALLA_VOLTA=4;
		private final int TEMPO_RACCOLTA=20; //sec
		public void run(){
			int cnt=0;
			while(true)
			{
				try
				{	
					TimeUnit.SECONDS.sleep(TEMPO_RACCOLTA);
					while(cnt<PIATTI_ALLA_VOLTA)
					{
						contenitore.put();
						cnt++;
					}
					cnt=0;
				}catch(InterruptedException e){}
			}
		}
		
}
