package it.sisop1516.appelli.laghetto;

public abstract class Laghetto {
	public int minPesci,maxPesci,numPesci,numPescatori,numAddetti;
	
	public abstract void inizia(int t);
	
	public abstract void finisci(int t);
	
	protected int getMin(){return minPesci;}
	protected int getMax(){return maxPesci;}
	protected int getNum(){return numPesci;}
	
	protected void test(int numPescatori,int numAddetti)
	{
		Pescatore[] ps=new Pescatore[numPescatori];
		Addetto[] as=new Addetto[numAddetti];
		for(int i=0;i<ps.length;i++)
		{
			ps[i]=new Pescatore(200,800,1000,this);
		}
		for(int i=0;i<as.length;i++)
		{
			as[i]=new Addetto(300,600,3000,this);
		}
		for(Thread t:ps)t.start();
		for(Thread t:as)t.start();
	}
}
