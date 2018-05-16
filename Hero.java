 /*
  * File: Hero.java
  * Author: Richard Kneale
  * Student ID: 200790336
  * Date created: 16th November 2016
  * Description: Contains the information relating to a hero
  */
 
 // Used to access LinkedLists
 import java.util.*;
 
 public class Hero extends Character
 { 
	 // The value in the 'position' array that stores the column of the warehouse that the hero is in
	 private static final int COLUMN_OF_ROOMS = 0;
	 
	 // The value in the 'position' array that stores the row of the warehouse that the hero is in
	 private static final int ROW_OF_ROOMS = 1;
	 
	 // The number of coordinates required to represent the position of the hero in the warehouse
	 private static final int NUMBER_OF_COORDINATES = 2;
	 
	 // The maximum total size of items that a hero can store in their backpack
	 public static final int BACKPACK_CAPACITY = 50;
	 
	 // A LinkedList to store a hero's items
	 private LinkedList<Item> backpack = new LinkedList<Item> ();
	 
	 // Stores the position of the character
	 int position[] = new int[NUMBER_OF_COORDINATES];
	 
	 // A constructor that provides the hero with their initial position in the warehouse
	 public Hero(int c, int r)
	 {
		 position[COLUMN_OF_ROOMS] = c;
		 position[ROW_OF_ROOMS] = r;
	 }
	 
	 // Allows the user to set the position of the hero
	 public void setPosition(int c, int r)
	 {
		 position[COLUMN_OF_ROOMS] = c;
		 position[ROW_OF_ROOMS] = r;
	 }
	 
	 // Allows the user to change the position of the hero
	 public void updatePosition(int c, int r)
	 {
		 position[COLUMN_OF_ROOMS] += c;
		 position[ROW_OF_ROOMS] += r;
	 }
	 
	 // Returns an integer to represent the column that a hero is in
	 public int getColumn()
	 {
		 return position[COLUMN_OF_ROOMS];
	 }
	 
	 // Returns an integer to represent the row that a hero is in
	 public int getRow()
	 {
		 return position[ROW_OF_ROOMS];
	 }
	 
	 // Returns a String representation of the items that the hero can drop
	 public String dropItem()
	 {
		 StringBuffer backpackDescription = new StringBuffer();
		 
		 backpackDescription.append("OK. Here are the items you can drop:\n");
		 
		 // Count how many items are in the backpack
		 int numberOfItems = backpack.size();
		 
		 // If the backpack is empty
		 if (numberOfItems == 0)
		 {
			 backpackDescription.append("Nothing\n");
		 }
		 
		 // If the backpack contains items
		 else
		 {
			 // Add a description to the StringBuffer for each item in turn
			 for (int item = 0; item < numberOfItems; item++)
			 {
				 // Add a numbered description of the item to the StringBuffer
				 backpackDescription.append((item + 1) + ": " + backpack.get(item).itemDescription());
				 
				 // Provide the correct line spacing
				 if (item != (numberOfItems - 1))
					 backpackDescription.append("\n");
			 }
		 }
			 
		 return backpackDescription.toString();
	 } // dropItem()
	 
	 // Returns a reference to the backpack LinkedList
	 public LinkedList<Item> getBackpack()
	 {
		 return backpack;
	 }
	 
	 // Returns a reference to an item in the backpack LinkedList
	 public Item getBackpackItem(int item)
	 {
		 return backpack.get(item);
	 }
	 
	 // Returns the total value of the items in the backpack
	 public int getBackpackValue()
	 {
		 // Total defaults to zero if the backpack is empty
		 int backpackValue = 0;
		 
		 // Count how many items are in the backpack
		 int numberOfItems = backpack.size();
		
		 // If the backpack contains items
		 if (backpack.size() != 0)
		 {
			 for (int item = 0; item < numberOfItems; item++)
			 {
				 // Add the value of the item to the count
				 backpackValue += backpack.get(item).getValue();
			 }
		 }
		 
		 return backpackValue;
	 }
	 
	 // Returns the total size of the items in the backpack
	 public int getBackpackSize()
	 {
		 // Total defaults to zero if the backpack is empty
		 int backpackSize = 0;
		 
		 // Count how many items are in the backpack
		 int numberOfItems = backpack.size();
		 
		 // If the backpack contains items
		 if (backpack.size() != 0)
		 {
			 for (int item = 0; item < numberOfItems; item++)
			 {
				 // Add the size of the item to the count
				 backpackSize += backpack.get(item).getSize();
			 }
		 }
		 
		 return backpackSize;
	 }
	 
	 // Returns a String representation of the items in the hero's backpack
	 public String toString()
	 {
		 StringBuffer heroDescription = new StringBuffer();
		 
		 heroDescription.append("Here are the items in your backpack:\n");
		 
		 // If the backpack contains items
		 int numberOfItems = backpack.size();
		 
		 // If the backpack is empty
		 if (numberOfItems == 0)
			 heroDescription.append("Nothing\n");
		 
		 // If the backpack contains items
		 else
		 {
			 for (int item = 0; item < numberOfItems; item++)
			 {
				 // Add a description of the item to the StringBuffer
				 heroDescription.append(backpack.get(item).itemDescription() + "\n");
			 }
			 
			 heroDescription.append("Total worth of items is " + getBackpackValue() + "\nSpace left in your backpack is " + (BACKPACK_CAPACITY - getBackpackSize()) + "\n");
		 }
		 
		 return heroDescription.toString();
	 } // toString()
 } // Hero class