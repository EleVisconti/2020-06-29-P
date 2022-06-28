package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;



public class Model {
	private Graph<Integer, DefaultWeightedEdge> grafo ;
	private List<Integer> partite ;
	private PremierLeagueDAO dao;
	private List<Adiacenza> archi;
	private ArrayList<Integer> percorsoMigliore;
	

	public List<Integer> getPartite() {
		return partite;
	}

	public void setPartite(List<Integer> partite) {
		this.partite = partite;
	}

	public Model() {
		 dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(int mese, int min) {
		grafo = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		partite = new ArrayList<>(this.dao.getVertex(mese));
		Graphs.addAllVertices(this.grafo, this.partite);
		archi = new ArrayList<Adiacenza>(this.dao.getArchi(mese, min));
		for(Adiacenza a : archi) {
			Graphs.addEdgeWithVertices(this.grafo,a.m1, a.m2, a.peso);
		}
		
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}

	public String getConnMax(int min) {
		String s="";
		double max=0;
		List<Adiacenza> squadreMax = new ArrayList<Adiacenza>();
		for(Adiacenza a : this.archi) {
		 if(a.getPeso()>max) { 
			 max=a.getPeso();
		 }
		}
		for(Adiacenza a : this.archi) {
			if(a.getPeso()==max)
			 squadreMax.add(a);
		}
		for(Adiacenza a : squadreMax) {
			s+="\n"+a.getM1()+" - "+a.getM2()+" "+max;
		}
		return s;
	}
	
	public List<Integer> trovaPercorso(Integer m1, Integer m2){
		this.percorsoMigliore = new ArrayList<>();
		List<Integer> parziale = new ArrayList<>();
		parziale.add(m1);
		//List<Integer> percorsoAmmissibile = new ArrayList<>();
		cerca(m2,  parziale);
		return this.percorsoMigliore;
	}
	
	private void cerca(Integer destinazione, List<Integer> parziale) {
		
		if(parziale.get(parziale.size()-1).equals(destinazione)) {
			if(calcolaPeso(parziale) > calcolaPeso(percorsoMigliore)) {
				this.percorsoMigliore = new ArrayList<> (parziale);
			}
			return;
		}
		
		
		for(Integer vicino : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(vicino)) {
				parziale.add(vicino);
				cerca(destinazione, parziale);
				parziale.remove(parziale.size() - 1);
			}
		}
	}

	private double calcolaPeso(List<Integer> parziale) {
		double peso = 0.0 ;
		for(int i=1; i<parziale.size(); i++) {
			double p = this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i-1), parziale.get(i))) ;
			peso += p ;
		}
		return peso ;

	}

	
	
}
