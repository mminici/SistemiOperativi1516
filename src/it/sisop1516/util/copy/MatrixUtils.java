package it.sisop1516.util.copy;

import java.util.Arrays;

public class MatrixUtils {
	/**
	 * This method prints informations about matrix consistency
	 * @param matrix 
	 * @param realVal this method works when each cell must have the same value, so each cell's value is compared with realVal
	 * @param name	  it's possible to eventually name the matrix
	 * @param eachCell	if true, the method will print each inconsistent cell indices
	 */
	public static void checkConsistency(int[][] matrix,int realVal,String name,boolean eachCell)
	{	
		int cont=0;
		for(int i=0;i<matrix.length;i++)
		{	
			for(int j=0;j<matrix[0].length;j++)
			{
				if(matrix[i][j]!=realVal){if(eachCell){System.out.println("ERRORE! cella <"+i+","+j+"> è inconsistente");}cont++;}
			}
		}
		if(name!=null)
		{
		System.out.println("\nLa matrice "+name+" ha: "+cont+" celle inconsistenti");
		return;
		}
		System.out.println("\nLa matrice ha: "+cont+" celle inconsistenti");

	}
	
	/**
	 * This method prints the matrix
	 * @param matrix
	 */
	public static void print(int[][] matrix)
	{	
		System.out.println("{");
		for(int i=0;i<matrix.length;i++)
		{	
			String s=Arrays.toString(matrix[i]);
			StringBuilder sb=new StringBuilder(s);
			sb.replace(0, 1, ""); sb.replace(s.length()-2, s.length(), "");
			System.out.println(sb.toString());
		}
		System.out.println("}");
	}
	
	/**
	 * This method initialize every matrix's cell with the same value
	 * @param matrix
	 * @param initializeValue
	 */
	public static void initialize(int[][] matrix,int initializeValue)
	{
		for(int i=0;i<matrix.length;i++)for(int j=0;j<matrix[0].length;j++)matrix[i][j]=initializeValue;
	}
	
	/**
	 * 
	 * @param sizeR
	 * @param sizeC
	 * @param initializeValue
	 * @return a matrix with <strong>sizeR</strong> rows, <strong>sizeC</strong> columns initialized with <strong>initializedValue</strong> value
	 */
	public static int[][] initialize(int sizeR,int sizeC,int initializeValue)
	{
		int[][] matrix=new int[sizeR][sizeC];
		initialize(matrix,initializeValue);
		return matrix;
	}
	
}
