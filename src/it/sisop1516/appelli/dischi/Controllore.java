package it.sisop1516.appelli.dischi;

public abstract class Controllore{

        protected int numProcessori;
        protected final int MAX_PROCESSI_PER_DISCO=5;

        public Controllore(int numProcessori){
            if(numProcessori<1) throw new IllegalArgumentException();
            this.numProcessori=numProcessori;
        }

        protected abstract void allocaDischi(int a,int b) throws InterruptedException;

        protected abstract void rilasciaDischi(int a,int b) throws InterruptedException;

        protected void test(int numProcessi){
                Processo[] processi=new Processo[numProcessi];
                for(int i=0;i<numProcessi;i++)
                {
                    processi[i]=new Processo(this);
                }
                for(Thread t:processi) t.start();
        }
}
