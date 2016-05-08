package it.sisop1516.semafori.LS;
/**
 * La classe MemoriaCondivisa è la classe astratta riferente al problema 
 * dei lettori-scrittori che concorrono all'azione su un'unica memoria condivisa.
 * @author marco
 *
 */
public abstract class MemoriaCondivisa {
		public abstract void inizioLettura() throws InterruptedException;
		
		public abstract void inizioScrittura() throws InterruptedException;
		
		public abstract void fineLettura() throws InterruptedException;
		
		public abstract void fineScrittura() throws InterruptedException;
		
		protected void test(int numLettori,int numScrittori)
		{
			Lettore[] lettori=new Lettore[numLettori];
			for(int i=0;i<numLettori;i++)
			{
				lettori[i]=new Lettore(this);
			}
			Scrittore[] scrittori=new Scrittore[numScrittori];
			for(int i=0;i<numScrittori;i++)
			{
				scrittori[i]=new Scrittore(this);
			}
			System.out.println("Test della classe "+getClass().getSimpleName());
			for(int i=0;i<numLettori;i++){new Thread(lettori[i]).start();}
			System.out.println("Avviati "+numLettori+" lettori");
			for(int i=0;i<numScrittori;i++){new Thread(scrittori[i]).start();}
			System.out.println("Avviati "+numScrittori+" scrittori");
		}
}
