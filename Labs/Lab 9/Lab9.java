import java.net.*;
import java.util.Enumeration;
public class Lab9 {
    public static void main(String[] args) {
        long start, end;
        start = System.nanoTime();
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while(interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
                while(addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if(address instanceof Inet4Address && netInterface.isUp() && !address.isLoopbackAddress() && address.isSiteLocalAddress()) {
                        System.out.println("\tIs up? " + netInterface.isUp());
                        System.out.println("\tAddress: " + address);
                        byte[] byteAddress = address.getAddress();
                        InetAddress subAddress = null;
                        for(int i = 0; i < 256; i++) {
                            byteAddress[3] = (byte) i;
                            subAddress = InetAddress.getByAddress(byteAddress);
                            if(!subAddress.getHostName().equals(subAddress.getHostAddress())) {
                                System.out.println(subAddress.getHostName());
                            }
                        }
                    }
                }
            }
        } catch(SocketException se) {
            se.printStackTrace(System.err);
        } catch(UnknownHostException uhe) {
            uhe.printStackTrace(System.err);
        }
        end = System.nanoTime();
        System.out.println("Time: " + (end - start));
    }
}