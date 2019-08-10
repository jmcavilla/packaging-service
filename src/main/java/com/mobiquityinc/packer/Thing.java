package com.mobiquityinc.packer;

public class Thing {

	private Integer index;
	private Double weight;
	private Double cost;

	public Thing() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param index
	 * @param weight
	 * @param cost
	 */
	public Thing(Integer index, Double weight, Double cost) {
		super();
		this.index = index;
		this.weight = weight;
		this.cost = cost;
	}

	/**
	 * @return the index
	 */
	public Integer getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(Integer index) {
		this.index = index;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the cost
	 */
	public Double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(Double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Package [INDEX = ");
		builder.append(getIndex());
		builder.append(", WEIGHT = ");
		builder.append(getWeight());
		builder.append(", COST = ");
		builder.append(getCost());
		builder.append("]");
		return builder.toString();
	}
	
	

}
