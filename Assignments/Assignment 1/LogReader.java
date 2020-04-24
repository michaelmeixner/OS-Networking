// Michael Meixner, OS & Networking, Assignment 1

import java.io.*;
import java.util.*;

public class LogReader {
    public static final void main(String[] args) {
        Stack<String> stack = new Stack<String>();
        BufferedReader reader = null;
        String line = null;
        try {
            reader = new BufferedReader(new FileReader("PrintAppClient_Info.log"));
            while((line = reader.readLine()) != null)
                stack.push(line);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace(System.err);
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        }
        int i = 0;
        while(stack.isEmpty() == false && i < 10) {
            System.out.println(stack.pop());
            i++;
        }
    }
}