import java.io.*;
import java.util.Hashtable;
public class MemManager {
    private File toTranslate;
    private File physicalMemory;
    private Hashtable<Integer, Integer> TLB;
    private int[] pageTable;
    private byte[][] memory;

    public MemManager(String fileToTranslate) {
        this.toTranslate = new File(fileToTranslate);
        if(!this.toTranslate.exists()) {
            System.out.println("File with addresses doesn't exist. Test file is being made.");
            try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(this.toTranslate)))) {
                writer.println(1);
                writer.println(256);
                writer.println(32768);
                writer.println(32769);
                writer.println(128);
                writer.println(65534);
                writer.println(33153);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        this.physicalMemory = new File("BACKING_STORE.bin");
        this.TLB = new Hashtable<Integer, Integer>(16);
        for(int i = 0; i < pageTable.length; i++) {
            pageTable[i] = -1;
        }
        this.memory = new byte[256][256];
    }

    public void makeBackingStore() {
        try (BufferedOutputStream storeWriter = new BufferedOutputStream(new FileOutputStream(this.physicalMemory))){
            byte j = 0b000;
            for(int i = 0; i < 65536; i++) {
                storeWriter.write(j++);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public int getFromTLB(int page) {
        Integer frame = this.TLB.get(page);
        if(frame != null) {
            return frame;
        } else return -1;
    }
    public static void main(String[] args) {
        String translateFile = (args.length > 0) ? args[0] : "addresses.txt";
        System.out.println("File of addresses: " + translateFile);
        MemManager memManager = new MemManager(translateFile);
        memManager.makeBackingStore();
        File tslateFile = new File(translateFile);
        String line = null;
        RandomAccessFile backStore = null;
        int index = 0; // to keep track of next open frame in memory
        try (BufferedReader reader = new BufferedReader(new FileReader(tslateFile))) {
            backStore = new RandomAccessFile(memManager.physicalMemory, "r");
            while((line = reader.readLine()) != null) {
                System.out.println("Address: " + line);
                int virtualPage = ((Integer.parseInt(line))&0x00F);
                int offset = ((Integer.parseInt(virtualPage))&0x00F);
                int frame = memManager.getFromTLB(virtualPage);
                if(frame == -1) {
                    // consult page table
                    frame = memManager.pageTable[virtualPage];
                    if(frame == -1) {
                        // page fault
                        byte[] fromFile = new byte[256];
                        backStore.seek(256*virtualPage);
                        backStore.read(fromFile, 0, 256);
                        memManager.memory[index] = fromFile.clone();
                        memManager.pageTable[virtualPage] = index;
                        if(memManager.TLB.size() < 12) {
                            memManager.TLB.put(virtualPage, index);
                        } else {
                            memManager.TLB.remove(memManager.TLB.keys().nextElement());
                            memManager.TLB.put(virtualPage, index);
                        }
                        frame = index;
                        index++;
                    }
                }
                System.out.println("Page: " + virtualPage + "\nOffset: " + offset);
                System.out.println("Frame: " + frame + "\nOffset: " + offset);
                System.out.println("Data: " + memManager.memory[frame][offset]);
                System.out.println("Size of TLB: " + memManager.TLB.size());
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                backStore.close();
            } catch (Exception e) {}
        }
    }
}