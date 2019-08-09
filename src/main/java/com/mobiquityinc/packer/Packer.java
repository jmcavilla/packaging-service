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
	private static final String PACKAGE_REGEX = "\\((?<" + INDEX + ">\\d+)\\,(?<" + WEIGHT
			+ ">\\d+(\\.\\d{1,2})?)\\,€(?<" + COST + ">\\d+(\\.\\d{1,2})?)\\)";

	private Packer() {
	}


	public static String pack(String filePath) throws APIException {

		parsedInputFile(filePath);


		return null;
	}


	private static Line parseLine(String string) {
		List<Package> packages = new ArrayList<>();
		System.out.println(string);

		Pattern p = Pattern.compile(PACKAGE_REGEX);
		Matcher m = p.matcher(string);
		while (m.find()) {
			//TODO: Prepare validations for the package

			Package parsedPackage = new Package(Integer.valueOf(m.group(INDEX)),
					Double.valueOf(m.group(WEIGHT)), Double.valueOf(m.group(COST)));
			
			
			packages.add(parsedPackage);

		}
		return null;
	}
	
	private static List<Line> parsedInputFile(String inputPath) throws APIException {
        List<Line> lines = new ArrayList<>();

        try (FileInputStream inputStream = new FileInputStream(inputPath)) {
            try (Scanner scanner = new Scanner(inputStream)) {
                for (long lineId = 0; scanner.hasNext(); lineId++) {
                    String line = scanner.nextLine();
                    lines.add(parseLine(line));
                }
            }
        } catch (IOException e) {
            throw new APIException(e.getMessage(), e);
        }

        return lines;
    }
}
