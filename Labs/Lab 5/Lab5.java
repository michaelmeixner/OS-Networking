// Michael Meixner, Lab 5
import java.io.*;
import java.lang.*;

public class Lab5 extends Thread {
    BufferedReader reader = null;
    PrintWriter writer = null;
    File a = null;
    File b = null;

    public Lab5() {
        this.a = null;
        this.b = null;
    }

    public Lab5(String original, String copy) {
        this.a = new File(original);
        this.b = new File(copy);
    }

    public void run() {
        String data = null;
        try {
            reader = new BufferedReader(new FileReader(this.a));
            writer = new PrintWriter(new BufferedWriter(new FileWriter(this.b)));
            while((data = reader.readLine()) != null) {
                writer.println(data);
            }
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch(Exception e) {}
            try {
                writer.close();
            } catch(Exception e) {}
        }
    }
}