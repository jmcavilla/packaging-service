# Package Challenge
You want to send your friend a package with different things. 
Each thing you put inside the package has such parameters as index number, weight and cost. The package has a weight limit. Your goal is to determine which things to put into the package so that the total weight is less than or equal to the package limit and the total cost is as large as possible.
You would prefer to send a package which weights less in case there is more than one package with the same price.

# Input Sample
Your program should accept as its first argument a path to a filename. The input file contains several lines. Each line is one test case.
Each line contains the weight that the package can take (before the colon) and the list of things you need to choose. Each thing is enclosed in parentheses where the 1st number is a thing's index number, the 2nd is its weight and the 3rd is its cost. 

# Input Example
```
81 : (1,53.38,€45) (2,88.62,€98) (3,78.48,€3) (4,72.30,€76) (5,30.18,€9) (6,46.34,€48)
8 : (1,15.3,€34)
75 : (1,85.31,€29) (2,14.55,€74) (3,3.98,€16) (4,26.24,€55) (5,63.69,€52) (6,76.25,€75) (7,60.02,€74) (8,93.18,€35) (9,89.95,€78)
56 : (1,90.72,€13) (2,33.80,€40) (3,43.15,€10) (4,37.97,€16) (5,46.81,€36) (6,48.77,€79) (7,81.80,€45) (8,19.36,€79) (9,6.76,€64)
```
# Output sample
For each set of things that you put into the package provide a list (items’ index numbers are separated by comma). 

# Input Example
```
4
-
2,7
8,9
```
# Constraints
You should write a class com.mobiquityinc.packer.Packer with a static method named pack. This method accepts the absolute path to a test file as a String. It does return the solution as a String. Your class should throw an com.mobiquityinc.exception.APIException if incorrect parameters are being passed.

Additional constraints:
1. Max weight that a package can take is ≤ 100
2. There might be up to 15 items you need to choose from
3. Max weight and cost of an item is ≤ 100


# Packaging Challenge Solution
# English Version


## Strategy

Knowing that this is a combinatorial optimization problem (like the Knapsack problem) i choose a backtracking algorithm to prepare the solution:

    public static void fillPackage(Package base_package, Package optimal_package, List<Thing> thingsList,
			Boolean full) {

		if (full) {
			/*If the package can't carry anymore we can check if this option is best than the last one
			 * If it is the first one it will be the best one
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

## Data Structure

![](https://drive.google.com/uc?export=view&id=1F1bXdkvYXPgNZnnxoTlYoC-UMOuzWcxl)


