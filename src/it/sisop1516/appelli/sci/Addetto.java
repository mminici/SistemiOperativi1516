package it.sisop1516.appelli.sci;

public class Addetto extends Thread {
		
		private Gara gara;
		
		public Addetto(Gara gara){
			this.gara=gara;
		}
		
		public void run(){
			while(gara.prossimo()){}
		}
}
