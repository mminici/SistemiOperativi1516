package it.sisop1516.semafori.copy;

import it.sisop1516.util.MatrixUtils;

public class NoSemaphMatrix extends Thread {
		private int[] vector;
		private boolean inc;
		private int cont=10;
		public NoSemaphMatrix(int []vector,boolean b){
			this.vector=vector;
			inc=b;
		}
		public void run(){
			while(cont>0)
			{
			for(int i=0;i<vector.length;i++)
			{
				if(inc){vector[i]++;}
				else{vector[i]--;}
			}
			cont--;
			}
		}
		
		public static void main(String ...args) throws InterruptedException{
			int n=100;
			//build and initialize matrices to 0
			int[][] matrix=MatrixUtils.initialize(n, n, 0);
			int[][] matrixS=MatrixUtils.initialize(n, n, 0);
			//build thread arrays
			Thread[] incRows=new Thread[n]; //NThreadSafe
			Thread[] decRows=new Thread[n];  
			Thread[] incRowsS=new Thread[n]; //ThreadSafe
			Thread[] decRowsS=new Thread[n];  
			for(int i=0;i<n;i++)
			{
				incRows[i]=new NoSemaphMatrix(matrix[i],true);
				decRows[i]=new NoSemaphMatrix(matrix[i],false);
				incRowsS[i]=new SemaphMatrix(matrixS[i],true);
				decRowsS[i]=new SemaphMatrix(matrixS[i],false);
			}
			//...end building
			/*
			 * 
			 * Starting each thread in a loop and join them in another one
			 *
			 */
			for(int k=0;k<n;k++)
			{
			incRows[k].start(); incRowsS[k].start();
			decRowsS[k].start(); decRows[k].start();
			}
			for(int k=0;k<n;k++)
			{
			incRows[k].join(); incRowsS[k].join();
			decRowsS[k].join(); decRows[k].join();
			}
			//checking matrices consistency
			MatrixUtils.checkConsistency(matrix, 0, "NTS", true);
//			MatrixUtils.print(matrix);
			System.out.println("\n;....;....;....;....;....;....;....;....;....;....;....;....;");
			MatrixUtils.checkConsistency(matrixS, 0, "TS-Semaforo", false);
//			MatrixUtils.print(matrixS);
		}
}
