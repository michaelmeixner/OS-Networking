public class Lab8 {
    public static void main(String[] args) {
        int address = KeyboardReader.readInt("Enter a virtual address: ");
        System.out.println("Address: " + address + "\nPage number: " + (address / 4096) + "\nOffset: " + (address % 4096));
    }
}