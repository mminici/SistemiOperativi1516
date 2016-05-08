package it.sisop1516.semafori.filosofi;

public class SmartFilosofo extends Filosofo {
	private boolean soddisfatto=false;
	public SmartFilosofo(){
		super();
	}
	
	@Override
	public void run() {
		while(true/*finchè non schiattano*/)
		{	
			try {
				System.out.println("Filosofo #"+ID+" sta pensando");
				pensa();
				System.out.println("Filosofo #"+ID+" ha fame");
				if(soddisfatto)
				{
					Thread.sleep(1000);
				}
				//Il filosofo ha fame e prova ad acquisire le bacchette necessarie
				boolean primaBacchetta=bacchetta[ID].tryAcquire();
				boolean secondaBacchetta=bacchetta[(ID+1)%NUM_FILOSOFI].tryAcquire();
				while(!(primaBacchetta && secondaBacchetta))
				{	
					/*
					 * se non acquisisce entrambe le bacchette, ma fosse riuscito
					 * ad acquisirne una, allora la libera.
					*/
					if(primaBacchetta){bacchetta[ID].release();}
					else if(secondaBacchetta){bacchetta[(ID+1)%NUM_FILOSOFI].release();}
					primaBacchetta=bacchetta[ID].tryAcquire();
					secondaBacchetta=bacchetta[(ID+1)%NUM_FILOSOFI].tryAcquire();
				}
				mangia();
				System.out.println("Filosofo #"+ID+" ha mangiato");
				bacchetta[ID].release();
				bacchetta[(ID+1)%NUM_FILOSOFI].release();
			}catch(InterruptedException e){}
		}	
	}
	
	public static void main(String[] args){
		int c=0;
		while(c<SmartFilosofo.getFilosofi())
		{
			new Thread(new SmartFilosofo()).start();
			c++;
		}

	}
}
