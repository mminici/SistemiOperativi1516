package it.sisop1516.appelli.laghetto;


public class Pescatore extends Persona {
		public Pescatore(int min,int max,int p,Laghetto l)
		{	
			this.l=new LaghettoSem(l.getMin(),l.getMax(),l.getNum());
			minTempo=min;
			maxTempo=max;
			pausa=p;
		}
		
		private void pesca() throws InterruptedException{opera();}
		
		@Override
		public void run(){
			while(true)
			{
				try {
					l.inizia(0);
					pesca();
					l.finisci(0);
					pausa();
				} catch (InterruptedException e) {}
			}
		}
}
