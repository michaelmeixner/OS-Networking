import java.io.*;
import java.io.Serializable;

public class TaskAdd implements Task<Integer>, Serializable
{
    private final int sum;
    
    public TaskAdd(int sum)
    {
        this.sum = sum;
    }

    public Integer execute()
    {
        return numPlusTwo(sum);
    }

    public int numPlusTwo(int a)
    {
        return a + 2;
    }

}