package shopping;
//import character.Items;	//uncomment when Items class is made
import character.Character_Class;
import java.util.Scanner;

public class Shopping {

	private shopWeaponItem[] weaponsStock = {new shopWeaponItem("Sword Weapon", 350, "Weapon", 1, 1), new shopWeaponItem("Knife Weapon", 50, "Weapon", 2, 2), new shopWeaponItem("Dagger Weapon", 125, "Weapon", 3, 3), new shopWeaponItem("Mace Weapon", 350, "Weapon", 4, 4)}; 
	private shopWeaponItem[] itemsStock = {new shopWeaponItem("Health Potion", 150, "Item", 1, 1), new shopWeaponItem("Mana Potion", 150, "Item", 2, 2), new shopWeaponItem("Antidote", 100, "Item", 3, 3), new shopWeaponItem("Energy Restore", 225, "Item", 4, 4), new shopWeaponItem("Burn Heal", 250, "Item", 5, 5)};	
	private shopWeaponItem[] armorStock = {new shopWeaponItem("Dirty Rags", 100, "Armor", 1, 0), new shopWeaponItem("Layered Silk", 100, "Armor", 2,10), new shopWeaponItem("Plate Armor", 100, "Armor", 3, 20)};
	
	static Scanner input = new Scanner(System.in);
	private String response = null;
	private boolean currentlyHere, browsing = false; //using it for while loop so I don't have to use recursion
	
	
	
	public void store(Character_Class person) throws Exception{
		if (currentlyHere == false){
			System.out.println("Welcome to my store. Take a look at my wares.");
			System.out.println("You are level " + person.getLevel() + " and you currently have $" + person.getMoney() + "\n");
			try{
				Thread.sleep(1000);
			}
			catch(InterruptedException ie){
				ie.printStackTrace();
			}
		}
		
		stockShop();
		
		currentlyHere = true;
		while (currentlyHere == true){
			System.out.println("Weapons (1) / Consumables (2) / Armor (3) / View Backpack (4) /Exit Store (9)");
			response = input.nextLine();
			
			if (response.equals("1"))
				browse(weaponsStock,person);
			else if (response.equals("2"))
				browse(itemsStock,person);
			else if (response.equals("3"))
				browse(armorStock, person);
			else if (response.equals("4"))
				person.backpack.viewInventory();
			else if (response.equals("9"))
				exitStore();
			else
				System.out.println("Invalid input.");
			
		}
		
	}
	
	/*
	 *  Stock Shop
	 */
	
	public void stockShop(){
		setWeaponsStock();
		setItemsStock();
	}
	
	/*
	 * Browse
	 */
	/*
	//Displays weapons then switch statement to handle player choices
	public void browseWeapons(Character_Class person) throws Exception{
		System.out.println("Weapons:"); // (Number to Type) [Price] - [Weapons]
		browsing = true;
			
		while (browsing == true){    //using placeholder strings for parameters
			displayStock(weaponsStock, person);
			response = input.nextLine();
			switch (response.toString()){
			
				case "0":	buyItem(person, weaponsStock[0].getName(), weaponsStock[0].getPrice(), weaponsStock[0].getRequiredLevel()); //Sword
							break;
				case "1":	buyItem(person, weaponsStock[1].getName(), weaponsStock[1].getPrice(), weaponsStock[1].getRequiredLevel()); //Knife
							break;
				case "2":	buyItem(person, weaponsStock[2].getName(), weaponsStock[2].getPrice(), weaponsStock[2].getRequiredLevel()); //Dagger
							break;
				case "3":	buyItem(person, weaponsStock[3].getName(), weaponsStock[3].getPrice(), weaponsStock[3].getRequiredLevel()); //Mace
							break;
				case "4":	buyItem(person, weaponsStock[4].getName(), weaponsStock[4].getPrice(), weaponsStock[4].getRequiredLevel()); //Broken Stick
							break;
				case "9":
				case "exit":
				case "back": browsing = false;
							break;
				default: 
					System.out.println("Invalid input.");
			}
		}	
	}
	
	
	//Displays weapons then switch statement to handle player choices
	public void browseItems(Character_Class person) throws Exception{
		System.out.println("Items:"); // (Number to Buy) [Price] - [Item]
		browsing = true;
		
		while (browsing == true){   //using placeholder strings for parameters
			displayStock(itemsStock, person);
			response = input.nextLine();
			switch (response.toString()){
				case "0":	buyItem(person, itemsStock[0].getName(), itemsStock[0].getPrice(), itemsStock[0].getRequiredLevel()); //"Health Potion"
							break;
				case "1":	buyItem(person, itemsStock[1].getName(), itemsStock[1].getPrice(), itemsStock[1].getRequiredLevel()); //"Mana Potion"
							break;
				case "2":	buyItem(person, itemsStock[2].getName(), itemsStock[2].getPrice(), itemsStock[2].getRequiredLevel()); //"Antidote"
							break;
				case "3":	buyItem(person, itemsStock[3].getName(), itemsStock[3].getPrice(), itemsStock[3].getRequiredLevel()); //"Energy Restore"
							break;
				case "4":	buyItem(person, itemsStock[4].getName(), itemsStock[4].getPrice(), itemsStock[4].getRequiredLevel()); //"Burn Repel"
							break;
				case "9":
				case "exit":
				case "back": browsing = false;
							break;
				default: System.out.println("Invalid input.");
			}	
		}
		
	}
	*/
	//Trying to create general browse method. Using if else statements instead. Much more dynamic in terms of things to compare with.
	//basic browse guide
	
