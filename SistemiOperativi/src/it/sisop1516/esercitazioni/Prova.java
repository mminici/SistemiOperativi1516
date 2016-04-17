package it.sisop1516.esercitazioni;

import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Prova extends Thread {
	private int val;
	private int[] array1;
	private int[] array2;
	private int infBound;
	private int supBound;
	public Prova(int[] vector1, int[] vector2, int cont, int i) {
		infBound=cont;
		supBound=i;
		array1=vector1;
		array2=vector2;
		val=0;
	}
	public int getSomma(){try{join();}catch(InterruptedException e){} return val;}

	public static void main(String[] args) {
        System.out.println("Quanto devono essere lunghi gli array?");
        Scanner sc=new Scanner(System.in);
        //int arraysLength=sc.nextInt();
        int arraysLength=100000;
        System.out.println("Dimensione scelta : " + arraysLength);
        int[] vector1=new int[arraysLength];
        int[] vector2=new int[arraysLength];
        Random r=new Random();
        for(int i=0;i<arraysLength;i++)
        {
            //generate random values for arrays
            vector1[i]=r.nextInt();
            vector2[i]=r.nextInt();
        }
        LinkedList<Prova> list=new LinkedList<>();
        int cont=0;
        int offset=Math.round(arraysLength/8);
        while(cont+offset<arraysLength)
        {
            list.addLast(new Prova(vector1,vector2,cont,cont+offset));
            cont=cont+offset+1;
        }//building list of threads
        if(cont<arraysLength)
        {
            //handling length%4!=0
            list.addLast(new Prova(vector1,vector2,cont,arraysLength-1));
        }
        long currentTime=System.nanoTime();
        System.out.println("Sto lanciando "+list.size()+" Thread");
        for(Thread t:list)
        {  
            t.start();
        }
        int prodScalare=0;
        for(Prova t:list)
        {
            prodScalare+=t.getSomma();
        }
        currentTime=System.nanoTime()-currentTime;
        System.out.println("La somma è: "+prodScalare);
        System.out.println("È stato calcolato dopo "+TimeUnit.MILLISECONDS.convert(currentTime, TimeUnit.NANOSECONDS)+" millisecondi");
        prodScalare=0;
        currentTime=System.nanoTime();
        for(int j=0;j<arraysLength;j++)
        {
            prodScalare+=(vector1[j]*vector2[j]);
        }
        currentTime=System.nanoTime()-currentTime;
        System.out.println("Iterative version");
        System.out.println("La somma è: "+prodScalare);
        System.out.println("È stato calcolato dopo "+TimeUnit.MILLISECONDS.convert(currentTime, TimeUnit.NANOSECONDS)+" millisecondi");
        sc.close();
    }//main

	@Override
	public void run() {
		for(int i=infBound;i<=supBound;i++)
		{
			val+=(array1[i]*array2[i]);
		}
		
	}

}
