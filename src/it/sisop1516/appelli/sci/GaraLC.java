package it.sisop1516.appelli.sci;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GaraLC extends Gara {
	
	Lock l= new ReentrantLock();
	Condition c=l.newCondition();
	
	public GaraLC(int numeroSciatori){
		this.numeroSciatori=numeroSciatori;
		this.tempi=new int[numeroSciatori];
		this.prossimoSciatore=0;
	}
	
	
	@Override
	protected void partenza(Sciatore s) {
		int numMaglia=s.getMaglia();
		try{
			l.lock();
			while(!mioTurno(numMaglia)){
				c.await();
			}
			System.out.println("Sciatore #"+s.getId()+" parte");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{l.unlock();}
		
	}

	private boolean mioTurno(int numMaglia) {
		return prossimoSciatore==numMaglia;
	}


	@Override
	protected int arrivo(Sciatore s) {
		int numMaglia=s.getMaglia();
		int tempo=s.getTempo();
		int posizione=1;
		try{
			l.lock();
			tempi[numMaglia]=tempo;
			for(int i=0;i<numMaglia;i++)
			{
				if(tempi[i]<tempo)
				{
					posizione++;
				}
			}
		}finally{l.unlock();}
		return posizione;
	}

	@Override
	protected boolean prossimo() {
		if(prossimoSciatore>=numeroSciatori)
		{	
			try {
				l.lock();
			
			LinkedList<Integer> classifica=generaClassifica(tempi);
			for(int i=0;i<classifica.size();i++)
			{	
				int sciatore=classifica.remove();
				System.out.println("Sciatore #"+sciatore+" si classifica in "+(i+1)+"° posizione");
			}
			return false;
			}finally{l.unlock();}
		}
		try {
			TimeUnit.SECONDS.sleep(TEMPO_PROX_TURNO);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			l.lock();
			prossimoSciatore++;
			c.signalAll();
			}finally{l.unlock();}
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
}
