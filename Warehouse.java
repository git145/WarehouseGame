 /*
  * File: Warehouse.java
  * Author: Richard Kneale
  * Student ID: 200790336
  * Date created: 16th November 2016
  * Description: Contains the information relating to a warehouse
  */
 
 import java.util.Random;
 
 // Used to access LinkedLists
 import java.util.*;
 
 // Used to read input from a text file
 import java.io.*;
 
 public class Warehouse
 {
	 // An array to represent the rooms in a warehouse
	 private Room rooms[][];
	 
	 // The value entered by the user for the square root of the number of rooms
	 private int squareRootOfRooms;
	 
	 // One less than the square root of the number of rooms
	 private int squareRootOfRoomsMinusOne;
	 
	 // The minimum number of doors that a room can have
	 private static final int MIN_DOORS_PER_ROOM = 2;
	 
	 // An integer used to count the number of doors in a room
	 private int numberOfDoors;
	 
	 // The number of items in the warehouse at the start of a game
	 private static final int NUMBER_OF_ITEMS = 50;
	 
	 // A LinkedList to store item names
	 private LinkedList<String> itemNames = new LinkedList<String> ();
	 
	 // An integer used to count the number of items in the warehouse
	 private int numberOfItems;
	 
	 // An object to represent the Random class
	 private Random random = new Random();
	 
	 // Debugging information is output when debug is true
	 private boolean debug = false;
	 
	 // Constructor
	 public Warehouse(int n)
	 {
		 squareRootOfRooms = n;
		 squareRootOfRoomsMinusOne = (squareRootOfRooms - 1);
		 
		 // Allocate the positions in the rooms array based on the square root of the rooms
		 rooms = new Room[squareRootOfRooms][squareRootOfRooms];
		 
		 // Instantiate rooms with doors for the number of rooms specified by the user
		 for (int columnOfRooms = 0; columnOfRooms < squareRootOfRooms; columnOfRooms++)
			 for (int rowOfRooms = 0; rowOfRooms < squareRootOfRooms; rowOfRooms++)
			 {
				 // Pass boolean values for whether each wall has a door or not
				 rooms[columnOfRooms][rowOfRooms] = new Room(getFirstIsDoor(Wall.NORTH_WALL, columnOfRooms, rowOfRooms, squareRootOfRoomsMinusOne), 
															 getFirstIsDoor(Wall.EAST_WALL,  columnOfRooms, rowOfRooms, squareRootOfRoomsMinusOne), 
															 getFirstIsDoor(Wall.SOUTH_WALL, columnOfRooms, rowOfRooms, squareRootOfRoomsMinusOne), 
															 getFirstIsDoor(Wall.WEST_WALL,  columnOfRooms, rowOfRooms, squareRootOfRoomsMinusOne));
			 }
			 
		 checkMinDoors();
		 addItems();
	 } // Warehouse()
	 
	 // Determine where to add doors when the warehouse is first created
	 private boolean getFirstIsDoor(int wall, int c, int r, int s)
	 {
		 boolean isDoor = false;
		 
		 switch(wall)
		 {
			 case Wall.NORTH_WALL: if (r != 0) // If it is not a top room
										 isDoor = rooms[c][r - 1].getIsDoor(Wall.SOUTH_WALL); // Inherit south door of above room
								   break;
			 
			 case Wall.EAST_WALL: if (c != s) // If it is not a right room
										 isDoor = getRandomBoolean();
								  break;
			 
			 case Wall.SOUTH_WALL: if (r != s) // If it is not a bottom room
										 isDoor = getRandomBoolean();
								   break;
			 
			 case Wall.WEST_WALL: if (c != 0) // If it is not a left room
										 isDoor = rooms[c - 1][r].getIsDoor(Wall.EAST_WALL); // Inherit east door of room to the left
								  break;
		 }
		 
		 return isDoor;
	 } // getFirstIsDoor()
	 
	 // Determine whether each room has the minimum number of doors
	 private void checkMinDoors()
	 {
		 for (int columnOfRooms = 0; columnOfRooms < squareRootOfRooms; columnOfRooms++)
			 for (int rowOfRooms = 0; rowOfRooms < squareRootOfRooms; rowOfRooms++)
			 {
				 // Reset count for numberOfDoors
				 numberOfDoors = 0;
				 
				 // Output should be 0
				 if (debug)
				 {
					 System.out.println("");
					 System.out.println(numberOfDoors);
				 }
				 
				 // For each wall in compass order
				 for (int wall = Wall.NORTH_WALL; wall <= Wall.WEST_WALL; wall++)
				 {
					 // Add one to numberOfDoors if the wall has a door
					 numberOfDoors += rooms[columnOfRooms][rowOfRooms].doorToInteger(wall);
				 }
				 
				 // Output should be [0, 4]
				 if (debug)
					 System.out.println(numberOfDoors);
				 
				 while (numberOfDoors < MIN_DOORS_PER_ROOM)
				 {
					 boolean isDoor;
					 
					 // Randomise north door if it is not top room with door
					 if ((rowOfRooms != 0) && 
						 (rooms[columnOfRooms][rowOfRooms].getIsDoor(Wall.NORTH_WALL) == false))
					 {
						 randomUpdateIsDoor(Wall.NORTH_WALL, columnOfRooms, rowOfRooms);
						 if (numberOfDoors >= MIN_DOORS_PER_ROOM) 
							 break;
					 }
					 
					 // Randomise east door if it is not a right room with door
					 if ((columnOfRooms != squareRootOfRoomsMinusOne) &&
						 (rooms[columnOfRooms][rowOfRooms].getIsDoor(Wall.EAST_WALL) == false)) 
					 {
					 	 randomUpdateIsDoor(Wall.EAST_WALL, columnOfRooms, rowOfRooms);
						 if (numberOfDoors >= MIN_DOORS_PER_ROOM) 
							 break;
					 }
					 					 
					 // Randomise south door if it is not a bottom room with a door
					 if ((rowOfRooms != squareRootOfRoomsMinusOne) && 
						 (rooms[columnOfRooms][rowOfRooms].getIsDoor(Wall.SOUTH_WALL) == false))
					 {
						 randomUpdateIsDoor(Wall.SOUTH_WALL, columnOfRooms, rowOfRooms);
						 if (numberOfDoors >= MIN_DOORS_PER_ROOM) 
							 break;
					 }
					 
					 // Randomise west door if it is not a left room with a door
					 if ((columnOfRooms != 0) && 
						 (rooms[columnOfRooms][rowOfRooms].getIsDoor(Wall.WEST_WALL) == false))
					 {
						 randomUpdateIsDoor(Wall.WEST_WALL, columnOfRooms, rowOfRooms);
						 if (numberOfDoors >= MIN_DOORS_PER_ROOM) 
							 break;
					 }
				 }
				 
				 // Output should be [MIN_DOORS_PER_ROOM, 4]
				 if (debug)
				 {
					 System.out.println(numberOfDoors);
					 System.out.println("");
				 }
			 }
	 } // checkMinDoors()
	 
	 // Randomly determine whether to add a door to a wall or not
	 private void randomUpdateIsDoor(int wall, int c, int r)
	 {
		 boolean isDoor = getRandomBoolean();
						 
		 if (isDoor)
		 {
			 switch (wall)
			 {
				 case Wall.NORTH_WALL: rooms[c][r].setIsDoor(Wall.NORTH_WALL, isDoor); // Update north door
									   rooms[c][r - 1].setIsDoor(Wall.SOUTH_WALL, isDoor); // Update south door of above room
								       break;
			 
				 case Wall.EAST_WALL: rooms[c][r].setIsDoor(Wall.EAST_WALL, isDoor); // Update east door
								      rooms[c + 1][r].setIsDoor(Wall.WEST_WALL, isDoor); // Update west door of room to the right
								      break;
			 
				 case Wall.SOUTH_WALL: rooms[c][r].setIsDoor(Wall.SOUTH_WALL, isDoor); // Update south door
								       rooms[c][r + 1].setIsDoor(Wall.NORTH_WALL, isDoor); // Update north door of room below
								       break;
			 
				 case Wall.WEST_WALL: rooms[c][r].setIsDoor(Wall.WEST_WALL, isDoor); // Update west door
								      rooms[c - 1][r].setIsDoor(Wall.EAST_WALL, isDoor); // Update east door of room to the left
								      break;
			 }
			 
			 numberOfDoors++;
		 }
	 } // randomUpdateIsDoor()
	 
	 // Loop through the rooms in the warehouse and randomly adds the desired number of items
	 private int addItems()
	 {
		 setItemNames();
		 
		 // Count how many items were in the text file
		 int numberOfItemNames = itemNames.size();
		 
		 // Reset counter for number of items
		 numberOfItems = 0;
		 
		 // While the warehouse has less than the desired number of items
		 while (true)
		 {
			 // For each room in turn
			 for (int columnOfRooms = 0; columnOfRooms < squareRootOfRooms; columnOfRooms++)
				 for (int rowOfRooms = 0; rowOfRooms < squareRootOfRooms; rowOfRooms++)
				 {
					 if (getRandomBoolean())
					 {
						 // Get a reference to the current Room object that is being worked on
						 Room currentRoom = rooms[columnOfRooms][rowOfRooms];
						 
						 // Add an item to the room
						 // Go through the list of item names and restart when the numberOfItems exceeds the numberOfItemNames
						 currentRoom.addItem(itemNames.get(numberOfItems % numberOfItemNames));
						 
						 // Increase the counter
						 numberOfItems++;
						 
						 // Store a reference to the items in the room being worked on
						 LinkedList<Item> currentRoomItems = currentRoom.getItems();
						 
						 // Count how many items are in the room
						 int numberOfItemsInRoom = currentRoomItems.size();
						 
						 // If items were already in the room
						 if (numberOfItemsInRoom > 1)
						 {
							 // Get a reference to the item that was just created
							 Item temporaryItem = currentRoomItems.getLast();
						 
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
								 if (((item != 0) && ((temporaryItemSize <= currentRoom.getItem(item).getSize()) && (temporaryItemSize > currentRoom.getItem(item - 1).getSize()))) ||
									 ((item == 0) && (temporaryItemSize <= currentRoom.getItem(item).getSize())))
								 {
									 // Add the item to the Items list in size order
									 currentRoomItems.add(item, temporaryItem);
									 
									 // Remove the duplicate item from the end of the list
									 currentRoomItems.removeLast();
									 
									 // Exit the for loop
									 break;
								 }
							 }
						 }
					 }
					 
					 // Leave the method when the desired number of items has been reached
					 if (numberOfItems == NUMBER_OF_ITEMS)
						 return 0;
				 }
		 }
	 } // addItems()
	 
	 // Populates a LinkedList with item names from a file
	 private void setItemNames()
	 {
		 try 
		 {
			 // Delete all item names from the list
			 itemNames.clear();
			 
			 // Read the text in the file
			 Scanner itemNameFile = new Scanner(new FileInputStream("ItemList.txt"));
			 
			 // Create an array item for each line in the file
			 while (itemNameFile.hasNextLine())
			 {
				 String nextItemName = itemNameFile.nextLine();
				 itemNames.add(nextItemName);
			 }
		 }
		 
		 // Catch the error if the ItemName.txt file missing
		 catch (FileNotFoundException e)
		 {
			 System.out.println("The item name file is missing.");
		 }
	 }
	 
	 // Returns true or false randomly when called
	 private boolean getRandomBoolean() 
	 {
		 return random.nextBoolean();
     }
	 
	 // Returns the Room at room[c][r] when called
	 public Room getRoom(int c, int r) 
	 {
		 return rooms[c][r];
     }
	 
	 public String toString() 
	 {
		 // A StringBuffer to store a String representation of the warehouse to output
		 StringBuffer warehouseAssembly = new StringBuffer();
		 
		 warehouseAssembly.append("\n");
		 
		 for (int rowOfRooms = 0; rowOfRooms < squareRootOfRooms; rowOfRooms++)
		 {
			 warehouseAssembly.append(getHorizontalWall(rowOfRooms, Wall.NORTH_WALL) + "\n");
			 warehouseAssembly.append(getFullVerticalWall() + "\n");
			 warehouseAssembly.append(getVerticalWall(rowOfRooms) + "\n");
			 warehouseAssembly.append(getFullVerticalWall() + "\n");

			 // If it is the last row of rooms
			 if (rowOfRooms == squareRootOfRoomsMinusOne)
				 warehouseAssembly.append(getHorizontalWall(rowOfRooms, Wall.SOUTH_WALL));
		 }
		 
		 return warehouseAssembly.toString();
     }
	 
	 // Returns a String to represent a horizontal wall
	 private String getHorizontalWall(int r, int wall) 
	 {
		 StringBuffer horizontalWallAssembly = new StringBuffer();
		 
		 // For each column of rooms
		 for (int columnOfRooms = 0; columnOfRooms < squareRootOfRooms; columnOfRooms++)
		 {
			 horizontalWallAssembly.append("--" + rooms[columnOfRooms][r].doorToString(wall, Wall.HORIZONTAL) + "-");
		 }
		 
		 // Append last string to complete the wall
		 horizontalWallAssembly.append("-");
		 
		 return horizontalWallAssembly.toString();
     }
	 
	 // Returns a String to represent part of a vertical wall
	 private String getVerticalWall(int r) 
	 {
		 StringBuffer verticalWallAssembly = new StringBuffer();
		 
		 // For each column of rooms
		 for (int columnOfRooms = 0; columnOfRooms < squareRootOfRooms; columnOfRooms++)
		 {
			 verticalWallAssembly.append(rooms[columnOfRooms][r].doorToString(Wall.WEST_WALL, Wall.VERTICAL) + "   ");
			 
			 // Append last string to complete the wall
			 if (columnOfRooms == squareRootOfRoomsMinusOne)
				 verticalWallAssembly.append(rooms[columnOfRooms][r].doorToString(Wall.EAST_WALL, Wall.VERTICAL));
		 }
		 
		 return verticalWallAssembly.toString();
     }
	 
	 // Returns a String to represent part of a vertical wall with no doors
	 private String getFullVerticalWall() 
	 {
		 StringBuffer verticalWallAssembly = new StringBuffer();
		 
		 // For each column of rooms
		 for (int columnOfRooms = 0; columnOfRooms < squareRootOfRooms; columnOfRooms++)
		 {
			 verticalWallAssembly.append("|   ");
		 }
		 
		 // Append last string to complete the wall
		 verticalWallAssembly.append("|");
		 
		 return verticalWallAssembly.toString();
     }
 } // Warehouse class