package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	private Graph<Food,DefaultWeightedEdge> grafo; 
	private Map<Integer,Food> idMap; 
	private FoodDao dao;
	private Simulator sim;
	
	private List<Food> partenza;
			
	public Model() {
		this.dao = new FoodDao();
	}

	public void creaGrafo(int qty) { 

		idMap = new HashMap<Integer,Food>();
		
		for (Food o:dao.listAllFoods(qty)) { 
			idMap.put(o.getFood_code(), o); 
		}
		
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
			
		Graphs.addAllVertices(grafo, idMap.values()); 
			
		for(Adiacenza a:dao.getAdiacenze(idMap)) { 
			Graphs.addEdge(this.grafo, a.getF1(), a.getF2(), a.getPeso());
		}	
			
	}
	public List<Food> vertici() {
			
		TreeMap<String,Food> map = new TreeMap<String,Food>();
			
		for (Food o:this.grafo.vertexSet()) 
			map.put(o.getDisplay_name(), o);

		return new ArrayList<Food>(map.values());
	}
		
	public int numeroArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<FoodAndWeight> migliori(Food f) {
		
		ArrayList<FoodAndWeight> map = new ArrayList<FoodAndWeight>();
					
		for (Food f2:Graphs.neighborListOf(grafo, f)) {
			map.add(new FoodAndWeight(f2, this.grafo.getEdgeWeight(grafo.getEdge(f, f2))));
		}
		
		Collections.sort(map, new Comparator<FoodAndWeight>() {
			public int compare(FoodAndWeight o1, FoodAndWeight o2) {
			if (o2.weight - o1.weight < 0) 
				return -1;
			else  			
				return 1;
		}
		}); 
		return map;
	}
	
	public void simula(int numeroStazioni, Food partenza) {
		
		this.partenza = new ArrayList<>();
		
		for (FoodAndWeight f:migliori(partenza))
			this.partenza.add(f.getFood());		
		
		this.sim = new Simulator(this.grafo, numeroStazioni, partenza, this.partenza);
		sim.init();
	}
	
	public double getTempo(){
		return sim.getTempo();
	}
	
	public int cibiPreparati() {
		return sim.getCibiPreparati();
	}

}
