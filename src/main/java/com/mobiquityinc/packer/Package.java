package com.mobiquityinc.packer;

import java.util.ArrayList;
import java.util.List;

public class Package {

	private Double maxWeight;
	private Double cost;
	private Double weight;
	private Integer thingsCount;
	private List<Thing> things;

	public Package() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Package(Double maxWeight, Integer thingsCount) {
		super();
		this.maxWeight = maxWeight;
		this.cost = 0.0;
		this.weight = 0.0;
		this.thingsCount = thingsCount;
		this.things = new ArrayList<Thing>();
	}

	public Double getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getWeight() {
		return weight;
	}

	public void setWeight(Double weight) {
		this.weight = weight;
	}

	public List<Thing> getThings() {
		return things;
	}

	public void setThings(List<Thing> things) {
		this.things = things;
	}

	public Integer getThingsLength() {
		return thingsCount;
	}

	public void setThingsLength(Integer thingsLength) {
		this.thingsCount = thingsLength;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((maxWeight == null) ? 0 : maxWeight.hashCode());
		result = prime * result + ((things == null) ? 0 : things.hashCode());
		result = prime * result + ((weight == null) ? 0 : weight.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (maxWeight == null) {
			if (other.maxWeight != null)
				return false;
		} else if (!maxWeight.equals(other.maxWeight))
			return false;
		if (things == null) {
			if (other.things != null)
				return false;
		} else if (!things.equals(other.things))
			return false;
		if (weight == null) {
			if (other.weight != null)
				return false;
		} else if (!weight.equals(other.weight))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Package [maxWeight=" + maxWeight + ", cost=" + cost + ", weight=" + weight + ", things=" + things + "]";
	}

	/**
	 * 
	 * if you have to clear the package, use this function...
	 * 
	 */
	public void clear() {
		this.maxWeight = 0.0;
		this.cost = 0.0;
		this.weight = 0.0;
		this.thingsCount = 0;
		this.things = new ArrayList<Thing>();
	}

	/**
	 * 
	 * if you have to add a thing to the package, use this function...
	 * 
	 * 
	 * @param thing {@link Thing}
	 * 
	 */
	public void addThing(Thing thing) {
		this.getThings().add(thing);
		this.setCost(this.getCost() + thing.getCost());
		this.setWeight(this.getWeight() + thing.getWeight());
	}

	/**
	 * 
	 * if you have to remove a thing from the package, use this function...
	 * 
	 * 
	 * @param thing {@link Thing}
	 * 
	 */
	public void removeThing(Thing thing) {
		this.getThings().remove(thing);
		this.setCost(this.getCost() - thing.getCost());
		this.setWeight(this.getWeight() - thing.getWeight());
	}
	
	
	/**
	 * 
	 * if you have to use things from another package, use this function...
	 * 
	 * 
	 * @param thing {@link Thing}
	 * 
	 */
	public void updatePackage(Package other) {
		this.clear();
		this.getThings().addAll(other.getThings());
		this.setCost(other.getCost());
		this.setWeight(other.getWeight());
		this.setMaxWeight(other.getMaxWeight());
		this.setThingsLength(this.getThings().size());
	}
	
	
	public String getThingsIndexNumbers(){
		StringBuilder info = new StringBuilder();
				
		for (int i = 0; i < this.getThings().size(); i++) {
			if(i < this.getThings().size()-1){
				info.append(this.getThings().get(i).getIndex());
				info.append(",");
			}else{
				info.append(this.getThings().get(i).getIndex());
			}
		}
		
		return info.toString();
	}

}
