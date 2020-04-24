//Michael Meixner; Lab 2
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

public class lab2
{
    public static final void main(String[] args)
    {
        BufferedReader reader = null;
        BufferedReader fReader = null;
        PrintWriter writer = null;
        try
        {
            boolean urlCheck = false;
            reader = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(new BufferedWriter(new FileWriter("PageContents.html")));
            
            String url;
            URL address = null;;
            
            while(!urlCheck)
            {
                System.out.println("Please enter a URL: ");
                try
                {
                    url = reader.readLine();
                    address = new URL(url);
                }
                catch(MalformedURLException mue)
                {
                    mue.printStackTrace(System.err);
                    continue;
                }
                catch(IOException ioe)
                {
                    ioe.printStackTrace(System.err);
                }
                urlCheck = true;
            }

            fReader = new BufferedReader(new InputStreamReader(address.openStream()));
            
            String data = null;
            
            while((data = fReader.readLine()) != null)
            {
                // System.out.println("Page line: " + data);
                writer.println(data);
            }
        }
        // catch(FileNotFoundException fnfe)
        // {
        //     fnfe.printStackTrace(System.err);
        // }
        catch(IOException ioe)
        {
            ioe.printStackTrace(System.err);
        }
        finally
        {
            try
            {
                fReader.close();
            }
            catch(Exception e){}
            try
            {
                writer.close();
            }
            catch(Exception e){}
        }
    }
}