package com.mobiquityinc.packer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mobiquityinc.exception.APIException;

public class Packer {

  private Packer() {
  }

  public static String pack(String filePath) throws APIException {
	  
	  final String REG_EXP = "\\(([^)]+)\\)";
	  
	  try {
		    ArrayList<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(filePath)));
		    
		    
		    
		    for (String string : lines) {
				System.out.println(string);
				
				Pattern p = Pattern.compile(REG_EXP);
				Matcher m = p.matcher(string);
				while(m.find()) {
				    System.out.println(m.group(1));
				    
				    
				    
				}
				
			}
		    
		    
		    
		}
		catch (IOException e) {
		    // Handle a potential exception
		}
	  
    return null;
  }
}
