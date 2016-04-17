package it.sisop1516.cc;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerCC extends ContoCorrente {
	private AtomicInteger deposito;
	
	public AtomicIntegerCC(int depositoIniziale) {
		super(depositoIniziale);
		deposito=new AtomicInteger(depositoIniziale);
	}

	@Override
	public void deposita(int importo) {
		deposito.addAndGet(importo);
	}

	@Override
	public void preleva(int importo) {
		deposito.addAndGet(-importo);
	}
	
	public int getDeposito(){return deposito.get();}

}
