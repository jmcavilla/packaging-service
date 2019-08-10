package com.mobiquityinc.packer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.exception.APIException;

public class Packer {
	private static final String INDEX = "index";
	private static final String WEIGHT = "weight";
	private static final String COST = "cost";
	private static final String LINE_REGEX = "\\((?<" + INDEX + ">\\d+)\\,(?<" + WEIGHT + ">\\d+(\\.\\d{1,2})?)\\,€(?<"
			+ COST + ">\\d+(\\.\\d{1,2})?)\\)";
	private static final Double MAX_PACKAGE_WEIGHT_COST = 100.0;
	private static final Integer MAX_THINGS = 15;

	// 1. Max weight that a package can take is ≤ 100
	// 2. There might be up to 15 items you need to choose from
	// 3. Max weight and cost of an item is ≤ 100

	private Packer() {
	}

	public static void pack(String filePath) throws APIException {

		List<Package> packagesToSend = parsedInputFile(filePath);
		
		if(packagesToSend.isEmpty()){
			System.out.println("We couldn't find the best combination of things to send to your friend. Sorry :(");
		}else{
			System.out.println("RESULT: ");
			int packageIndex = 1;
			for (Package pack : packagesToSend) {
				if(pack.getThings().isEmpty()){
					System.out.println("Package " + packageIndex + ") None of the items could be add into the package");					
				}else{
					System.out.println("Package " + packageIndex+ ") " +pack.getThingsIndexNumbers());
				}
				packageIndex++;
			}
			System.out.println("---------------------------------------------------------------------------");
		}
	}
	
	/**
	 * Parse the file into {@link Line} Line objects
	 * 
	 * 
	 * @param inputPath
	 * @return
	 * @throws APIException
	 */
	private static List<Package> parsedInputFile(String inputPath) throws APIException {
		List<Package> packagesToSend = new ArrayList<Package>();
		List<Line> lines = new ArrayList<>();

		try (FileInputStream inputStream = new FileInputStream(inputPath)) {
			Scanner scanner = new Scanner(inputStream);
			int lineIndex = 1;
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				lines.add(parseLine(line, lineIndex));
				lineIndex++;
			}
			for (Line line : lines) {

				Package base_package = new Package(line.getMaxWeight(), line.getThingList().size());
				Package optimal_package = new Package(line.getMaxWeight(), line.getThingList().size());

				fillPackage(base_package, optimal_package, line.getThingList(), false);
				
				packagesToSend.add(optimal_package);
			}

			scanner.close();

		} catch (IOException e) {
			throw new APIException("We couldn't find the file that you send to us, please verify: " + inputPath, e);
		}

		return packagesToSend;
	}

	/**
	 * Parse each line of the file into {@link Line} Line objects
	 * 
	 * 
	 * @param lineText
	 * @return
	 * @throws APIException 
	 */
	private static Line parseLine(String lineText,Integer lineIndex) throws APIException {
		//System.out.println("We will verify the line number "+ lineIndex);
		List<Thing> things = new ArrayList<>();
		String[] parseLine = lineText.split(":");
		Double packageMaxWeight = 0.0;
		String text = "";
		if (parseLine != null && parseLine.length > 1) {
			 try {
				 packageMaxWeight = Double.parseDouble(parseLine[0]);
	        } catch (NumberFormatException e) {
	            throw new APIException("The max weight of the package must be a number", e);
	        }
			
			text = parseLine[1];
			
			if(text.isEmpty()){
				throw new APIException("You must send us a list of things that you want to verify...");
			}
		}else{
			System.out.println("Please, check the line " + lineIndex + " because it could be empty...");
			System.out.println("---------------------------------------------------------------------------");
		}
		
		if(packageMaxWeight > MAX_PACKAGE_WEIGHT_COST){
			throw new APIException("The maximum weight that a package can carry is "+ MAX_PACKAGE_WEIGHT_COST +". You are trying with: " + packageMaxWeight);
		}

		Pattern p = Pattern.compile(LINE_REGEX);
		Matcher m = p.matcher(text);
		while (m.find()) {
			Integer index = Integer.valueOf(m.group(INDEX));
			Double weight = Double.valueOf(m.group(WEIGHT));
			Double cost = Double.valueOf(m.group(COST).replace("€", ""));
			
			
			if(weight > MAX_PACKAGE_WEIGHT_COST){
				throw new APIException("The maximum weight that a package can carry is "+ MAX_PACKAGE_WEIGHT_COST +". The " + index + " item weigt it's greater than the maximum:  "+ weight);
			}
			
			if(cost > MAX_PACKAGE_WEIGHT_COST){
				throw new APIException("The maximum cost that a item can be valuated is "+ MAX_PACKAGE_WEIGHT_COST +". The " + index + " item cost it's greater than the maximum:  "+ cost);
			}
			
			
			Thing parsedPackage = new Thing(index, weight, cost);
			things.add(parsedPackage);
		}
		
		if(things.size() > MAX_THINGS){
			throw new APIException("The maximum things a package can carry is" + MAX_THINGS + ". You are trying with: " + packageMaxWeight);
		}
		

		return new Line(packageMaxWeight, things);
	}

	/**
	 * Fill the package choosing the best combination from the things that
	 * receives with backtracking
	 * 
	 * 
	 * @param base_package
	 * @param optimal_package
	 * @param thingsList
	 * @param full
	 */
	public static void fillPackage(Package base_package, Package optimal_package, List<Thing> thingsList,
			Boolean full) {

		if (full) {
			/*If the package can't carry anymore we can check if this option is best than the last one
			 * If it is the first one it will be the best one :D
			 */
			if (base_package.getCost() > optimal_package.getCost()
					|| (base_package.getCost() == optimal_package.getCost()
							&& base_package.getWeight() < optimal_package.getWeight())) {
				optimal_package.updatePackage(base_package);
			}

		} else {
			for (Thing thing : thingsList) {
				
				//We check if the item it's already in the package, if it's not we can continue
				 
				if (!base_package.getThings().contains(thing)) {
					//Now we are checking if the mac weight is reached, if it's not we can keep adding other things
					if (base_package.getMaxWeight() > base_package.getWeight() + thing.getWeight()) {
						base_package.addThing(thing);
						fillPackage(base_package, optimal_package, thingsList, false);
						base_package.removeThing(thing);
					} else {
						//If it's full, we have to decide if it is the best option
						fillPackage(base_package, optimal_package, thingsList, true);
					}
				}
			}
		}

	}
}
