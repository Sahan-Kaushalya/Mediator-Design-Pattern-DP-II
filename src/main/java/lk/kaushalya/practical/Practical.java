package lk.kaushalya.practical;

import java.util.ArrayList;


/**
 * Practical 08
 * Mediator Pattern
 * <p>
 * Mediator pattern defines an object that encapsulates how a set of objects interact.
 * It reduces coupling between classes that communicate with each other.
 * <p>
 * 1. Draw a class diagram for the mediator pattern.
 * 2. Write a program to demonstrate Taxi Service using the mediator design pattern.
 * <p>
 * - Customer requests a taxi through the taxi center app.
 * - Taxi center app finds the available taxi.
 * - Taxi center app send customer location to the available driver.
 */

// Mediator interface
interface TaxiMediator {
    void requestTaxi(Customer customer, String taxiType);

    void assignTaxi(Taxi taxi, Customer customer);

    void NotifyDriver(Taxi taxi, Customer customer);

    void registerTaxi(Taxi taxi);
}

class TaxiCenterApp implements TaxiMediator {

    private ArrayList<Taxi> registeredTaxis = new ArrayList<>();

    @Override
    public void requestTaxi(Customer customer, String taxiType) {
        if(customer != null) {
            for (Taxi taxi : registeredTaxis) {
                if (taxi.getAvailable() && taxi.getType().equalsIgnoreCase(taxiType)) {
                    assignTaxi(taxi, customer);
                    NotifyDriver(taxi, customer);
                    taxi.setAvailable(false);
                    return;
                }
            }
            System.out.println("No available taxis at the moment for customer: " + customer.getName());
        } else {
            System.out.println("Customer is null.");

        }

    }

    @Override
    public void assignTaxi(Taxi taxi, Customer customer) {
        customer.receiveMessage("Customer " + customer.getName() + " has been assigned to Taxi " + taxi.getCarNumber() + "" +
                "\ndriven by " + taxi.getDriverName()+"\nCustomer phone number: " + customer.getPhoneNumber());
    }

    @Override
    public void NotifyDriver(Taxi taxi, Customer customer) {
        taxi.receiveMessage("New customer request from " + customer.getName() + "\nat location: " + customer.getLocation() + "" +
                "\nto destination: " + customer.getDestination()+"\nCustomer phone number: " + customer.getPhoneNumber());

    }

    @Override
    public void registerTaxi(Taxi taxi) {
        if (taxi != null && !registeredTaxis.contains(taxi)) {
            registeredTaxis.add(taxi);
        }else {
            System.out.println("This taxi is already registered: "+taxi.getCarNumber());
        }
    }
}

// Customer class
abstract class Customer {
    private String name;
    private String location;
    private String destination;
    private String phoneNumber;

    private TaxiMediator mediator;

    public Customer(String name, String location, String destination, String phoneNumber, TaxiMediator mediator) {
        this.name = name;
        this.location = location;
        this.destination = destination;
        this.phoneNumber = phoneNumber;
        this.mediator = mediator;
    }

    public void requestTaxi(String taxiType) {
        mediator.requestTaxi(this,taxiType);
    }

    public abstract void receiveMessage(String message);

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getDestination() {
        return destination;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public TaxiMediator getMediator() {
        return mediator;
    }
}

// Taxi class
abstract class Taxi {
    private String driverName;
    private String carNumber;
    private Boolean isAvailable;
    private String phoneNumber;
    private String type;

    private TaxiMediator mediator;

    public Taxi(String driverName, String carNumber, Boolean isAvailable, String phoneNumber, String type, TaxiMediator mediator) {
        this.driverName = driverName;
        this.carNumber = carNumber;
        this.isAvailable = isAvailable;
        this.phoneNumber = phoneNumber;
        this.type = type;
        this.mediator = mediator;
    }

    public void registerTaxi() {
        mediator.registerTaxi(this);
    }

    public abstract void receiveMessage(String message);

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getType() {
        return type;
    }

