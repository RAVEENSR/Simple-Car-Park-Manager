package carParkManager;

import java.io.Serializable;

public class Car extends Vehicle implements Serializable{

	private static final long serialVersionUID = -4758779393298187186L;
	//Declaring private variables
	private int numOfDoors; //holds number of doors of the car
	private String carColor;//holds the color of the car
	
	//Declaring constructor for Car class
	public Car(String vehiclePlateId, String vehicleBrand, DateTime entryDateTime, int numOfDoors,String carColor) {
		super(vehiclePlateId, vehicleBrand, entryDateTime);
		this.numOfDoors = numOfDoors;
		this.carColor = carColor;
	}
	
	//Declaring setter methods for private variables
	public int getNumOfDoors() {
		return numOfDoors;
	}
	public String getCarColor() {
		return carColor;
	}
}
