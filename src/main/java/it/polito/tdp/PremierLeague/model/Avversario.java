package it.polito.tdp.PremierLeague.model;

public class Avversario {
	private Player avversario;
	private int delta;

	public Avversario(Player avversario, int delta) {
		super();
		this.avversario = avversario;
		this.delta = delta;
	}

	public Player getAvversario() {
		return avversario;
	}

	public void setAvversario(Player avversario) {
		this.avversario = avversario;
	}

	public int getDelta() {
		return delta;
	}

	public void setDelta(int delta) {
		this.delta = delta;
	}

}
