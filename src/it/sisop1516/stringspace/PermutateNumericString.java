package it.sisop1516.stringspace;

public class PermutateNumericString extends StringSpace {
	/**
	 * @author marco
	 * Questa classe genera tutte le possibili permutazioni di n=supBound-infBound
	 * numeri lunghe length
	 */
		private byte[] vector; //contains characters of randomString
		private byte infBound,supBound;
		
		public PermutateNumericString(int length,byte infBound,byte supBound)
		{	
			if(length<2){throw new IllegalArgumentException("Not acceptable key length");}
			if(infBound>=supBound){throw new IllegalArgumentException("Not acceptable bound values");}
			this.length=length;
			this.supBound=supBound;
			this.infBound=infBound;
			vector=new byte[length];
			currentString="";
			for(int i=0;i<vector.length;i++){vector[i]=infBound; currentString=String.valueOf(infBound)+currentString;}
		}
		
		
		
		public void nextString() throws ExceededStringBoundException
		{	
			vector[vector.length-1]=(byte)(vector[vector.length-1]+1);
			if(vector[vector.length-1]==(byte)(supBound+1))
			{
				vector[vector.length-1]=infBound;
				vector[vector.length-2]=(byte)(vector[vector.length-2]+1);
				for(int i=(int)vector.length-2;i>0;i--)
				{
					if(vector[i]==(byte)(supBound+1))
					{
						vector[i]=infBound;
						vector[i-1]=(byte)(vector[i-1]+1);
					}
				}
				if(vector[0]==(byte)(supBound+1)){throw new ExceededStringBoundException("Exceeded bound limit",new RuntimeException());}
			}
			currentString="";
			for(int j=0;j<vector.length;j++){currentString=currentString+String.valueOf(vector[j]);}
			
		}
		
		public static void main(String [] args)
		{
			PermutateNumericString s=new PermutateNumericString(3,(byte)0,(byte)9);
			int cont=0;
			while(true){
				System.out.println(s.getString());
				System.out.println();
				try {
					cont++;
					s.nextString();
				} catch (Exception e) {
					System.out.println("quanti?"+cont);break;
				}
			}
		}
}
