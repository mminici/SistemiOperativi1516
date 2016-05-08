package it.sisop1516.semafori.PC;

public abstract class Buffer {
		protected int[] buffer;
		protected int in=0;
		protected int out=0;
		
		public Buffer(int dimensione){
			buffer=new int[dimensione];
		}
		
		public abstract int get() throws InterruptedException;
		
		public abstract void put(int i) throws InterruptedException;
		
		protected void test(int numProduttori,int numConsumatori){
			for(int i=0;i<numProduttori;i++){new Thread(new Produttore(this)).start();}
			for(int i=0;i<numProduttori;i++){new Thread(new Consumatore(this)).start();}

			
		}
}
