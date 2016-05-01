package it.sisop1516.stringspace;

public class GenericNumericStringSpace extends StringSpace {
	@SuppressWarnings("unused")
	private int infBound,supBound;
	public GenericNumericStringSpace(int length,int infBound,int supBound)
	{	
		if(length<2){throw new IllegalArgumentException("Not acceptable key length");}
		if(infBound>=supBound){throw new IllegalArgumentException("Not acceptable bound values");}
		this.length=length;
		this.infBound=infBound;
		this.supBound=supBound;
		currentString=String.valueOf(infBound);
		while(currentString.length()<length)
		{
			currentString="0"+currentString;
		}
		
	}
	
	@Override
	public void nextString() throws ExceededStringBoundException {
		int i=new Integer(currentString); i++;
		if(i>supBound){throw new ExceededStringBoundException("Exceeded bound limits",new RuntimeException());}
		currentString=String.valueOf(i);
		while(currentString.length()<length)
		{
			currentString="0"+currentString;
		}
	}

}
