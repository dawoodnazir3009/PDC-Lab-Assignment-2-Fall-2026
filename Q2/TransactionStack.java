package Q2;

public class TransactionStack 
{
    private final char[] data;
    private int top;            // index of the top element; -1 when empty
    private final int capacity;


    public TransactionStack(int capacity) 
    {
        
        this.capacity = capacity;
        this.data = new char[capacity];
        this.top = -1; //-1 as stack is currently empty
    }


    public boolean isEmpty()
    {
        if (top == -1)
        return true; //true as stack is empty
        else
            return false; // as stack is not empty
    }
}
