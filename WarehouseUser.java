 /*
  * File: WarehouseUser.java
  * Author: Richard Kneale
  * Student ID: 200790336
  * Date created: 16th November 2016
  * Description: Allows the user to run the program correctly
  */
 
 
 // Used to manage user input
 import java.util.Scanner;
 
 // Used to access LinkedLists
 import java.util.*;
 
 public class WarehouseUser
 {
	 // Debugging information is output when debug is set to true
	 private static boolean debug = false;
	 
	 // An object to represent the Warehouse
	 private static Warehouse warehouse;
	 
	 // An object to represent the Hero
	 private static Hero hero;
	 
	 // An integer to represent the square root of rooms
	 private static int SQUARE_ROOT_OF_ROOMS = 8;
	 
	 // A String to represent the players next move
	 private static String playerChoice;
	 
	 // Integers to represent the player's choice of which item to pick up or drop
	 private static int itemToPickUp;
	 private static int itemToDrop;
	 
	 // Stores a reference to the room that the hero is in
	 private static Room heroRoom;
	 
	 private static Scanner in = new Scanner(System.in);
	 
	 public static void main(String argv[])
	 {
		 if (debug)
		 {
			 // Inform the user that the program is running in debugging mode
			 addEmptyLine();
			 System.out.println("*DEBUGGING MODE*");
		 }
		 
		 addEmptyLine();
		 
		 // Output a statement to inform the user that the game has begun
		 System.out.println("Starting the game ...");
		 
		 // Create the warehouse
		 warehouse = new Warehouse(SQUARE_ROOT_OF_ROOMS);
		 
		 // One less than the values entered by the user is passed due to zero-indexing
		 hero = new Hero(getRandomInteger(1, SQUARE_ROOT_OF_ROOMS) - 1, 
			 getRandomInteger(1, SQUARE_ROOT_OF_ROOMS) - 1);
		 
		 // Describe the starting position of the hero
		 heroRoomDescription();
		 
		 // While the user still wants to play the game
		 while (true)
		 {
			 addEmptyLine();
			 
			 // Allow th eplayer to choose their next move
			 playerChoice();
			 
			 // if the player has decided to terminate the game
			 if (playerChoice.equals("t"))
			 {
				 // Leave the loop
				 break;
			 }
		 }
	 } // main()
	 
	 // Returns a random integer in the range [minimum, maximum]
	 private static int getRandomInteger(int minimum, int maximum)
	 {
		 return (int)((Math.random() * ((maximum - minimum) + 1)) + minimum);
	 }
	 
	 // Prints a description of the room that the hero is in
	 private static void heroRoomDescription()
	 {
		 // Display additional information to describe the hero's position
		 if (debug)
		 {
			 // Print the floor plan for the warehouse
			 System.out.println(warehouse);
		 }
		 
		 // Print a reference to the room that the hero is in
		 int heroColumn = hero.getColumn();
		 int heroRow = hero.getRow();
		 addEmptyLine();
		 System.out.print("You are in a room (" + (heroColumn + 1) + ", " + (heroRow + 1) + ")\n");
		 heroRoom = warehouse.getRoom(heroColumn, heroRow);
		 System.out.print(heroRoom);
	 }
	 
	 // Allows the user to pick the hero's next move
	 private static void playerChoice()
	 {
		 while(true)
		 {
			 System.out.print("What would you like to do? ");
			 
			 playerChoice = in.nextLine();
			 
			 // Collect a reference to the room that the hero is in
			 heroRoom = warehouse.getRoom(hero.getColumn(), hero.getRow());
				 
			 // Player moves through a north door
			 if (playerChoice.equals("n") && heroRoom.getIsDoor(Wall.NORTH_WALL))
			 {
				 // Move hero to above row of rooms
				 updateHeroPosition(0, -1);
				 break;
			 }
			 	
			 // Player moves through a east door
			 else if (playerChoice.equals("e") && heroRoom.getIsDoor(Wall.EAST_WALL))
			 {
				 // Move hero to right column of rooms
				 updateHeroPosition(1, 0);
				 break;
			 }
			 
			 // Player moves through a south door
			 else if (playerChoice.equals("s") && heroRoom.getIsDoor(Wall.SOUTH_WALL))
			 {
				 // Move hero to below row of rooms
				 updateHeroPosition(0, 1);
				 break;
			 }
			 
			 // Player moves through a west door
			 else if (playerChoice.equals("w") && heroRoom.getIsDoor(Wall.WEST_WALL))
			 {
				 // Move hero to left column of rooms
				 updateHeroPosition(-1, 0);
				 break;
			 }
			 
			 // If the player chooses a direction with no door
			 else if ((playerChoice.equals("n")) && ((heroRoom.getIsDoor(Wall.NORTH_WALL)) == false) ||
					  (playerChoice.equals("e")) && ((heroRoom.getIsDoor(Wall.EAST_WALL)) == false) ||
					  (playerChoice.equals("s")) && ((heroRoom.getIsDoor(Wall.SOUTH_WALL)) == false) ||
					  (playerChoice.equals("w")) && ((heroRoom.getIsDoor(Wall.WEST_WALL)) == false))
			 {
				 System.out.println("You can't go that way.\n");
			 }
			 
			 // Player displays inventory
			 else if (playerChoice.equals("i"))
			 {
				 addEmptyLine();
				 System.out.print(hero);
				 break;
			 }
			 
			 // Player picks up an item
			 else if (playerChoice.equals("p"))
			 {
				 pickUpItem(heroRoom);
			 }
			 
			 // Player drops an item
			 else if (playerChoice.equals("d"))
			 {
				dropItem(heroRoom);
			 }
			 
			 // Player chooses to terminate the game
			 else if (playerChoice.equals("t"))
			 {
				 addEmptyLine();
				 System.out.println("Goodbye");
				 break;
			 }
					
			 else
				 System.out.println("This value is invalid.\n");
		 }
	 } // playerChoice()
	 
	 private static void pickUpItem(Room room)
	 {
		 // Inform the user of the items that they can pick up
		 addEmptyLine();
		 System.out.println(room.pickUpItem());
				 
		 int numberOfItemsInRoom = room.getItems().size();
				 
		 // If the room contains items
		 if (numberOfItemsInRoom != 0)
		 {
			 while(true)
			 {
				 validateItemToPickUp();
					 
				 // If the player decides not to pick up an item
				 if (itemToPickUp == 0)
				 {
					 System.out.println("You did not take any items\n");
					 break;
				 }
					 
				 // If the item number is too high
				 else if (itemToPickUp > numberOfItemsInRoom)
					 System.out.println("This item does not exist\n");
					 
				 // If a valid item number is entered
				 else
				 {
					 // Deal with zero-indexing
					 int itemToAccess = (itemToPickUp - 1);
						 
					 // Store a temporary reference to the item to pick up
					 Item temporaryItem = room.getItem(itemToAccess);
						 
					 // If the item will overload the player's backpack
					 if ((temporaryItem.getSize() + hero.getBackpackSize()) > (hero.BACKPACK_CAPACITY))
					 {
						 System.out.println("This item will not fit in your backpack.\n");
					 }
				
					 // If the item will not overload the player's backpack
					 else
					 {
						 // Inform the user that an item has been picked up
						 System.out.println("OK. Item taken\n");
						 
						 //Get a reference to the hero's backpack
						 LinkedList<Item> heroBackpack = hero.getBackpack();
						 
						 // Add the item to the hero's backpack
						 heroBackpack.addLast(temporaryItem);
						 
						 // Count how many items are in the backpack
						 int numberOfItemsInBackpack = heroBackpack.size();
							
						 // If items were already in the backpack
						 if (heroBackpack.size() > 1)
						 {
							 // Get the size of the item that was just created
							 int temporaryItemSize = temporaryItem.getSize();
						 
							 // Get the position of the penultimate item in the array
							 // First compare size with penultimate item so the new item isn't compared 
							 // with itself, which will prevent the item being placed in size order
							 int penultimateItem = (numberOfItemsInBackpack - 2);
						 
							 // Insert the new item in size order
							 for (int item = penultimateItem; item >= 0; item--)
							 {
								 // If the item should be moved to the new location in the LinkedList
								 if (((item != 0) && ((temporaryItemSize <= heroBackpack.get(item).getSize()) && (temporaryItemSize > heroBackpack.get(item - 1).getSize()))) ||
									 ((item == 0) && (temporaryItemSize <= heroBackpack.get(item).getSize())))
								 {
									 // Add the item to the backpack list in size order
									 heroBackpack.add(item, temporaryItem);
									 
									 // Remove the duplicate item from the end of the list
									 heroBackpack.removeLast();
									 
									 // Exit the for loop
									 break;
								 }
							 }
						 }
					 }
						 
					 // Remove the item from the room
					 room.getItems().remove(itemToAccess);
						 
					 break;
				 }
			 }	
		 }
	 } // pickUpItem()
	 
	 private static void dropItem(Room room)
	 {
		 // Inform the user of the items that they can drop
		 addEmptyLine();
		 System.out.println(hero.dropItem());
				 
		 int numberOfItemsInBackpack = hero.getBackpack().size();
				 
		 // If the hero has items in their backpack
		 if (numberOfItemsInBackpack != 0)
		 {
			 while(true)
			 {
				 validateItemToDrop();
					 
				 // If the player decides not to drop an item
				 if (itemToDrop == 0)
				 {
					 System.out.println("You did not drop any items\n");
					 break;
				 }
					 
				 // If the item number is too high
				 else if (itemToDrop > numberOfItemsInBackpack)
					 System.out.println("This item does not exist\n");
					 
				 // If a valid item number is entered
				 else
				 {
					 // Deal with zero-indexing
					 int itemToAccess = (itemToDrop - 1);
						 
					 // Inform the user that an item has been dropped
					 System.out.println("OK. Item dropped\n");
						 
					 // Store a temporary reference to the item to pick up
					 Item temporaryItem = hero.getBackpackItem(itemToAccess);
						 
					 // Get a reference to the Item list for the room that the hero is in
					 LinkedList<Item> heroRoomItems = room.getItems();
						 
					 // Add the item to the room
					 heroRoomItems.addLast(temporaryItem);
						 
					 // Count how many items are in the room
					 int numberOfItemsInRoom = heroRoomItems.size();
						 
					 // If items were already in the room
					 if (numberOfItemsInRoom > 1)
					 {
						  // Get the size of the item that was just created
						 int temporaryItemSize = temporaryItem.getSize();
						 
						 // Get the position of the penultimate item in the array
						 // First compare size with penultimate item so the new item isn't compared 
						 // with itself, which will prevent the item being placed in size order
						 int penultimateItem = (numberOfItemsInRoom - 2);
						 
						 // Insert the new item in size order
						 for (int item = penultimateItem; item >= 0; item--)
						 {
							 // If the item should be moved to the new location in the LinkedList
							 if (((item != 0) && ((temporaryItemSize <= room.getItem(item).getSize()) && (temporaryItemSize > room.getItem(item - 1).getSize()))) ||
								 ((item == 0) && (temporaryItemSize <= room.getItem(item).getSize())))
							 {
							     // Add the item to the Items list in size order
							     heroRoomItems.add(item, temporaryItem);
									 
								 // Remove the duplicate item from the end of the list
								 heroRoomItems.removeLast();
									 
								 // Exit the for loop
								 break;
							 }
					     }
					 }
						 
					 // Remove the item from the hero's backpack
					 hero.getBackpack().remove(itemToAccess);
					 
					 break;
				 }
		     }
		 }
	 } // droptem()
	 
	 // Validate the player's input when they choose an item to pick up
	 private static void validateItemToPickUp()
	 {
		 while(true)
		 {
			 System.out.print("Which item would you like? ");
			 
			 if (in.hasNextInt())
			 {
				 itemToPickUp = in.nextInt();
				 
				 if (itemToPickUp >= 0) 
				 {
					 in.nextLine();
					 break;

				 }
				 else 
				 {
					 in.nextLine();
					 System.out.print("The value must be at least 0");
				 }
			 }
			 
			 else
			 {
				 in.nextLine();
				 integerPrompt();
			 }
		 }
	 } // pickUpItem()
	 
	 // Validate the player's input when they choose an item to drop
	 private static void validateItemToDrop()
	 {
		 while(true)
		 {
			 System.out.print("Which item would you like to drop? ");
			 
			 if (in.hasNextInt())
			 {
				 itemToDrop = in.nextInt();
				 
				 if (itemToDrop >= 0)
				 {
					 in.nextLine();
					 break;
			     }
				 else 
				 {
					 in.nextLine();
					 System.out.print("The value must be at least 0");
				 }
			 }
			 
			 else
			 {
				 in.nextLine();
				 integerPrompt();
			 }
		 }
	 } // dropItem()
	 
	 // Used to change the position of the hero
	 private static void updateHeroPosition(int c, int r)
	 {
		 // Move hero to specified room
		 hero.updatePosition(c, r);
		 
     	 heroRoomDescription();
	 }
	 
	 // Adds an empty line to the output window for readability
	 private static void addEmptyLine()
	 {
		 System.out.println("");
	 }
	 
	 // Informs the user that they must enter an integer
	 public static void integerPrompt()
	 {
		 System.out.print("The value must be an integer.\n\n");
	 }
 } // WarehouseUser class