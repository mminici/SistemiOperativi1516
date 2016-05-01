package it.sisop1516.concurrentMatrix;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicRowThread implements Runnable {
		private AtomicInteger[][] matrix;
		private int rowIndex;
		
		public AtomicRowThread(AtomicInteger[][] matrix,int index)
		{
			this.matrix=matrix;
			this.rowIndex=index;
		}

		@Override
		public void run() {
			for(int i=0;i<matrix[rowIndex].length;i++)
			{
				matrix[rowIndex][i].decrementAndGet();
			}
			
		}
}
