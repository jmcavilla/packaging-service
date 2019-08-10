package com.mobiquityinc.packer;

import java.util.List;

public class Line {
	
	private Double maxWeight;
	private List<Thing> thingsList;

	public Line() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param maxWeight
	 * @param packageList
	 */
	public Line(Double maxWeight, List<Thing> packageList) {
		super();
		this.maxWeight = maxWeight;
		this.thingsList = packageList;
	}

	/**
	 * @return the maxWeight
	 */
	public Double getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @param maxWeight the maxWeight to set
	 */
	public void setMaxWeight(Double maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @return the packageList
	 */
	public List<Thing> getThingList() {
		return thingsList;
	}

	/**
	 * @param packageList the packageList to set
	 */
	public void setThingList(List<Thing> packageList) {
		this.thingsList = packageList;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Line [maxWeight=");
		builder.append(maxWeight);
		builder.append(", thingsList=");
		builder.append(thingsList);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
