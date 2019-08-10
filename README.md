# Packaging Service!
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

![](https://picasaweb.google.com/109134416379125023794/6723674838878171713#6723674841612848978)


