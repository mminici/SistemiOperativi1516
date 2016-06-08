package it.sisop1516.appelli.pallacanestro;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class PartitaSem extends Partita {

	private Semaphore[][] giocatori=new Semaphore[NUM_SQUADRE][GIOCATORI_PER_SQUADRA];
	private Random r=new Random();
	
	public PartitaSem(){
		for(int i=0;i<NUM_SQUADRE;i++)
		{
			for(int j=0;j<GIOCATORI_PER_SQUADRA;j++)
			{
				giocatori[i][j]=new Semaphore(0);
				squadre[i][j]=new Giocatore(this,j,i);
			}
		}
	}
	
	@Override
	protected void iniziaPartita() {
		squadraInPossesso=r.nextInt(NUM_SQUADRE);
		giocatoreInPossesso=r.nextInt(GIOCATORI_PER_SQUADRA);
		giocatori[squadraInPossesso][giocatoreInPossesso].release();
		for(Giocatore[] squadra:squadre)
			for(Giocatore g:squadra)g.start();
		
	}

	@Override
	protected void terminaPartita() {
		for(Giocatore[] squadra:squadre)
			for(Giocatore g:squadra)g.interrupt();
		System.out.print("Partita terminata\nSquadra #"+0+" ha totalizzato "+punteggio[0]+"punti\nSquadra #"+1+" ha totalizzato "+punteggio[1]+" punti\n");
		
	}

	@Override
	protected void aspettaPossesso(int squadra, int id) throws InterruptedException {
		giocatori[squadra][id].acquire();
	}

	@Override
	protected void effettuaAzione(Giocatore g) {
		boolean ok=g.provaAzione();
		if(passaggiConsecutivi<3)
		{
			if(ok)
			{	
				passaggiConsecutivi++;
				int id=r.nextInt(GIOCATORI_PER_SQUADRA);
				while(id==giocatoreInPossesso){id=r.nextInt(GIOCATORI_PER_SQUADRA);}
				System.out.println("Giocatore squadra #"+squadraInPossesso+" maglia -"+giocatoreInPossesso+"- ha effettuato il passaggio al giocatore -"+id+"-");
				giocatoreInPossesso=id;
				giocatori[squadraInPossesso][giocatoreInPossesso].release();
				return;
			}
			else
			{
				System.out.println("Giocatore squadra #"+squadraInPossesso+" maglia -"+giocatoreInPossesso+"- ha sbagliato il passaggio");
				passaggiConsecutivi=0;
				squadraInPossesso=(squadraInPossesso+1)%2;
				giocatoreInPossesso=r.nextInt(GIOCATORI_PER_SQUADRA);
				giocatori[squadraInPossesso][giocatoreInPossesso].release();
				return;
			}
		}
		else if(passaggiConsecutivi==3)
		{
			passaggiConsecutivi=0;
			if(ok)
			{
				punteggio[squadraInPossesso]++;
				System.out.println("Giocatore squadra #"+squadraInPossesso+" maglia -"+giocatoreInPossesso+"- HA SEGNATO!");
				int id=r.nextInt(GIOCATORI_PER_SQUADRA);
				while(id==giocatoreInPossesso){id=r.nextInt(GIOCATORI_PER_SQUADRA);}
				giocatoreInPossesso=id;
				giocatori[squadraInPossesso][giocatoreInPossesso].release();
				return;
			}
			else
			{
				System.out.println("Giocatore squadra #"+squadraInPossesso+" maglia -"+giocatoreInPossesso+"- ha sbagliato il tiro");
				squadraInPossesso=(squadraInPossesso+1)%2;
				giocatoreInPossesso=r.nextInt(GIOCATORI_PER_SQUADRA);
				giocatori[squadraInPossesso][giocatoreInPossesso].release();
				return;
			}
		}
		
	}
	
	public static void main(String[] args)
	{
		PartitaSem p=new PartitaSem();
		p.test();
	}

}
