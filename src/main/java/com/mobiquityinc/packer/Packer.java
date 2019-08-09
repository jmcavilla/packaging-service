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
	private static final String LINE_REGEX = "\\((?<" + INDEX + ">\\d+)\\,(?<" + WEIGHT
			+ ">\\d+(\\.\\d{1,2})?)\\,€(?<" + COST + ">\\d+(\\.\\d{1,2})?)\\)";
//	private static final String WEIGHT_REGEX = "/(?<weight>\\d+)\\:(?<data>\\w+)/g";
	private static final String WEIGHT_REGEX = "\\d*:\\d*";
	


	private Packer() {
	}


	public static String pack(String filePath) throws APIException {

		parsedInputFile(filePath);


		return null;
	}


	private static Line parseLine(String lineText) {
		List<Package> packages = new ArrayList<>();
		System.out.println(lineText);
		String[] parseLine = lineText.split(":");
		String packageMaxWeight = "";
		String text = "";
		if(parseLine != null && parseLine.length > 0) {
			packageMaxWeight = parseLine[0];
			text = parseLine[1];
			System.out.println(packageMaxWeight + " " + text);
		}

		Pattern p = Pattern.compile(LINE_REGEX);
		Matcher m = p.matcher(text);
		while (m.find()) {
			//TODO: Prepare validations for the package

			Package parsedPackage = new Package(Integer.valueOf(m.group(INDEX)),
					Double.valueOf(m.group(WEIGHT)), Double.valueOf(m.group(COST)));
			
			
			packages.add(parsedPackage);

		}
		
		
		return new Line(Double.valueOf(packageMaxWeight), packages);
	}
	
	private static List<Line> parsedInputFile(String inputPath) throws APIException {
        List<Line> lines = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(inputPath)) {
        	Scanner scanner = new Scanner(inputStream);
        	
        	
            while(scanner.hasNext()) {
                String line = scanner.nextLine();
                lines.add(parseLine(line));
            }
            for (Line line : lines) {
				
            	System.out.println(line.toString());
			}
            
        } catch (IOException e) {
            throw new APIException(e.getMessage(), e);
        }

        return lines;
    }
}