    public TaxiMediator getMediator() {
        return mediator;
    }

}

// SilverCustomer class (Customer Type)
class SilverCustomer extends Customer {

    public SilverCustomer(String name, String location, String destination, String phoneNumber, TaxiMediator mediator) {
        super(name, location, destination, phoneNumber, mediator);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Silver Customer: " + getName() + "\nreceived message: " + message);
    }
}

// GoldCustomer class (Customer Type)
class GoldCustomer extends Customer {

    public GoldCustomer(String name, String location, String destination, String phoneNumber, TaxiMediator mediator) {
        super(name, location, destination, phoneNumber, mediator);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Gold Customer: " + getName() + "\nreceived message: " + message);
    }
}

// Bike class (Taxi Type)
class Bike extends Taxi {

    public Bike(String driverName, String carNumber, Boolean isAvailable, String phoneNumber, TaxiMediator mediator) {
        super(driverName, carNumber, isAvailable, phoneNumber,"Bike", mediator);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Bike: " + getCarNumber() + "\nreceived message: " + message);
    }
}

// Tuk class (Taxi Type)
class Tuk extends Taxi {

    public Tuk(String driverName, String carNumber, Boolean isAvailable, String phoneNumber, TaxiMediator mediator) {
        super(driverName, carNumber, isAvailable, phoneNumber, "Tuk", mediator);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Tuk: " + getCarNumber() + "\nreceived message: " + message);
    }
}

// Car class (Taxi Type)
class Car extends Taxi {

    public Car(String driverName, String carNumber, Boolean isAvailable, String phoneNumber, TaxiMediator mediator) {
        super(driverName, carNumber, isAvailable, phoneNumber, "Car", mediator);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Car: " + getCarNumber() + "\nreceived message: " + message);
    }
}

public class Practical {
    public static void main(String[] args) {

        TaxiCenterApp app = new TaxiCenterApp();

        // Registering taxis
        Taxi bike1 = new Bike("Akalanka Nimal", "WP-CSB 1234", true, "0771234567", app);
        Taxi tuk1 = new Tuk("Amal Perera", "WP-AAT 5456", true, "0779876543", app);
        Taxi car1 = new Car("Kamal Nilupul", "NP-CCC 2789", true, "0775555555", app);
        Taxi car2 = new Car("Nimal Perera", "WP-AAA 1234", true, "0771111111", app);
        Taxi bike2 = new Bike("Sunil Perera", "WP-BBB 5678", true, "0772222222", app);

        // Customer Registration
        Customer SilverCustomer1 = new SilverCustomer("Anura Kumara", "Colombo", "Kandy", "0771234567", app);
        Customer GoldCustomer1 = new GoldCustomer("Tharaka Perera", "Galle", "Matara", "0773333333", app);
        Customer SilverCustomer2 = new SilverCustomer("Saman Perera", "Gampola", "Kandy", "0774444444", app);
        Customer GoldCustomer2 = new GoldCustomer("Namal Perera", "Kandy", "Galle", "0775555555", app);
        Customer GoldCustomer3 = new GoldCustomer("Yohan Perera", "Gampaha", "Minuwangoda", "0776666666", app);

        // Testing the taxi service
        System.out.println();
        System.out.println("--- Taxi Service Testing ---");
        System.out.println();
        System.out.println("------ Taxi Registration ------");
        bike1.registerTaxi();
        tuk1.registerTaxi();
        car1.registerTaxi();
        car2.registerTaxi();
        bike2.registerTaxi();
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println("------ Customer Requests ------");
        System.out.println();
        SilverCustomer1.requestTaxi("Car");
        System.out.println();
        GoldCustomer1.requestTaxi("Bike");
        System.out.println();
        SilverCustomer2.requestTaxi("Tuk");
        System.out.println();
        GoldCustomer2.requestTaxi("Car");
        System.out.println();
        GoldCustomer3.requestTaxi("Car");
        System.out.println();
        System.out.println("-------------------------------");
        System.out.println();
        System.out.println("---- Taxi Service Testing Completed ----");
    }
}
