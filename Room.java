 /*
  * File: Room.java
  * Author: Richard Kneale
  * Student ID: 200790336
  * Date created: 16th November 2016
  * Description: Contains the information relating to a room
  */
 
 // Used to access LinkedLists
 import java.util.*;
 
 public class Room
 { 
	 // An integer to represent the total number of walls
	 private static final int NUMBER_OF_WALLS = 4;
	 
	 // An integer to represent that a wall has a door
	 private static final int IS_DOOR = 1;
	 
	 // An integer to represent that a wall does not have a door
	 private static final int IS_WALL = 0;
	 
	 // An array to store the doors in a room
	 private Door doors[] = new Door[NUMBER_OF_WALLS];
	 
	 // A LinkedList to store the items in a room
	 private LinkedList<Item> items = new LinkedList<Item> ();
	 
	 // Constructor
	 public Room (boolean n, boolean e, boolean s, boolean w)
	 {
		 // Set whether each wall has a door or not
		 doors[Wall.NORTH_WALL] = new Door(n);
		 doors[Wall.EAST_WALL] = new Door(e);
		 doors[Wall.SOUTH_WALL] = new Door(s);
		 doors[Wall.WEST_WALL] = new Door(w);
	 }
	 
	 // Returns a String to represent whether a wall has a door or not
	 public String doorToString(int wall, int orientation)
	 {
		 String isDoor;
		 
		 // If it is a door
		 if (doors[wall].getIsDoor()) 
			 isDoor = "D";
		 
		 // If it a horizontal wall
		 else if (orientation == Wall.HORIZONTAL) 
			 isDoor = "-";
		 
		 // If it is a vertical wall
		 else if (orientation == Wall.VERTICAL) 
			 isDoor = "|";
		 
		 else 
			 isDoor = "Invalid";
		 
		 return isDoor;
	 } // doorToString()
	 
	 // Returns an integer to represent whether a wall has a door or not
	 public int doorToInteger(int wall)
	 {
		 int isDoor;
		 
		 // If it is a door
		 if (doors[wall].getIsDoor()) 
			 isDoor = IS_DOOR;
		 
		 // If it is a wall
		 else isDoor = IS_WALL;
		 
		 return isDoor;
	 }
	 
	 // Returns whether a specific wall has a door or not
	 public boolean getIsDoor(int wall)
	 {
		 return doors[wall].getIsDoor();
	 }
	 
	 // A user can input a wall and a boolean value to change its isDoor variable to
	 public void setIsDoor(int wall, boolean changeToIsDoor)
	 {
		 doors[wall].setIsDoor(changeToIsDoor);
	 }
	 
	 // Add an item to the room
	 public void addItem(String itemName)
	 {
		 items.addLast(new Item(itemName));
	 }
	 
	 // Returns a String representation of a wall's direction
	 private static String wallToString(int wall)
	 {
		 String wallDirection;
		 
		 switch (wall)
		 {
			 case Wall.NORTH_WALL: wallDirection = "north"; break;
			 case Wall.EAST_WALL:  wallDirection = "east";  break;
			 case Wall.SOUTH_WALL: wallDirection = "south"; break;
			 case Wall.WEST_WALL:  wallDirection = "west";  break;
			 default: wallDirection = "invalid";
		 }
		 
		 return wallDirection;
	 }
	 
	 public String pickUpItem()
	 {
		 StringBuffer itemsDescription = new StringBuffer();
		 
		 itemsDescription.append("OK. Here are the items you can pick up:\n");
		 
		 // Count how many items are in the room
		 int numberOfItems = items.size();
		 
		 // If the room is empty
		 if (numberOfItems == 0)
		 {
			 itemsDescription.append("Nothing\n");
		 }
		 
		 // If the room contains items
		 else
		 {
			 // Add a description to the StringBuffer for each item in turn
			 for (int item = 0; item < numberOfItems; item++)
			 {
				 // Add a numbered description of the item to the StringBuffer
				 itemsDescription.append((item + 1) + ": " + items.get(item).itemDescription());
				 
				 // Provide the correct line spacing
				 if (item != (numberOfItems - 1))
					 itemsDescription.append("\n");
			 }
		 }
			 
		 return itemsDescription.toString();
	 } // pickUpItem()
	 
	 // Returns a reference to the LinkedList of items
	 public LinkedList<Item> getItems()
	 {
		 return items;
	 }
	 
	 // // Returns a reference to an Item in the LinkedList of items
	 public Item getItem(int item)
	 {
		 return items.get(item);
	 }
	 
	 // Returns a description of the room
	 public String toString()
	 {
		 StringBuffer roomDescription = new StringBuffer();
		 
		 // For each wall in turn
		 for (int wall = Wall.NORTH_WALL; wall <= Wall.WEST_WALL; wall++)
			 if (getIsDoor(wall))
				 roomDescription.append("There is a door going " + wallToString(wall) + "\n");
		 
		 // Count how many items are in the room
		 int numberOfItems = items.size();
			 
		 // Print a description for each item in turn
		 for (int item = 0; item < numberOfItems; item++)
		 {
				 roomDescription.append(items.get(item) + "\n");
		 }
			 
		 return roomDescription.toString();
	 } // toString()
 } // Room class