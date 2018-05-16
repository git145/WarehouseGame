 /*
  * File: Door.java
  * Author: Richard Kneale
  * Student ID: 200790336
  * Date created: 16th November 2016
  * Description: Contains the information relating to a door
  */
 
 public class Door
 { 
	 // If true it is a door 
	 // If false it is a wall (default to allow a door to serve as a placeholder)
	 private boolean isDoor = false;
	 
	 // Constructor
	 public Door(boolean isItADoor)
	 {
		 isDoor = isItADoor;
	 }
	 
	 // Returns isDoor to a class that calls the method
	 public boolean getIsDoor()
	 {
		 return isDoor;
	 }
	 
	 // Can be used to change the value of the boolean variable isDoor
	 public void setIsDoor(boolean changeToIsDoor)
	 {
		 isDoor = changeToIsDoor;
	 }
 } // Door class