package it.sisop1516.concurrentMatrix;

public class RowThread implements Runnable {
	private int[][] matrix;
	private int rowIndex;
	
	public RowThread(int[][] matrix,int index){
		this.matrix=matrix;
		this.rowIndex=index;
	}
	@Override
	public void run() {
		for(int i=0;i<matrix[rowIndex].length;i++)
		{
			matrix[rowIndex][i]=matrix[rowIndex][i]-1;
		}
		
	}

}
