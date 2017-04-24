package carParkManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class WestminsterCarParkManager implements CarParkManager, Serializable {

	private static final long serialVersionUID = 2524406296026998172L;

	static Scanner input = new Scanner(System.in);

	// Declaring private attributes

	private static final int MAXCAPACITY = 20;
	// number of free slots currently in the car park
	private int numOfAvailableSlots = 20;
	// holds the slot details of the car park
	private String[] slotArray = new String[MAXCAPACITY];
	// holds current vehicle details of the car park
	private ArrayList<Vehicle> carParkArray = new ArrayList<>(MAXCAPACITY);
	// Holds details about every vehicle which parked at car park
	private ArrayList<Vehicle> historyList = new ArrayList<>();

	// Method which validates the integer input
	public int intNumValidator(String msg) {
		int number = 0;
		boolean flag = true;
		while (flag) {
			try {
				System.out.print(msg + ": ");
				number = Integer.parseInt(input.next());
				flag = false;
			} catch (Exception e1) {
				System.out.println("!!! Invalid " + msg + ". Please enter a valid " + msg + " !!!");
			}
		}
		return number;
	}

	// common method to validate date input
	public void validation(int value, DateTime obj, String msg) {
		boolean flag = false;
		while (!flag) {
			try {
				System.out.print(msg + ": ");
				int m = Integer.parseInt(input.next());
				switch (value) {
				case 1:
					obj.setYear(m);
					break;
				case 2:
					obj.setMonth(m);
					break;
				case 3:
					obj.setDay(m);
					break;
				case 4:
					obj.setHour(m);
					break;
				case 5:
					obj.setMin(m);
					break;
				case 6:
					obj.setSec(m);
					break;
				}
				flag = true;
			} catch (Exception e) {
				System.out.println("!!! Invalid " + msg + ". Please enter a valid " + msg + " !!!");
			}
		}
	}

	// This method validates string input for vehicle type selection
	public String stringValidator(String msg) {
		String usrInput = " ";
		boolean repeatFlag = true;
		while (repeatFlag) {
			try {
				System.out.print(msg);
				usrInput = input.next();
				if (usrInput.length() == 1) {
					if (usrInput.equalsIgnoreCase("c") || usrInput.equalsIgnoreCase("v")
							|| usrInput.equalsIgnoreCase("m")) {
						repeatFlag = false;
					} else {
						System.out.println("Invalid input.");
					}
				} else {
					System.out.println("!!! Invalid Input. Input should contain only one character !!!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Invalid input.");
			}
		}
		return usrInput;
	}

	// this method returns a suitable parking slot for a particular vehicle type
	private int slotFinder(int reqSlots) {
		int slotNo = -1;
		for (int i = 0; i < slotArray.length; i++) {
			if (slotArray[i] == null && reqSlots == 1) {
				slotNo = i;
				break;
			} else if (slotArray[i] == null && slotArray[i + 1] == null && reqSlots == 2) {
				slotNo = i;
				break;
			}
		}
		return slotNo;
	}

	// this method handles input type for a date and time
	public void dateTimeInputGetter(DateTime dateTimeObj, String msg) {
		System.out.println("Enter the " + msg + " date---------");
		validation(1, dateTimeObj, "Year");
		validation(2, dateTimeObj, "Month");
		validation(3, dateTimeObj, "Day");
		System.out.println("Enter the " + msg + " time---------");
		validation(4, dateTimeObj, "Hour");
		validation(5, dateTimeObj, "Minute");
		validation(6, dateTimeObj, "Second");
	}

	// checks whether the vehicle plate id is in use in the car park
	public String vehicleIdValidator() {
		String vehiclePlateId = "";
		System.out.print("Please enter the vehicle Id plate number: ");
		boolean flag = true;
		while (flag) {
			vehiclePlateId = input.next();
			System.out.println();
			boolean found = false;
			for (int i = 0; i < carParkArray.size(); i++) {
				if (carParkArray.get(i).getVehiclePlateId().equalsIgnoreCase(vehiclePlateId)) {
					found = true;
					System.out.println("The entered vehicle plate id is currently in the park!");
					System.out.print("Please enter a valid vehicle plate id: ");
				}
			}
			if (!found) {
				flag = false;
			}
		}
		return vehiclePlateId;
	}

	// this method adds a vehicle to the system
	@Override
	public void addVehicle() {
		System.out.println("------Add Vehicle------");
		if (numOfAvailableSlots > 0) {
			String userInput = stringValidator("Select the Vehicle type: [C]-Car [V]-Van [M]-Motor Bike :");
			if (userInput.equalsIgnoreCase("v") && slotFinder(2) == -1) {
				System.out.println("Sorry. No enough parking slots for a van");
			} else {
				DateTime dateTimeObj = new DateTime();
				dateTimeInputGetter(dateTimeObj, "vehicle entry");
				String vehiclePlateId = vehicleIdValidator();
				System.out.print("Enter the vehicle brand: ");
				String vehicleBrand = input.next();
				System.out.println();

				if (userInput.equalsIgnoreCase("c")) {
					int numOfDoors = intNumValidator("Number of doors in Car");
					System.out.println();
					System.out.print("Enter the vehicle color of Car: ");
					String carColor = input.next();
					System.out.println();
					slotArray[slotFinder(1)] = vehiclePlateId;
					carParkArray.add(new Car(vehiclePlateId, vehicleBrand, dateTimeObj, numOfDoors, carColor));
					historyList.add(carParkArray.get(carParkArray.size() - 1));
					numOfAvailableSlots--;
					System.out.println(numOfAvailableSlots + " free parking slots remaining");
				} else if (userInput.equalsIgnoreCase("v")) {
					int cargoVolume = intNumValidator("Cargo Volume of the Van");
					System.out.println();
					slotArray[slotFinder(2)] = vehiclePlateId;
					slotArray[(slotFinder(2) + 1)] = vehiclePlateId;
					carParkArray.add(new Van(vehiclePlateId, vehicleBrand, dateTimeObj, cargoVolume));
					carParkArray.add(new Van(vehiclePlateId, vehicleBrand, dateTimeObj, cargoVolume));
					numOfAvailableSlots -= 2;
					System.out.println(numOfAvailableSlots + " free parking slots remaining");
				} else if (userInput.equalsIgnoreCase("m")) {
					int engineSize = intNumValidator("Engine size of the Motorbike");
					System.out.println();
					slotArray[slotFinder(1)] = vehiclePlateId;
					carParkArray.add(new MotorBike(vehiclePlateId, vehicleBrand, dateTimeObj, engineSize));
					numOfAvailableSlots--;
					System.out.println(numOfAvailableSlots + " free parking slots remaining");
				}
			}
		} else {
			System.out.println("Sorry. No available free slots in the car park");
		}
	}

	// This method removes a vehicle from the system
	@Override
	public void deleteVehicle() {
		System.out.println("------Delete Vehicle------");
		displayCurrentVehicleList();
		boolean flag = false;
		System.out.println("Enter the vehicle ID plate number to remove from the car park");
		String removeId = input.nextLine();
		DateTime dateTimeObject = new DateTime();
		dateTimeInputGetter(dateTimeObject, "vehicle leave");
		String type = "";
		for (int i = 0; i < carParkArray.size(); i++) {
			if (removeId == carParkArray.get(i).getVehiclePlateId()) {
				flag = true;
				carParkArray.get(i).setLeaveDateTime(dateTimeObject);
				type = carParkArray.get(i).getClass().getSimpleName();
				carParkArray.remove(i);
			}
		}
		for (int i = 0; i < slotArray.length; i++) {
			if (removeId == slotArray[i]) {
				slotArray[i] = null;
			}
		}
		numOfAvailableSlots++;
		if (type.equalsIgnoreCase("Van")) {
			numOfAvailableSlots++;
		}
		if (!flag) {
			System.out.println("A " + type + "has been removed from the car park");
		}else{
			System.out.println("No vehicle found");
		}
	}

	// this method is used to sort the a array list
	public void sortArrayList(ArrayList<Vehicle> ArrayList) {
		ArrayList.sort((o1, o2) -> Integer.compare(o1.getEntryDateTime().getSec(), o2.getEntryDateTime().getSec()));
		ArrayList.sort((o1, o2) -> Integer.compare(o1.getEntryDateTime().getMin(), o2.getEntryDateTime().getMin()));
		ArrayList.sort((o1, o2) -> Integer.compare(o1.getEntryDateTime().getHour(), o2.getEntryDateTime().getHour()));
		ArrayList.sort((o1, o2) -> Integer.compare(o1.getEntryDateTime().getDay(), o2.getEntryDateTime().getDay()));
		ArrayList.sort((o1, o2) -> Integer.compare(o1.getEntryDateTime().getMonth(), o2.getEntryDateTime().getMonth()));
		ArrayList.sort((o1, o2) -> Integer.compare(o1.getEntryDateTime().getYear(), o2.getEntryDateTime().getYear()));
	}

	// displays currently parked vehicles
	@Override
	public void displayCurrentVehicleList() {
		sortArrayList(carParkArray);
		System.out.println("------Currently Parked Vehicle List------");
		System.out.println("  Vehicle Plate ID     Entry Time     Vehicle Type");
		for (int i = carParkArray.size() - 1; i >= 0; i--) {
			System.out.println("   " + carParkArray.get(i).getVehiclePlateId() + "          "
					+ carParkArray.get(i).getEntryDateTime().toString() + "        "
					+ carParkArray.get(i).getClass().getSimpleName());
		}
	}

	// displays statistical data about the system
	@Override
	public void displayStatistics() {
		int carCount = 0;
		int vanCount = 0;
		int bikeCount = 0;
		for (int i = 0; i < carParkArray.size(); i++) {
			String vehicleType = carParkArray.get(i).getClass().getSimpleName();
			if (vehicleType.equalsIgnoreCase("Car")) {
				carCount++;
			} else if (vehicleType.equalsIgnoreCase("Van")) {
				vanCount++;
			} else if (vehicleType.equalsIgnoreCase("MotorBike")) {
				bikeCount++;
			}
		}
		double carPercentage = (carCount / this.carParkArray.size()) * 100.0;
		double vanPercentage = (vanCount / this.carParkArray.size()) * 100.0;
		double bikePercentage = (bikeCount / this.carParkArray.size()) * 100.0;
		System.out.println("------Statistical Data------");
		System.out.println("Cars = " + carPercentage + "%");
		System.out.println("Vans = " + vanPercentage + "%");
		System.out.println("Bikes= " + bikePercentage + "%");

		sortArrayList(carParkArray);
		sortArrayList(historyList);
		int lastCarPositon = this.carParkArray.size() - 1;
		System.out.println("                           Id Plate Number\t\t   Date & Time \t\t Type");
		System.out.println(
				"Longest parked vehicle(Overall): " + historyList.get(historyList.size() - 1).getVehiclePlateId()
						+ "\t\t\t\t" + historyList.get(historyList.size() - 1).getEntryDateTime().toString() + "\t"
						+ historyList.get(historyList.size() - 1).getClass().getSimpleName());
		System.out.println("Longest parked vehicle(Current): " + carParkArray.get(0).getVehiclePlateId() + "\t\t\t\t"
				+ carParkArray.get(0).getEntryDateTime().toString() + "\t"
				+ carParkArray.get(0).getClass().getSimpleName());
		System.out.println("Last parked vehicle:             " + carParkArray.get(lastCarPositon).getVehiclePlateId()
				+ "\t\t\t\t" + carParkArray.get(lastCarPositon).getEntryDateTime().toString() + "\t"
				+ carParkArray.get(lastCarPositon).getClass().getSimpleName());
	}

	@Override
	// search vehicles to a given date
	public void searchByDate() {
		System.out.println("------Search by Date------");
		boolean found = false;
		DateTime object = new DateTime();
		validation(1, object, "Year");
		validation(2, object, "Month");
		validation(3, object, "Day");
		System.out.println("Date: " + object.toString2());
		System.out.println("Vehicle Plate No       Type");
		for (int i = 0; i < historyList.size(); i++) {
			DateTime tempObj = historyList.get(i).getEntryDateTime();
			if (tempObj.getYear() == object.getYear() && tempObj.getMonth() == object.getMonth()
					&& tempObj.getDay() == object.getDay()) {
				found = true;
				System.out.println(historyList.get(i).getVehiclePlateId() + "\t\t"
						+ historyList.get(i).getClass().getSimpleName());
			}
		}
		if (!found) {
			System.out.println("No vehicle data found!");
		}

	}

	// calculates parking charge for a given vehicle to a given date and time
	public long individualCharge(Vehicle vehicleObj, DateTime obj) {
		DateTime entryObj = vehicleObj.getEntryDateTime();
		DateTime leaveObj = obj;
		long entryTimeInMins = (long) (entryObj.getYear() * 365 * 24 * 60) + (long) (entryObj.getMonth() * 30 * 24 * 60)
				+ (long) (entryObj.getDay() * 24 * 60);
		long leaveTimeInMins = (long) (leaveObj.getYear() * 365 * 24 * 60) + (long) (leaveObj.getMonth() * 30 * 24 * 60)
				+ (long) (leaveObj.getDay() * 24 * 60);
		long timeDiffInMins = leaveTimeInMins - entryTimeInMins;
		long days = timeDiffInMins / 24;
		long hours = (timeDiffInMins % 24) / 60;
		if ((timeDiffInMins % 24) % 60 > 0) {
			hours++;
		}
		long charge = 0;
		if (days > 0) {
			charge = days * 30 + hours * 4;
		} else if (days == 0 && hours > 3) {
			charge = ((hours - 3) * 4) + 9;
		} else if (days == 0 && hours < 3) {
			charge = hours * 3;
		}
		return charge;
	}

	// calculates current charging for current vehicles in the park
	@Override
	public void currentCharges() {
		System.out.println("------Charges for currently parked vehicles------");
		sortArrayList(carParkArray);
		DateTime object = new DateTime();
		dateTimeInputGetter(object, "current");
		System.out.println("  Vehicle Plate ID     Price(£)");
		for (int i = 0; i < carParkArray.size(); i++) {
			carParkArray.get(i).setCurrentCharge(individualCharge(carParkArray.get(i), object));
			System.out
					.println(carParkArray.get(i).getVehiclePlateId() + "\t\t" + carParkArray.get(i).getCurrentCharge());
		}
	}

	// storing room details in hotelDetails.txt file
	public void storeInFile(WestminsterCarParkManager obj) {
		try {
			File file = new File("carParkSystem.txt");
			FileOutputStream fos = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(obj);
			oos.flush();
			oos.close();
			fos.close();
			System.out.println("Successfully saved data to the file");
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("!!! File not found !!!");
		}
	}

	// loading room details from Hotel_Details.txt file
	public void loadFromFile(WestminsterCarParkManager obj) {
		File file = new File("carParkSystem.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			obj = (WestminsterCarParkManager) ois.readObject();
			numOfAvailableSlots = obj.numOfAvailableSlots;
			slotArray = obj.slotArray;
			carParkArray = obj.carParkArray;
			historyList = obj.historyList;
			System.out.println("Successfully loaded data from  file");
			ois.close();

		} catch (Exception e) {
			System.out.println("!!! File is empty or File not found !!!");
		}
	}

	// Displays the main menu
	public void startMenu(WestminsterCarParkManager obj) {
		System.out.println();
		boolean repeatFlag = true;
		loadFromFile(obj);
		while (repeatFlag) {
			// The main menu
			System.out.println("__________________________________________________");
			System.out.println("|Welcome to Westminster Car Park Managment System|");
			System.out.println("|                       Menu                     |");
			System.out.println("==================================================\n\n");
			System.out.println("A: Add Vehicle");
			System.out.println("D: Delete Vehicle");
			System.out.println("V: View currently parked Vehicles");
			System.out.println("C: View Vehicle Charges");
			System.out.println("S: Search Vehicles by day");
			System.out.println("G: Get Statistical data");
			System.out.println("X: Exit \n\n");
			System.out.print("Please enter your selection: ");
			try {
				String usrInput = input.next();
				if (usrInput.length() == 1) {
					// Getting the first character in the user input
					char userInput = usrInput.charAt(0);
					// Both upper and lower case characters are allowed for a
					// particular selection
					switch (userInput) {
					case 'A':
					case 'a':
						addVehicle();
						break;
					case 'd':
					case 'D':
						deleteVehicle();
						break;
					case 'v':
					case 'V':
						displayCurrentVehicleList();
						break;
					case 'c':
					case 'C':
						currentCharges();
						break;
					case 's':
					case 'S':
						searchByDate();
						break;
					case 'g':
					case 'G':
						displayStatistics();
						break;
					case 'x':
					case 'X':
						storeInFile(obj);
						System.exit(0);
						break;
					default:
						System.out.println("Invalid input");
						break;
					}
				} else {
					System.out.println("!!! Invalid Input. Input should contain only one character !!!");
				}

			} catch (Exception e) {
				System.out.println();
				e.printStackTrace();
			}
		}
	}
}
