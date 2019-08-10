package com.mobiquityinc;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.Packer;

public class PackagingService {

	public PackagingService() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

			if(args.length == 0){
				System.out.println("You have to specify one or more files to check...");
			}else{
				//System.out.println("We will try to find the best option for you. Let's start. . .");
				for (int i = 0; i < args.length; i++) {
					System.out.println("CHECKING FILE " + args[i]);
					System.out.println("---------------------------------------------------------------------------");
					try {
						Packer.pack(args[i]);
					} catch (APIException e) {
						System.out.println(e.getMessage());
					}
				}
			}
	}

}
