package it.sisop1516.appelli.pallacanestro_v2;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PartitaSem extends Partita {
	
	private Semaphore mutex=new Semaphore(1);
	private Semaphore[] squadre=new Semaphore[NUM_SQUADRE];
	private boolean inCorso=true;
	private Random r=new Random();
	
	public PartitaSem(){
		for(int i=0;i<NUM_SQUADRE;i++)
		{
			squadre[i]=new Semaphore(0);
		}
		squadraInPossesso=r.nextInt(NUM_SQUADRE);
		squadre[squadraInPossesso].release();
	}
	
	@Override
	protected boolean riceviPalla(int s) throws InterruptedException {
		boolean ok=false;
		squadre[s].acquire();
		squadraInPossesso=s;
		System.out.println("Giocatore della squadra #"+s+" è in possesso");
		mutex.acquire();
		ok=inCorso;
		mutex.release();
		return ok;
	}

	@Override
	protected boolean lanciaPalla(int s, int p) throws InterruptedException {
		mutex.acquire();
		if(!inCorso){ mutex.release(); return false;}
		//la partita è in corso
		boolean successo=false;
		int esito=r.nextInt(100); //percentuale
		if(esito<=p){successo=true;}
		if(successo)
		{
			if(passaggiConsecutivi<PASSAGGI_AL_TIRO)
			{
				System.out.println("Giocatore della squadra #"+s+" ha passato la palla con successo");
				passaggiConsecutivi++;
				mutex.release();
				squadre[s].release();
				return true;
			}
			else
			{
				System.out.println("Giocatore della squadra #"+s+" HA SEGNATO!");
				punteggio[s]++;
				passaggiConsecutivi=0;
				squadre[s].release();
				mutex.release();
				return true;
			}
		}
		else
		{
			System.out.println("Giocatore della squadra #"+s+" ha perso il possesso :(");
			passaggiConsecutivi=0;
			squadraInPossesso=(squadraInPossesso+1)%2;
			squadre[squadraInPossesso].release();
			mutex.release();
			return false;
		}
	}

	@Override
	protected void termina() throws InterruptedException {
		mutex.acquire();
		inCorso=false;
		mutex.release();
		System.out.print("Partita terminata\nSquadra #"+0+" ha totalizzato "+punteggio[0]+" punti\nSquadra #"+1+" ha totalizzato "+punteggio[1]+" punti\n");
	}
	
	public static void main(String[] args){
		PartitaSem p=new PartitaSem();
		p.test(10);
	}
}
