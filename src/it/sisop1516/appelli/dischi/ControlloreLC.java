package it.sisop1516.appelli.dischi;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ControlloreLC extends Controllore{

    private Lock l=new ReentrantLock();
    private Condition[] code;
    private int[] contaProcessi;

    public ControlloreLC(int numProcessori){
        super(numProcessori);
        code=new Condition[numProcessori];
        contaProcessi=new int[numProcessori];
        for(int i=0;i<numProcessori;i++)
        {
            code[i]=l.newCondition();
            contaProcessi[i]=0;
        }
    }

    public void allocaDischi(int d1,int d2) throws InterruptedException{
        try
        {
            l.lock();
            while(!(contaProcessi[d1]<MAX_PROCESSI_PER_DISCO)){code[d1].await();}
            contaProcessi[d1]++;
            while(!(contaProcessi[d2]<MAX_PROCESSI_PER_DISCO)){code[d2].await();}
            contaProcessi[d2]++;
        }finally{l.unlock();}
    }

    public void rilasciaDischi(int d1,int d2) throws InterruptedException{
        try
        {
            l.lock();
            contaProcessi[d1]--;
            code[d1].signalAll();
            contaProcessi[d2]--;
            code[d2].signalAll();
        }finally{l.unlock();}
    }

    public static void main(String[] args){
        Controllore c=new ControlloreLC(100);
        c.test(100);
    }
}
