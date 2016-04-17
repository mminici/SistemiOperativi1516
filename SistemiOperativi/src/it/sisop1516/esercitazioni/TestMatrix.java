package it.sisop1516.esercitazioni;


import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import it.sisop1516.concurrentMatrix.AtomicColumnThread;
import it.sisop1516.concurrentMatrix.AtomicRowThread;
import it.sisop1516.concurrentMatrix.ColumnThread;
import it.sisop1516.concurrentMatrix.RowThread;

public class TestMatrix {

	public static void main(String[] args) throws InterruptedException {
		System.out.println("int version-NTS");
		int[][] intMatrix=new int[100][100];
		//initialize intMatrix
		for(int l=0;l<intMatrix.length;l++) for(int p=0;p<intMatrix[l].length;p++) intMatrix[l][p]=0;
		LinkedList<Thread> list=new LinkedList<>();
		for(int i=0;i<intMatrix.length;i++) //loop to generate threads
		{
			//rows
			Thread t=new Thread(new RowThread(intMatrix,i));
			list.addLast(t);
			for(int j=0;j<intMatrix[0].length;j++)
			{
				//columns
				Thread th=new Thread(new ColumnThread(intMatrix,j));
				list.addLast(th);
			}
		}
		long currentTime=System.nanoTime();
		for(Thread t:list)
		{
			t.start();
		}
		for(Thread t:list)
		{
			t.join();
		}
		currentTime=System.nanoTime()-currentTime;
//		System.out.println("Matrix after threads finished");
//		for(int k=0;k<intMatrix.length;k++)
//		{
//			System.out.println(Arrays.toString(intMatrix[k]));
//		}
		System.out.println("Calcolato dopo: "+TimeUnit.MILLISECONDS.convert(currentTime, TimeUnit.NANOSECONDS)+" millisecondi");
		System.out.println();
		
		AtomicInteger[][] atomicMatrix=new AtomicInteger[100][100];
		//initialize matrix
		for(int u=0;u<atomicMatrix.length;u++) for(int y=0;y<atomicMatrix[u].length;y++) atomicMatrix[u][y]=new AtomicInteger(0);
		LinkedList<Thread> atomicList=new LinkedList<>();
		for(int e=0;e<intMatrix.length;e++)//loop to generate threads
		{
			//rows
			Thread t=new Thread(new AtomicRowThread(atomicMatrix,e));
			atomicList.addLast(t);
			for(int r=0;r<intMatrix[0].length;r++)
			{
				//columns
				Thread th=new Thread(new AtomicColumnThread(atomicMatrix,r));
				atomicList.addLast(th);
			}
		}
		currentTime=System.nanoTime();
		for(Thread t:atomicList)
		{
			t.start();
		}
		for(Thread t:atomicList)
		{
			t.join();
		}
		currentTime=System.nanoTime()-currentTime;
		System.out.println("AtomicInteger version-TS");
//		System.out.println("Matrix after threads finished");
//		for(int q=0;q<intMatrix.length;q++)
//		{
//			System.out.println(Arrays.toString(atomicMatrix[q]));
//		}
		System.out.println("Calcolato dopo: "+TimeUnit.MILLISECONDS.convert(currentTime, TimeUnit.NANOSECONDS)+" millisecondi");
		for(int a=0;a<intMatrix.length;a++) //loop to check errors
		{
			for(int b=0;b<intMatrix[a].length;b++)
			{
				if(intMatrix[a][b]!=(atomicMatrix[a][b].get())) System.out.print("ERRORE! Cella#"+a+","+b+" \n"+"Valore intMatrix: "+intMatrix[a][b]+"\nValore atomicMatrix: "+atomicMatrix[a][b].get()+"\n");
			}
		}
	}

}
