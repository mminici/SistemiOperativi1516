package it.sisop1516.appelli.piscina;

import java.util.concurrent.TimeUnit;

public class Istruttore extends Thread {
		private Piscina piscina;
		private static final int[] TEMPI_APERTURA={4,5};
		private static final int TEMPO_PAUSA=1;
		public void run(){
			piscina.apriPiscina();
			try {
				TimeUnit.HOURS.sleep(TEMPI_APERTURA[piscina.getMod()]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			piscina.chiudiPiscina();
			try {
				TimeUnit.HOURS.sleep(TEMPO_PAUSA);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			piscina.apriPiscina();
			try {
				TimeUnit.HOURS.sleep(TEMPI_APERTURA[piscina.getMod()]);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			piscina.chiudiPiscina();
		}
}
