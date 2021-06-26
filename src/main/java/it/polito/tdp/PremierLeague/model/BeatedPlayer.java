package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class BeatedPlayer {
	private Player top;
	private List<Avversario> battuti;
	
	public BeatedPlayer(Player top, List<Avversario> battuti) {
		super();
		this.top = top;
		this.battuti = battuti;
	}
	public BeatedPlayer() {
		// TODO Auto-generated constructor stub
	}
	public Player getTop() {
		return top;
	}
	public void setTop(Player top) {
		this.top = top;
	}
	public List<Avversario> getBattuti() {
		return battuti;
	}
	public void setBattuti(List<Avversario> battuti) {
		this.battuti = battuti;
	}
	
	

}
