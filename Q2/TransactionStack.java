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

    public boolean isFull() 
    {

        if (top == (capacity -1))
            return true; //true as stack is full
        else
            return false; // as stack contains space
    }
 
    
    public boolean push(char c)
    {
        if (isFull()) 
        {
            System.out.println("STACK OVERFLOW! Cannot push \'" + c + "\'");
            return false;
        }
        //adding to stack
        top++;
        data[top] = c;
        return true; //successfully added
    }
 
    
    public char pop() 
    {
        if (isEmpty()) 
        {
            System.out.println("!STACK UNDERFLOW! Nothing to pop");
            return '\0'; //line end caharcer
        }
        return data[top--];
    }
 
    
    public char peek() 
    {
        if (isEmpty())
        {
            System.out.println("STACK EMPTY! Nothing to peek");
            return '\0';
        }
        return data[top];
    }
}
