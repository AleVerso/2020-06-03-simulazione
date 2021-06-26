package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private PremierLeagueDAO dao;
	private Graph<Player, DefaultWeightedEdge> grafo;
	private Map<Integer, Player> idMap;
	private int bestDegree;
	private List<Player> dreamTeam;

	public Model() {
		this.dao = new PremierLeagueDAO();
		this.idMap = new HashMap<>();
	}

	public void creaGrafo(double goal) {
		// creo grafo
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		// popolo idMap
		dao.listAllPlayers(idMap);
		// aggiungo vertici
		Graphs.addAllVertices(grafo, dao.getVertices(goal, idMap));
		// aggiungo archi
		for (Adiacenza a : dao.getArchi(idMap)) {
			if (this.grafo.containsVertex(a.getP1()) && this.grafo.containsVertex(a.getP2())) {
				if (a.getPeso() < 0) {
					// arco da p2 a p1
					Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), (a.getPeso() * (-1)));
				} else if (a.getPeso() > 0) {
					// arco da p1 a p2
					Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso());
				}

			}
		}

	}

	public Graph<Player, DefaultWeightedEdge> getGrafo() {
		return grafo;
	}

	public int getVerticesSize() {
		return this.grafo.vertexSet().size();
	}

	public int getEdgeSize() {
		return this.grafo.edgeSet().size();
	}

	public BeatedPlayer FindTopPlayer() {
		int edgesMax = Integer.MIN_VALUE;
		Player top = null;

		for (Player a : this.grafo.vertexSet()) {
			if (grafo.outDegreeOf(a) > edgesMax)
				edgesMax = grafo.outDegreeOf(a);
			top = a;
		}
		BeatedPlayer result = new BeatedPlayer();
		result.setTop(top);
		List<Avversario> avv = new ArrayList<>();

		for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(result.getTop())) {
			avv.add(new Avversario(grafo.getEdgeTarget(e), (int) grafo.getEdgeWeight(e)));
		}
		Collections.sort(avv, new ComparatoreAvversari());
		result.setBattuti(avv);
		return result;
	}

	public List<Player> getDreamTeam(int k) {
		List<Player> parziale = new ArrayList<>();
		this.dreamTeam = new ArrayList<>();
		this.bestDegree = 0;

		cerca(parziale, new ArrayList<Player>(this.grafo.vertexSet()), k);

		return dreamTeam;

	}

	public void cerca(List<Player> parziale, List<Player> totale, int nGiocatori) {

		// terminazione
		if (parziale.size() == nGiocatori) {
			int grado = this.getDegree(parziale);
			if (grado > this.bestDegree) {
				dreamTeam = new ArrayList<>(parziale);
				bestDegree = grado;
			}
			// costruisco il parziale
			for (Player p : totale) {
				if (!parziale.contains(p)) {
					parziale.add(p);
					// non posso inserire i successori di p
					// sovrascrivo in una nuova lista i totali
					List<Player> rimanenti = new ArrayList<>(totale);
					rimanenti.removeAll(Graphs.successorListOf(grafo, p));
					cerca(parziale, rimanenti, nGiocatori);
					parziale.remove(p);
				}
			}

		}
	}

	private int getDegree(List<Player> team) {
		int degree = 0;
		int in;
		int out;

		for (Player p : team) {
			in = 0;
			out = 0;
			for (DefaultWeightedEdge edge : this.grafo.incomingEdgesOf(p))
				in += (int) this.grafo.getEdgeWeight(edge);

			for (DefaultWeightedEdge edge : grafo.outgoingEdgesOf(p))
				out += (int) grafo.getEdgeWeight(edge);

			degree += (out - in);
		}
		return degree;
	}

	public Integer getBestDegree() {
		return bestDegree;
	}
}
	
	
	
	
	
	

