package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

public class Simulator {
	
	private Graph<Food,DefaultWeightedEdge> grafo; 
	private int numeroStazioni;
	private Food partenza;
	
	private double tempoNecessario;
	private PriorityQueue<Event> queue;
	private Map<Integer,Food> giaTrattati;
	
	
	public Simulator(Graph<Food, DefaultWeightedEdge> grafo, int numeroStazioni, Food partenza, Map<Double,Food> viciniPartenza) {
		super();
		this.grafo = grafo;
		this.numeroStazioni = numeroStazioni;
		this.partenza = partenza;
		this.giaTrattati = new HashMap<Integer,Food>();
	}
	
	public void init() {
		this.tempoNecessario = 0;
		this.queue = new PriorityQueue<Event>();
		
		for (int i=0; i<numeroStazioni; i++) {
			Food prossimo = this.prossimo(partenza, this.giaTrattati);
			this.queue.add(new Event(tempo(partenza,prossimo),prossimo,i+1));
		}
		this.run();
	}
	
	private void run() {
		
		Event e;
		
		while((e = this.queue.poll())!=null) {  
			
			Double tempo = e.getTempo();	
			Food corrente = e.getFood();
			int numeroStazione = e.getNumeroStazione();
			
			this.tempoNecessario = tempo;
			
			//System.out.println(e.toString());
			
			Food prossimo = this.prossimo(corrente, this.giaTrattati); 		// il metodo mette il cibo aggiunto nella lista dei trattati e lo restituisce
			
			if (prossimo != null) { 										// ancora cibi disponibili
				double tempoProssimo = tempo(corrente,prossimo);
				this.queue.add(new Event(tempo + tempoProssimo,prossimo,numeroStazione));				
			}		
		}
	}
	
	public Food prossimo(Food f, Map<Integer,Food> trattati) {
		
		TreeMap<Double,Food> map = new TreeMap<Double,Food>(Collections.reverseOrder());
		double tempoCibo = 0.0;
				
		for (Food f2:Graphs.neighborListOf(grafo, f)) {
			if(!trattati.containsKey(f2.getFood_code())) {
				tempoCibo = this.tempo(f, f2);
				map.put(tempoCibo, f2);
			}
		}

		if (map.size()>0) {
			Food daAggiungere = map.get(tempoCibo);
			this.giaTrattati.put(daAggiungere.getFood_code(), f);
			return daAggiungere;
		} else
			return null;
	}
	
	public Double tempo (Food f1, Food f2) {
		return this.grafo.getEdgeWeight(this.grafo.getEdge(f1, f2));
	}
	
	public Double getTempo() {
		return this.tempoNecessario;
	}
	
	public int getCibiPreparati() {
		return this.giaTrattati.size();
	}
	
}
