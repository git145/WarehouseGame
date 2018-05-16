 /*
  * File: Item.java
  * Author: Richard Kneale
  * Student ID: 200790336
  * Date created: 28th November 2016
  * Description: Contains the information relating to a item
  */
 
 public class Item
 { 
	 // A string to represent the name of the item
	 private String name;
	 
	 // An integer to represent the size of the item
	 private int size;
	 private static final int MINIMUM_SIZE = 5;
	 private static final int MAXIMUM_SIZE = 15;
	 
	 // An integer to represent the value of the item
	 private int value;
	 private static final int MINIMUM_VALUE = 1;
	 private static final int MAXIMUM_VALUE = 20;
	 
	 
	 // Constructor
	 public Item(String itemName)
	 {
		 name = itemName;
		 size = getRandomInteger(MINIMUM_SIZE, MAXIMUM_SIZE);
		 value = getRandomInteger(MINIMUM_VALUE, MAXIMUM_VALUE);
	 }
	 
	 // Returns a random integer in the range [minimum, maximum]
	 private int getRandomInteger(int minimum, int maximum)
	 {
		 return (int)((Math.random() * ((maximum - minimum) + 1)) + minimum);
	 }
	 
	 // Returns the name of the item
	 public String getName()
	 {
		 return name;
	 }
	 
	 // Returns the size of the item
	 public int getSize()
	 {
		 return size;
	 }
	 
	 // Returns the value of the item
	 public int getValue()
	 {
		 return value;
	 }
	 
	 // Returns a description of the item
	 public String toString()
	 {
		 return "There is a " + name + " here (size " + size + ", value " + value +")";
	 }
	 
	 // Returns an alternative description of the item to toString()
	 public String itemDescription()
	 {
		 return name + " (size " + size + ", value " + value + ")";
	 }
 } // Item class