package it.polito.tdp.food.model;

public class FoodAndWeight {
	
	Food food;
	double weight;
	
	public FoodAndWeight(Food food, double weight) {
		super();
		this.food = food;
		this.weight = weight;
	}

	public Food getFood() {
		return food;
	}

	public void setFood(Food food) {
		this.food = food;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
	
	
	
	

}
