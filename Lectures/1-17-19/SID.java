import java.io.*;

public class SID
{
    public static final void main(String[] args)
    {
        // try
        // {
        //     BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        //     System.out.println("Please enter something.");
        //     String data = null;
        //     while((data = input.readLine()) != null)
        //     {
        //         System.out.println("You typed: " + data);
        //     }
        // }
        // catch(IOException ioe)
        // {
        //     ioe.printStackTrace(System.err);
        // }

        BufferedReader input = null;
        try
        {
            input = new BufferedReader(new FileReader("Lecture.txt"));
            String data = null;
            while((data = input.readLine()) != null)
            {
                System.out.println("File text: " + data);
            }
        }
        catch(FileNotFoundException fnfe)
        {
            fnfe.printStackTrace(System.err);
        }
        catch(IOException ioe)
        {
            ioe.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                input.close();
            }
            catch(Exception e){}
        }

        // BufferedReader input = null;
        // PrintWriter write = null;
        // try
        // {
        //     input = new BufferedReader(new inputStreamReader(System.in));
            
        //     System.out.println("Enter a file name: ");
        //     String data = input.readLine();
        //     System.out.println("Enter something to write to the file: ");
        //     String writing = null;
        //     while((writing = input.readLine()) != null)
        //     {
        //         write = new PrintWriter(data);
        //         write.writing
        //     }
        // }

    }
}