package it.sisop1516.concurrentMatrix;

public class ColumnThread implements Runnable {
	private int[][] matrix;
	private int columnIndex;
	
	public ColumnThread(int[][] matrix,int index){
		this.matrix=matrix;
		this.columnIndex=index;
	}
	@Override
	public void run() {
		for(int i=0;i<matrix.length;i++)
		{
			matrix[i][columnIndex]=matrix[i][columnIndex]+1;
		}
		
	}
}