	/*While browsing is true
	 * 		display items
	 * 		ask for response (implicitly)
	 * 		convert response to int
	 * 		if answer is in range of index with passed array, buy item
	 * 		else if answer is 9, exit, or back, back up one level
	 * 		else, invalid answer
	 */
	
	
	
	public void browse(shopWeaponItem[] stock, Character_Class person) throws Exception {
		System.out.println(stock[0].getType() + " :"); //using the first element of the array to grab the type of items the player is browsing.
		browsing = true;
		
		while (browsing == true) {
			displayStock(stock, person);
			response = input.nextLine();
			int responseInt = Integer.parseInt(response); //using as array index
			
			if (responseInt >= 0 && responseInt <= stock.length) //if index is in range; purchase item
				buyItem(person, stock[responseInt].getName(), stock[responseInt].getPrice(), stock[responseInt].getRequiredLevel());
			else if (responseInt == 9 || response.toString() == "back" || response.toString() == "exit")// Leave browsing menu
				browsing = false;
			else if (responseInt < 1 || responseInt > stock.length +1) //response outside of array range
				System.out.println("Don't have anything on that shelf yet. Anything else?");
			else
				System.out.println("Invalid input");
		}
	}
	
	
	
	/*
	 * Display Stock
	 */
	
	public void displayStock(shopWeaponItem[] stock, Character_Class person) throws Exception{ //used for weapons and items
		System.out.println();
		for(int index = 0; index < stock.length; index++){
			//if (person.getLevel() >= stock[index].requiredLevel)  In case we want to not show unavailable items
			System.out.print("("+ index +") "+  stock[index].getPrice() + " - "+ stock[index].getName());
			
			if (person.getLevel() < stock[index].requiredLevel) // if player hasn't reached required level, display level requirement 
				System.out.println(" - " + "You must be Level " + stock[index].getRequiredLevel() + " to purchase.");
			else
				System.out.println();
		}
		System.out.println("(9) Back to Store Menu");
	}

	/*
	 * Buy Item
	 */
	public void buyItem(Character_Class person, String item, int price, int requiredLevel) throws Exception{ 
		if(person.getMoney() >= price && person.getLevel() >= requiredLevel){
			person.setMoney(person.getMoney() - price);
			person.backpack.addToInventory(item, 1);//add item to inventory
			System.out.println("Here's your " + item);
			System.out.println("You have $" + person.getMoney() + " left. \n Would you like anything else? \n");
		}
		else if(person.getMoney() >= price && person.getLevel() < requiredLevel){
			System.out.println("You don't meet the level requirements for this purchase");
		}
		else {
			System.out.println("Not enough gold. \nMaybe try farming a local dungeon. Probably some thieves still around.");
		}
	}
	
	/*
	 * Leave store (exit shopping)
	 */
	
	public void exitStore(){
		currentlyHere = false;
	}
	
	/*
	 * Getters and Setters
	 */
	
	public void setWeaponsStock(){
		//foreach normal weapon
		//	add weapon to weaponsStock arraylist
	}
	
	public void setItemsStock(){
		
	}
	
	/*
	 * JUNK CLASS FOR JUNK OBJECTS FOR JUNK INFORMATION  <-- DELETE AS SOON REAL WEAPONS, ITEMS, AND ARMOR ARE MADE
	 */
	class shopWeaponItem {
		private String name; //name
		private int price;   //gold price
		private String type; //item or weapon
		private int requiredLevel; //required level to purchase weapon
		private int specialStat;
		
		shopWeaponItem(){
		}
		
		//Constructor - 4 accepted arguments
		shopWeaponItem(String name, int price, String type, int requiredLevel, int specialStat){
			this.name = name;
			this.price = price;
			this.type = type;
			this.requiredLevel = requiredLevel;
			this.specialStat = specialStat; //special stat is the amount armor adds to player's armor stat, or how much a basic health potion.
		}
		
		public String getName(){
			return this.name;
		}
		
		public int getPrice(){
			return this.price;
		}
		
		public String getType(){
			return this.type;
		}
		
		public int getRequiredLevel() {
			return this.requiredLevel;
		}
		
		public int getSpecialStat() {
			return this.specialStat;
		}
	}
}