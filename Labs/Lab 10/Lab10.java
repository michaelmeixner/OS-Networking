import java.io.*;
public class Lab10 {
    static File[] roots = File.listRoots();
    static int selector = 1;

    public static void printDirs() {
        System.out.println("Directories: ");
        for(File file: roots) {
            if(file.isDirectory()) {
                System.out.println("\t" + file.getName());
            }
        }
        System.out.println("Executables: ");
        for(File file: roots) {
            if(file.canExecute()) {
                System.out.println("\t" + file.getName());
            }
        }
    }
    public static void main(String[] args) {
        for(File root: roots) {
            System.out.println("Drive " + selector++ + ": " + root.getPath());
        }
        int choice = -1;
        while(choice < 0 || choice > roots.length) {
            choice = KeyboardReader.readInt("Please enter the number of the drive you want: ");
        }
        roots = roots[choice - 1].listFiles();
        loop: while(true) {
            printDirs();
            int operation = KeyboardReader.readInt("1. Select a folder.\n2. Move to parent directory.\n3. Quit.");
            switch(operation) {
                case 1:
                    String name = KeyboardReader.readLine("Enter folder name.");
                    boolean found = false;
                    for(File file: roots) {
                        if(file.getName().equals(name)) {
                            roots = file.listFiles();
                            found = true;
                            break;
                        }
                    }
                    if(found == false) {
                        System.out.println("No folder by that name.");
                    }
                    break;

                case 2:
                    System.out.println(roots[0].getParent());
                    if(roots[0].getParentFile().getParentFile() != null) {
                        roots = roots[0].getParentFile().getParentFile().listFiles();
                    } else {
                        System.out.println("No parent folder. You're in root.");
                    }
                    break;

                case 3:
                    break loop;

                default:
                break;
            }
        }
    }
}