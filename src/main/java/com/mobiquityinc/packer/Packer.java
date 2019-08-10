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

	private Packer() {
	}

	public static String pack(String filePath) throws APIException {

		parsedInputFile(filePath);

		return null;
	}

	private static Line parseLine(String lineText) {
		List<Thing> things = new ArrayList<>();
		System.out.println(lineText);
		String[] parseLine = lineText.split(":");
		String packageMaxWeight = "";
		String text = "";
		if (parseLine != null && parseLine.length > 0) {
			packageMaxWeight = parseLine[0];
			text = parseLine[1];
		}

		Pattern p = Pattern.compile(LINE_REGEX);
		Matcher m = p.matcher(lineText);
		while (m.find()) {
			// TODO: Prepare validations for the line
			Thing parsedPackage = new Thing(Integer.valueOf(m.group(INDEX)), Double.valueOf(m.group(WEIGHT)),
					Double.valueOf(m.group(COST).replace("€", "")));
			things.add(parsedPackage);

		}

		return new Line(Double.valueOf(packageMaxWeight), things);
	}

	private static List<Line> parsedInputFile(String inputPath) throws APIException {
		List<Line> lines = new ArrayList<>();

		try (FileInputStream inputStream = new FileInputStream(inputPath)) {
			Scanner scanner = new Scanner(inputStream);

			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				lines.add(parseLine(line));
			}
			for (Line line : lines) {

				Package base_package = new Package(line.getMaxWeight(), line.getThingList().size());
				Package optimal_package = new Package(line.getMaxWeight(), line.getThingList().size());

				fillPackage(base_package, optimal_package, line.getThingList(), false);

				System.out.println("ASDASDD " + optimal_package.toString());

			}

			scanner.close();

		} catch (IOException e) {
			throw new APIException(e.getMessage(), e);
		}

		return lines;
	}

	public static void fillPackage(Package base_package, Package optimal_package, List<Thing> thingsList,
			Boolean full) {

		if (full) {

			if (base_package.getCost() > optimal_package.getCost()) {
				optimal_package.clear();
				optimal_package.getThings().addAll(base_package.getThings());
				optimal_package.setCost(base_package.getCost());
				optimal_package.setWeight(base_package.getWeight());
				optimal_package.setMaxWeight(base_package.getMaxWeight());
				optimal_package.setThingsLength(optimal_package.getThings().size());
			}

		} else {
			for (Thing thing : thingsList) {
				if (!base_package.getThings().contains(thing)) {
					if (base_package.getMaxWeight() > base_package.getWeight() + thing.getWeight()) {
						base_package.addThing(thing);
						fillPackage(base_package, optimal_package, thingsList, false);
						base_package.removeThing(thing);
					} else {
						fillPackage(base_package, optimal_package, thingsList, true);
					}
				}
			}
		}

	}
}
