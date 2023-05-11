package com.techelevator;

import com.techelevator.util.LogFile;
import com.techelevator.view.DateAndTime;
import com.techelevator.view.Menu;
import com.techelevator.view.PurchaseProcess;
import com.techelevator.view.VItems;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class VendingMachineCLI extends VItems{

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT};

	private Menu menu;
	Scanner input = new Scanner(System.in);

	double vendingMachinebalance=0;
	int vendingMachinestock=5;

	PurchaseProcess purchaseProcess = new PurchaseProcess();
	DateAndTime dateAndTime = new DateAndTime();

	public VendingMachineCLI(Menu menu) {
		super();
		this.menu = menu;
	}

	public void run() {
		ArrayList<String> slotNumbers = new ArrayList<>();
		ArrayList<String> productName = new ArrayList<>();
		ArrayList<Double> productCost = new ArrayList<>();
		ArrayList<String> productClass = new ArrayList<>();

		File inventoryFile = new File("vendingmachine.csv");
		try {
			Scanner scanFile = new Scanner(inventoryFile);
			while (scanFile.hasNextLine()) {
				String displayOutput = scanFile.nextLine();
				String[] lines = displayOutput.split("\\|");
				slotNumbers.add(lines[0]);
				productName.add(lines[1]);
				double cost = Double.parseDouble(lines[2]);
				productCost.add(cost);
				productClass.add(lines[3]);
			}
		} catch (FileNotFoundException e) {
			System.out.println("Error finding file");
		}

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				try {
					Scanner scanFile = new Scanner(inventoryFile);
					while (scanFile.hasNextLine()) {
						String displayOutput = scanFile.nextLine();
						System.out.println(displayOutput);
					}
				} catch (FileNotFoundException e) {
					System.out.println("Error finding file");
				}

			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {


				double total = 0;
				String ans = "Y";
				Scanner productScanner = new Scanner(System.in);
				while (ans.equals("Y")) {
					if (vendingMachinestock == 0) {
						System.out.println("ITEM SOLD OUT");
					}
					System.out.println("Enter the slot number to choose item");
					String slotID = productScanner.nextLine();
					int pos = slotNumbers.indexOf(slotID);

					double cost = productCost.get(pos);
					total += cost;
					System.out.printf("The total amount is: $%.2f\n", total);
					System.out.println("Would you like to chose another item? (Y/N)");
					ans = productScanner.nextLine();
					ans = ans.toUpperCase();
					vendingMachinestock = vendingMachinestock - 1;
				}

				System.out.println("Enter the total number of Bills: ");
				double totalBills = input.nextDouble();

				LogFile.log(dateAndTime.getAmPm() + "FEED MONEY: " + "$" + totalBills + " " + "$");

				double totalChangeGivenBack = totalBills - total;
				System.out.println("Your change is : " + totalChangeGivenBack);
				int changeInPennies;

				totalChangeGivenBack = totalChangeGivenBack * 100;
				changeInPennies = (int) totalChangeGivenBack;


				int quarters = changeInPennies / 25;
				changeInPennies = changeInPennies % 25;

				int dimes = changeInPennies / 10;
				changeInPennies = changeInPennies % 10;

				int nickels = changeInPennies / 5;
				changeInPennies = changeInPennies % 5;

				int pennies = changeInPennies / 1;

				System.out.println("Change in coins  " + " Quarters  " + quarters + "  dimes  " + dimes + "  nickels  " + nickels + "  pennies  " + pennies);
				System.out.println("Please take your change");


				boolean hasPrinted = false;
				for (int i = 0; i < productClass.size(); i++) {
					if (productClass.get(i).equals("Chip")) {
						if (!hasPrinted) {
							System.out.println("Crunch Crunch, Yum");
							hasPrinted = true;
						}
					} else if (productClass.get(i).equals("Gum")) {
						if (!hasPrinted) {
							System.out.println("Chew Chew, Yum");
							hasPrinted = true;
						}
					} else if (productClass.get(i).equals("Drink")) {
						if (!hasPrinted) {
							System.out.println("Glug Glug, Yum");
							hasPrinted = true;
						}
					} else if (productClass.get(i).equals("Candy")) {
						if (!hasPrinted) {
							System.out.println("Munch Munch, Yum");
							hasPrinted = true;
						}
					}
				}
								vendingMachinebalance = vendingMachinebalance + totalBills - changeInPennies;

								LogFile.log(dateAndTime.getAmPm() + "GIVE CHANGE: " + "$" + (double) totalChangeGivenBack + " " + "$");
							}

							System.out.println();
							System.out.println();
							purchaseProcess.finishTransaction();

						}
					}

				public static void main (String[]args){
					Menu menu = new Menu(System.in, System.out);
					VendingMachineCLI cli = new VendingMachineCLI(menu);
					cli.run();

				}
			}


