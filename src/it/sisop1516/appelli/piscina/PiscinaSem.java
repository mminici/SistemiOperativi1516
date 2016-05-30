package it.sisop1516.appelli.piscina;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PiscinaSem extends Piscina {
	
	private Semaphore mutex=new Semaphore(1);
	private Semaphore[] corsie=new Semaphore[NUM_CORSIE];
	private int[] numNuotatori=new int[NUM_CORSIE];
	private int[] ORARIO_CHIUSURA={4,5};
	private int tempoCorrente;
	private long tempoIniziale;
	private Random r=new Random();
	
	public PiscinaSem(){
		for(int i=0;i<corsie.length;i++)
		{
			corsie[i]=new Semaphore(0,true); //FIFO policy
			numNuotatori[i]=0;
		}
		mod=MATTINA;
		tempoCorrente=0;
	}
	
	@Override
	public int getMod(){return mod;}
	
	@Override
	public int tempoAllaChiusura(){
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tempoCorrente=(int) TimeUnit.MINUTES.convert(System.currentTimeMillis()-tempoIniziale,TimeUnit.MILLISECONDS);
		int tempoAllaChiusura=(ORARIO_CHIUSURA[mod]*60)-tempoCorrente;
		mutex.release();
		return tempoAllaChiusura;
	}
	
	@Override
	public int getCorsia() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(presenteIstruttore)
		{
			int corsiaScelta=-1;
			for(int i=0;i<numNuotatori.length;i++)
			{
				if(numNuotatori[i]<MAX_PERSONE_CORSIA)
				{	
					corsiaScelta=i;
					numNuotatori[i]++;
					mutex.release(); 
					System.out.println("Nuotatore sceglie corsia "+i);
					return corsiaScelta; //nella corsia "i" c'è almeno un posto
				}
			}
			corsiaScelta=r.nextInt(NUM_CORSIE); //scelgo casualmente una corsia
			try {
				mutex.release();
				corsie[corsiaScelta].acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("Nuotatore sceglie corsia "+corsiaScelta);
			return corsiaScelta;
		}
		else{
			mutex.release();
			//throw new IllegalStateException();
			return -1;
		}
	}

	@Override
	public void chiudiPiscina() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mod=(mod+1)%1;
		presenteIstruttore=false;
		System.out.println("Istruttore ha chiuso la piscina");
		System.out.println("Pausa "+((mod==1)?"PRANZO":"SERA"));
		mutex.release();
	}

	@Override
	protected void lasciaCorsia(int corsia) {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		numNuotatori[corsia]--;
		if(presenteIstruttore)
		{
			if(numNuotatori[corsia]==3){
				corsie[corsia].release();
			}
		}
		else
		{
			numNuotatori[corsia]=0;
		}
		mutex.release();
	}



	@Override
	protected boolean aperta() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean b=presenteIstruttore;
		mutex.release();
		return b;
	}


	@Override
	protected void apriPiscina() {
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tempoIniziale=System.currentTimeMillis();
		presenteIstruttore=true;
		System.out.println("Istruttore ha aperto la piscina");
		mutex.release();
	}

	public static void main(String [] args) throws InterruptedException{
		Piscina piscina=new PiscinaSem();
		piscina.test(100);
	}


}
