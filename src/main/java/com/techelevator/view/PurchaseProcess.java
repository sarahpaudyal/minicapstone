package com.techelevator.view;
public class PurchaseProcess {


    double totalPrice = 0;
    double totalFedMoney;
    DateAndTime dateAndTime = new DateAndTime();


    public void selectItem() {
    }


    public void feedMoney(double totalBills) {
        // System.out.println("Enter number of dollar Bills");
        totalFedMoney += totalBills;
    }

        public void finishTransaction () {
            System.out.println("Transaction completed");
            System.out.println("Thank you for your purchase:)");
        }


    }





