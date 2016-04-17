package it.sisop1516.cc;

public class SyncCC extends ContoCorrente {
	
	public SyncCC(int depositoIniziale) {
		super(depositoIniziale);
	}
	
	@Override
	public synchronized void deposita(int importo) {
		this.deposito+=importo;

	}

	@Override
	public synchronized void preleva(int importo) {
		this.deposito-=importo;

	}

}
