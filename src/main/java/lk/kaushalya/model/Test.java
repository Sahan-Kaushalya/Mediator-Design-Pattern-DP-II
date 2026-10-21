package lk.kaushalya.model;

import java.text.SimpleDateFormat;

interface AirTrafficControl {
    public void requestTakeOff(Flight f);
    public void requestLanding(Flight f);
    public void notifyFlight(String message, Flight f);

}

class AirPortControlTower implements AirTrafficControl {

    @Override
    public void requestTakeOff(Flight f) {
        System.out.println();
        System.out.println("Flight: " + f.getNumber() + " request permission to take-off");
        notifyFlight("Take-off approved , Take-off at: "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()), f);
    }

    @Override
    public void requestLanding(Flight f) {
        System.out.println();
        System.out.println("Flight: " + f.getNumber() + " request permission to landing");
        notifyFlight("Landing approved , Landing at: "+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()), f);
    }

    @Override
    public void notifyFlight(String message, Flight f) {
        f.receiveMessage(message);
    }
}

abstract class Flight {

    private AirTrafficControl airTrafficControl;
    private String number;

    public Flight(AirTrafficControl airTrafficControl, String number) {
        this.airTrafficControl = airTrafficControl;
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void requestTakeOff() {
        airTrafficControl.requestTakeOff(this);
    }

    public void requestLanding() {
        airTrafficControl.requestLanding(this);
    }

    public abstract void receiveMessage(String message);

}

class CommercialFlight extends Flight {

    public CommercialFlight(AirTrafficControl airTrafficControl, String number) {
        super(airTrafficControl, number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Commercial Flight: " + getNumber() + " received message: " + message);
    }
}

class PrivateFlight extends Flight {

    public PrivateFlight(AirTrafficControl airTrafficControl, String number) {
        super(airTrafficControl, number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Private Flight: " + getNumber() + " received message: " + message);
    }
}

class MilitaryFlight extends Flight {

    public MilitaryFlight(AirTrafficControl airTrafficControl, String number) {
        super(airTrafficControl, number);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("Military Flight: " + getNumber() + " received message: " + message);
    }
}

public class Test {

    public static void main(String[] args) {

        AirPortControlTower controlTower = new AirPortControlTower();
        Flight commercialFlight = new CommercialFlight(controlTower, "C123");
        Flight privateFlight = new PrivateFlight(controlTower, "P456");
        Flight militaryFlight = new MilitaryFlight(controlTower, "M789");

        commercialFlight.requestTakeOff();
        privateFlight.requestLanding();
        militaryFlight.requestTakeOff();
    }
}
