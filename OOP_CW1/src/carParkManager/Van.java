package carParkManager;

import java.io.Serializable;

public class Van extends Vehicle implements Serializable{

	private static final long serialVersionUID = 3579150089770043071L;
	
	//Declaring private variables
	private int cargoVolume; //holds the cargo volume for the van

	//Declaring constructor for Van class
	public Van(String vehiclePlateId, String vehicleBrand, DateTime entryDateTime, int cargoVolume) {
		super(vehiclePlateId, vehicleBrand, entryDateTime);
		this.cargoVolume = cargoVolume;
	}

	//Declaring setter methods for private variables
	public int getCargoVolume() {
		return cargoVolume;
	}
}
