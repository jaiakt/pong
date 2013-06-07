package net.srivastav;

public class Singleton {

	private static final Singleton singleton = new Singleton();
	
	public int winner;
	public int difficulty;

	private Singleton() {
	}


	public static Singleton getInstance() {
		return singleton;
	}
}