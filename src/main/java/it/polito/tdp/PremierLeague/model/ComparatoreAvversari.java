package it.polito.tdp.PremierLeague.model;

import java.util.Comparator;

public class ComparatoreAvversari implements Comparator<Avversario> {

	@Override
	public int compare(Avversario o1, Avversario o2) {
		return -(o1.getDelta() - o2.getDelta());
	}

}
