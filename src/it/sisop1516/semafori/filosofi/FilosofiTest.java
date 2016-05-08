package it.sisop1516.semafori.filosofi;

public class FilosofiTest {

	public static void main(String[] args) {
		int c=0;
		while(c<Filosofo.getFilosofi())
		{
			new Thread(new Filosofo()).start();
			c++;
		}

	}

}
