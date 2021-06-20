package it.polito.tdp.food.model;

public class Event implements Comparable<Event>{
	
	private Double tempo; 				// tempo di progressione della stazione
	private Food food; 					// cibo in lavorazione al momento
	private Integer numeroStazione; 	// numero della stazione
		
	@Override
	public String toString() {
		return "Event [tempo=" + tempo + ", food=" + food + ", numeroStazione=" + numeroStazione + "]";
	}

	public Event(Double tempo, Food food, Integer numeroStazione) {
		super();
		this.tempo = tempo;
		this.food = food;
		this.numeroStazione = numeroStazione;
	}

	public Double getTempo() {
		return tempo;
	}

	public void setTempo(Double tempo) {
		this.tempo = tempo;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public Integer getNumeroStazione() {
		return numeroStazione;
	}

	public void setNumeroStazione(Integer numeroStazione) {
		this.numeroStazione = numeroStazione;
	}

	@Override
	public int compareTo(Event other) { 
		return (int)(this.tempo - other.tempo);
	}
	
}

