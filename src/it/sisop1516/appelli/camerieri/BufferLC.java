package it.sisop1516.appelli.camerieri;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BufferLC extends Buffer {

	private Lock l=new ReentrantLock();
	private Condition voglioMettere=l.newCondition();
	private Condition voglioPrelevare=l.newCondition();
	
	public BufferLC(int tipo) {
		super(tipo);
	}

	@Override
	protected int get() throws InterruptedException {
		l.lock();
		try
		{
			while(numPiatti==0)
			{
				voglioPrelevare.await();
			}
			numPiatti--;
			voglioMettere.signalAll();
		}finally{l.unlock();}
		return 1;
	}

	@Override
	protected void put() throws InterruptedException {
		l.lock();
		try
		{
			while(numPiatti==maxPiatti)
			{
				voglioMettere.await();
			}
			numPiatti++;
			voglioPrelevare.signalAll();
		}finally{l.unlock();}

	}

}
