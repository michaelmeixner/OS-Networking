public class Driver extends Lab5 {
    public static void main(String[] args) {
        Lab5 thread1 = new Lab5("Original.txt", "Copy.txt");
        Lab5 thread2 = new Lab5("Orig2.txt", "Cop2.txt");
        Lab5 thread3 = new Lab5("Orig3.txt", "Cop3.txt");
        thread1.start();
        thread2.start();
        thread3.start();
        while(thread1.isAlive() || thread2.isAlive() || thread3.isAlive()) {
            System.out.println("Running...");
        }
    }
}