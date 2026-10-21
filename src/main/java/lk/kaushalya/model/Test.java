package lk.kaushalya.model;

interface AirTrafficControl{
    public void requestTakeOff(Flight f);
    public void requestLanding(Flight f);
    public void notifyFlight(String message,Flight f);
}

class  AirPortControlTower implements  AirTrafficControl{

    @Override
    public void requestTakeOff(Flight f) {
        System.out.println("Flight: "+f.getNumber()+" request permission to take-off");
        notifyFlight("Take-off approved",f);
    }

    @Override
    public void requestLanding(Flight f) {
        System.out.println("Flight: "+f.getNumber()+" request permission to landing");
        notifyFlight("Landing approved",f);
    }

    @Override
    public void notifyFlight(String message, Flight f) {
        f.receiveMessage(message);
    }
}

abstract  class Flight {
    private String number;

    public String getNumber() {
        return number;
    }

    public void requestTakeOff() {

    }

    public void requestLanding() {

    }

    public abstract void receiveMessage(String message);

}
public class Test {
    public static void main(String[] args) {

    }
}
