package it.sisop1516.concurrentMatrix;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicColumnThread implements Runnable {
	private AtomicInteger[][] matrix;
	private int columnIndex;
	
	public AtomicColumnThread(AtomicInteger[][] matrix,int index){
		this.matrix=matrix;
		this.columnIndex=index;
	}

	@Override
	public void run() {
		for(int i=0;i<matrix.length;i++)
		{
			matrix[i][columnIndex].incrementAndGet();
		}
		
	}
}
