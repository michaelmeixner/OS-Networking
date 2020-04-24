// Lab 3.5, Michael Meixner and Lucas Miller
import java.math.BigDecimal;
import java.rmi.registry.*;

public class Client {

    private Client() {}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(host);
            RemoteTask stub = (RemoteTask) registry.lookup("RemoteTask");
            // String response = KeyboardReader.readLine("Type a message:");
            // while(true)
            // {
            //     response = stub.writeSomething(response);
            //     response = KeyboardReader.readLine("Response received: " + response);
            // }

            Pi pi = new Pi(5);
            Sum sum = new Sum(3,4);
            Divide divide = new Divide(8,4);
            System.out.println(stub.executeTask(pi));
            System.out.println(stub.executeTask(sum));
            System.out.println(stub.executeTask(divide));


        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }
}