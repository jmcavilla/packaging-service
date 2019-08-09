package com.mobiquityinc.packer;

import java.util.List;

public class Line {
	
	private Double maxWeight;
	private List<Package> packageList;

	public Line() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param maxWeight
	 * @param packageList
	 */
	public Line(Double maxWeight, List<Package> packageList) {
		super();
		this.maxWeight = maxWeight;
		this.packageList = packageList;
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
	public List<Package> getPackageList() {
		return packageList;
	}

	/**
	 * @param packageList the packageList to set
	 */
	public void setPackageList(List<Package> packageList) {
		this.packageList = packageList;
	}
	
}
