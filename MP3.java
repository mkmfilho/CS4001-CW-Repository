public class MP3 extends Gadget {
    private int availableMemory; // in MB

    public MP3(String model, double price, int weight, String size, int availableMemory) {
        super(model, price, weight, size);
        this.availableMemory = availableMemory;
    }

    public int getAvailableMemory() { return availableMemory; }

    public void downloadMusic(int size) {
        if (size <= 0) {
            System.out.println("Download size must be positive.");
            return;
        }
        if (size <= availableMemory) {
            availableMemory -= size;
            System.out.println("Downloaded " + size + " MB. Remaining memory: " + availableMemory + " MB");
        } else {
            System.out.println("Not enough memory to download the music.");
        }
    }

    public void deleteMusic(int size) {
        if (size > 0) {
            availableMemory += size;
            System.out.println("Deleted " + size + " MB. Available memory: " + availableMemory + " MB");
        } else {
            System.out.println("Delete size must be positive.");
        }
    }

    @Override
    public void display() {
        super.display();
        System.out.println("Available memory: " + availableMemory + " MB");
    }
}
