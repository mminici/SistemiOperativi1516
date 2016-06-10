package it.sisop1516.appelli.dischi;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Processo extends Thread{
        private Random r=new Random();
        private Controllore c;
        private final int MIN_TEMPO=100;//ms
        private final int MAX_TEMPO=1000;//ms

        public Processo(Controllore c){
            this.c=c;
        }

        public void run(){
            int d1=-1;
            int d2=-2;
            while(true)
            {
                try
                {
                    //genero casualmente i due processori scelti
                    d1=r.nextInt(c.numProcessori);
                    d2=r.nextInt(c.numProcessori);
                    while(d2==d1){d2=r.nextInt(c.numProcessori);}
                    System.out.println("Processo #"+getId()+" richiede i dischi "+d1+" e "+d2);
                    c.allocaDischi(d1,d2);
                    TimeUnit.MILLISECONDS.sleep(r.nextInt(MAX_TEMPO-MIN_TEMPO+1)+MIN_TEMPO);
                    System.out.println("Processo #"+getId()+"sta per rilasciare i dischi");
                    c.rilasciaDischi(d1,d2);
                }catch(InterruptedException e){}
            }
        }
}
