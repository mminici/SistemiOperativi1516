package it.sisop1516.appelli.sci;

import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class GaraSem extends Gara {
	
	private Semaphore mutex=new Semaphore(1);
	private Semaphore[] puoiScendere;
	private Semaphore stampa=new Semaphore(0);
	
	public GaraSem(int numeroSciatori){
		this.numeroSciatori=numeroSciatori;
		this.tempi=new int[numeroSciatori];
		this.prossimoSciatore=0;
		this.puoiScendere=new Semaphore[numeroSciatori];
		puoiScendere[0]=new Semaphore(1);
		for(int i=1;i<puoiScendere.length;i++)
		{
			puoiScendere[i]=new Semaphore(0);
		}
	}
	
	@Override
	protected void partenza(Sciatore s) {
		int numMaglia=s.getMaglia();
		try {
			puoiScendere[numMaglia].acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Sciatore #"+s.getId()+" parte");
	}

	@Override
	protected int arrivo(Sciatore s) {
		int numMaglia=s.getMaglia();
		int tempo=s.getTempo();
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tempi[numMaglia]=tempo;
		int posizione=1;
		for(int i=0;i<numMaglia;i++)
		{
			if(tempi[i]<tempo)
			{
				posizione++;
			}
		}
		if(numMaglia==(numeroSciatori-1)){stampa.release();}
		mutex.release();
		return posizione;
	}

	@Override
	protected boolean prossimo() {
		if(prossimoSciatore>=numeroSciatori)
		{	
			try {
				stampa.acquire();
				mutex.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LinkedList<Integer> classifica=generaClassifica(tempi);
			for(int i=0;i<classifica.size();i++)
			{	
				int sciatore=classifica.remove();
				System.out.println("Sciatore #"+sciatore+" si classifica in "+(i+1)+"° posizione");
			}
			mutex.release();
			return false;
		}
		try {
			TimeUnit.SECONDS.sleep(TEMPO_PROX_TURNO);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		puoiScendere[prossimoSciatore].release();
		try {
			mutex.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		prossimoSciatore++;
		mutex.release();
		return true;
	}

	private static LinkedList<Integer> generaClassifica(int[] tempi) {
		LinkedList<Integer> classifica=new LinkedList<>();
		int migliore=-1;
		int tempoMigliore=Integer.MAX_VALUE-1;
		while(classifica.size()<tempi.length)
		{
			for(int i=0;i<tempi.length;i++)
			{
				if(tempi[i]<tempoMigliore)
				{
					tempoMigliore=tempi[i];
					migliore=i;
				}
		    }
			classifica.add(migliore);
			tempi[migliore]=Integer.MAX_VALUE;
			tempoMigliore=Integer.MAX_VALUE-1;
		}
		return classifica;
	}
	
	
	public static void main(String[] args){
		Gara gara=new GaraSem(25);
		gara.test();
	}
}
