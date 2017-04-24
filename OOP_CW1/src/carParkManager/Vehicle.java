package carParkManager;

import java.io.Serializable;

public abstract class Vehicle implements Serializable {

	private static final long serialVersionUID = -9171948116549025236L;
	// Declaring private variables
	private String vehiclePlateId; //holds the number plate id of the vehicle
	private String vehicleBrand;   //holds the brand of the vehicle
	private DateTime entryDateTime;//holds the entry date of the vehicle
	private DateTime leaveDateTime;//holds the leaving of the vehicle
	private double currentCharge;  //holds the current charge for a vehicle currently in the car park
	private double finalCharge;	   //holds the final charge for the vehicle

	// Declaring constructor for Vehicle class
	public Vehicle(String vehiclePlateId, String vehicleBrand, DateTime entryDateTime) {
		this.vehiclePlateId = vehiclePlateId;
		this.vehicleBrand = vehicleBrand;
		this.entryDateTime = entryDateTime;
	}

	// Declaring getter methods for private variables
	public String getVehiclePlateId() {
		return vehiclePlateId;
	}

	public String getVehicleBrand() {
		return vehicleBrand;
	}

	public DateTime getEntryDateTime() {
		return entryDateTime;
	}

	public DateTime getLeaveDateTime() {
		return leaveDateTime;
	}

	public double getFinalCharge() {
		return finalCharge;
	}
	public double getCurrentCharge() {
		return currentCharge;
	}
	// Setter methods for private variables
	public void setLeaveDateTime(DateTime leaveDateTime) {
		this.leaveDateTime = leaveDateTime;
	}

	public void setFinalCharge(double finalCharge) {
		this.finalCharge = finalCharge;
	}
	public void setCurrentCharge(double currentCharge) {
		this.currentCharge = currentCharge;
	}

}
