import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.nio.charset.*;
import java.nio.file.*;

public class WebServer {
    private static final File root = new File("C:\\Users\\9mich\\Documents\\OS and Networking\\Assignments\\Website");
    public static final void main(String[] args) {
        System.out.println(root.getPath().length());
        try (ServerSocket serverSocket = new ServerSocket(80)) {
            System.out.println("Accepting connections on port " + serverSocket.getLocalPort());
            ExecutorService pool = Executors.newFixedThreadPool(1000);
            while (true) { 
                try {
                    pool.execute(new HttpHandler(serverSocket.accept()));
                } catch (IOException ioe) {
                    System.err.println("Error Accepting Connection");
                    ioe.printStackTrace(System.err); 
                }
            }
        } catch (IOException ioe) {    
            ioe.printStackTrace(System.err); 
        }
    }
    public static File getRoot() {
        return WebServer.root;
    }
}

class HttpHandler implements Runnable {
    private Socket socket;

    HttpHandler(Socket socket) {
        this.socket = socket;
    }

    public final void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedOutputStream out = new BufferedOutputStream(socket.getOutputStream());
            ArrayList<String> requestLineuestHeader = new ArrayList<>();
            for(String input = in.readLine(); input != null && input.length() > 0; input = in.readLine()) {
                requestLineuestHeader.add(input);
            }

            System.out.println();
            System.out.println("Processing Connection");
            String getLine = null;
            for(String requestLine : requestLineuestHeader) {
                System.out.println(requestLine);
                if(requestLine.startsWith("GET")) {
                    getLine = requestLine;
                }
            }

            String[] parts = getLine.split(" ");
            String fileName = (parts[1].length() > 1)? parts[1].substring(1) : "index.html";
            System.out.println("The requested file: " + fileName);

            // byte[] file = "<html><body><p>Hello World</p><body></html>\n\n".getBytes(Charset.forName("UTF-8"));
            byte[] file = null;
            String headerResponse = "200 OK";
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            try {
                File newFile = new File(WebServer.getRoot(), fileName).getCanonicalFile();
                if (!newFile.getAbsolutePath().startsWith(WebServer.getRoot().getAbsolutePath())) {
                    // 405 forbidden
                }
                if(newFile.isFile()) {
                    file = Files.readAllBytes(newFile.toPath());
                } else {
                    File indexFile = new File(newFile, "index.html");
                    if (indexFile.exists())
                        file = Files.readAllBytes(indexFile.toPath());
                    else {
                        outputStream.write("<html>\n<body>".getBytes("US-ASCII"));
                        String parent = newFile.getParent();
                        if(parent.equals(WebServer.getRoot().getCanonicalPath())) {
                            outputStream.write(("<a href=\"/\"/>../</a>\n").getBytes("US-ASCII"));
                        } else {
                            outputStream.write(("<a href=\"/" + parent.substring(62) + "/\"/>../</a>\n").getBytes("US-ASCII"));
                        }
                        for(File itFile: newFile.listFiles()) {
                            outputStream.write(("<a href=\"" + itFile.getName() + "/\"/>" + itFile.getName() + "</a>\n").getBytes("US-ASCII"));
                        }
                        outputStream.write("</body>\n</html>".getBytes("US-ASCII"));
                        file = outputStream.toByteArray();
                    }
                }
            } catch(NoSuchFileException nsfe) {
                System.err.println("FILE NOT FOUND: " + fileName);
                headerResponse = "404 NOT FOUND";
                file = new byte[0];
            }

            String mimeType = "text/html";
            if(fileName.endsWith("png")) {
                mimeType = "image/png";
            } else if(fileName.endsWith("jpg")) {
                mimeType = "image/jpeg";
            } else if(fileName.endsWith("otf")) {
                mimeType = "font/otf";
            } else if(fileName.endsWith("woff")) {
                mimeType = "font/woff";
            } else if(fileName.endsWith("woff2")) {
                mimeType = "font/woff2";
            } else if(fileName.endsWith("ttf")) {
                mimeType = "font/ttf";
            } else if(fileName.endsWith("eot")) {
                mimeType = "application/vnd.ms-fontobject";
            } else if(fileName.endsWith("svg")) {
                mimeType = "image/svg+xml";
            } else if(fileName.endsWith(".js")) {
                mimeType = "application/javascript";
            } else if(fileName.endsWith("css")) {
                mimeType = "text/css";
            }
            
            String header = "HTTP/1.1 " + headerResponse + "\n"
                + "Server: BCWeb 1.0\n"
                + "Keep-Alive: timeout=15, max=100\n"
                + "Content-length: " + file.length + "\n"
                + "Content-type: " + mimeType + "; charset=UTF-8\n\n";
            byte[] headerArr = header.getBytes(Charset.forName("US-ASCII"));
            out.write(headerArr);
            out.flush();
            out.write(file);
            out.flush();
        } catch(IOException ioe) {
            ioe.printStackTrace(System.err);
        } finally {
            try {
                socket.close();
            } catch(Exception e) {}
        }
    }
}