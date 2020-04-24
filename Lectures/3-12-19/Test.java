import java.net.*;
import java.util.Arrays;
import java.util.Enumeration;
public class Test {
    public static void main(String[] args) {
        int counter = 0;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                System.out.println(netInterface);
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    System.out.println("\t" + addresses.nextElement());
                }
                System.out.println("\tMTU: " + netInterface.getMTU());
                System.out.println("\tIs virtual? " + netInterface.isVirtual());
                System.out.println("\tMAC Address: " + Arrays.toString(netInterface.getHardwareAddress()));
                Enumeration<NetworkInterface> subs = netInterface.getSubInterfaces();
                while(subs.hasMoreElements()) {
                    System.out.println("\tSubInterface: " + subs.nextElement());
                }
                counter++;
            }
        } catch(SocketException se) {
            se.printStackTrace(System.err);
        }
        System.out.println(counter);
    }
}