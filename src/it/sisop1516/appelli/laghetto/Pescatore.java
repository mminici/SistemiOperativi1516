package it.sisop1516.appelli.laghetto;


public class Pescatore extends Persona {
		public Pescatore(int min,int max,int p,Laghetto l)
		{	
			this.l=l;
			minTempo=min;
			maxTempo=max;
			pausa=p;
		}
		
		private void pesca() throws InterruptedException{opera();}
		
		@Override
		public void run(){
			while(true)
			{	
				System.out.println("SONO NEL WHILE-TRUE-pesc"+" numero pesci: "+l.getNum());
				try {
					l.inizia(0);
					pesca();
					l.finisci(0);
					pausa();
				} catch (InterruptedException e) {}
			}
		}
}
