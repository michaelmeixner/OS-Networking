import java.io.*;
import java.io.Serializable;

public class TaskDiff implements Task<Integer>, Serializable
{
    private final int diff;
    
    public TaskDiff(int diff)
    {
        this.diff = diff;
    }

    public Integer execute()
    {
        return numMinusTwo(diff);
    }

    public int numMinusTwo(int a)
    {
        return a - 2;
    }

}