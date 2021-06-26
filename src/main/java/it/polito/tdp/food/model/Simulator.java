package it.polito.tdp.food.model;

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
	
	private List<Food> listaPartenza;
	
	
	public Simulator(Graph<Food, DefaultWeightedEdge> grafo, int numeroStazioni, Food partenza, List<Food> listaPartenza) {
		super();
		this.grafo = grafo;
		this.numeroStazioni = numeroStazioni;
		this.partenza = partenza;
		this.listaPartenza = listaPartenza;
	}
	
	public void init() {
		
		this.tempoNecessario = 0;
		this.queue = new PriorityQueue<Event>();
		this.giaTrattati = new HashMap<Integer,Food>();
		
		for (int i=0; i<numeroStazioni; i++) {
			Food prossimo = this.listaPartenza.get(i);
			if (prossimo != null)
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
			
			Food prossimo = this.prossimo(corrente, this.giaTrattati); 		// il metodo mette il cibo aggiunto nella lista dei trattati e lo restituisce
			
			if (prossimo != null) { 										// ancora cibi disponibili
				double tempoProssimo = tempo(corrente,prossimo);
				this.queue.add(new Event(tempo + tempoProssimo,prossimo,numeroStazione));				
			}		
		}
	}
	
	public Food prossimo(Food f, Map<Integer,Food> trattati) {
				
		double tempoCibo = 0.0;
		Food cibo = null;
				
		for (Food f2:Graphs.neighborListOf(grafo, f)) {
			if(!trattati.containsKey(f2.getFood_code()) && this.tempo(f, f2) > tempoCibo) {
				tempoCibo = this.tempo(f, f2);
				cibo = f2;
			}
		}

		if (cibo != null) {
			trattati.put(cibo.getFood_code(), f);
			return cibo;
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
