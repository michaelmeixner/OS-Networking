import java.io.*;
public class Demo {
    public static void main(String[] args) {
        File root = File.listRoots()[0];
        File[] allDirectories = root.listFiles(new FileFilter(){
            @Override
            public boolean accept(File f) {
                    return f.isDirectory() && f.canRead();
            }
        });

        final int AVG_DIR_NAME_LEN = 50 * 2;
        StringBuilder bobTheBuilder = new StringBuilder(AVG_DIR_NAME_LEN * allDirectories.length);
        for(File directory : allDirectories) {
            bobTheBuilder.append("<a href=\"");
            bobTheBuilder.append(directory.getPath());
            bobTheBuilder.append("\"/>");
            bobTheBuilder.append(directory.getName());
            bobTheBuilder.append("</a>");
            // System.out.println("<a href=\"" + directory.getPath() + "\"/>" + directory.getName() + "</a>");
        }
        byte[] bytes = bobTheBuilder.toString().getBytes();
        System.out.println(bytes);
    }
}