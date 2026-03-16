public class Mobile extends Gadget {
    private int creditMinutes;

    public Mobile(String model, double price, int weight, String size, int creditMinutes) {
        super(model, price, weight, size);
        this.creditMinutes = creditMinutes;
    }

    public int getCreditMinutes() { return creditMinutes; }

    public void addCredit(int minutes) {
        if (minutes > 0) {
            creditMinutes += minutes;
            System.out.println("Added " + minutes + " minutes. New credit: " + creditMinutes);
        } else {
            System.out.println("Please enter a positive amount of minutes to add.");
        }
    }

    public void makeCall(String phoneNumber, int durationMinutes) {
        if (durationMinutes <= 0) {
            System.out.println("Call duration must be positive.");
            return;
        }
        if (durationMinutes <= creditMinutes) {
            System.out.println("Calling " + phoneNumber + " for " + durationMinutes + " minutes.");
            creditMinutes -= durationMinutes;
        } else {
            System.out.println("Insufficient credit to make the call.");
        }
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Calling credit remaining: " + creditMinutes + " minutes");
    }
}
