package carParkManager;

import java.io.Serializable;

public class MotorBike extends Vehicle implements Serializable{

	private static final long serialVersionUID = 6584717267468046996L;
	
	//Declaring private variables
	private int engineSize; //holds engine cc of the motor bike
	
	//Declaring constructor for MotorBike class
	public MotorBike(String vehiclePlateId, String vehicleBrand, DateTime entryDateTime,int engineSize) {
		super(vehiclePlateId, vehicleBrand, entryDateTime);
		this.engineSize = engineSize;
	}
	
	//Declaring setter methods for private variables
	public int getEngineSize() {
		return engineSize;
	}
}
