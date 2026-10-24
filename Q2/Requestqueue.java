package Q2;

public class Requestqueue 
{
    private final char[] data;
    private int front;   // index of the next element to dequeue
    private int rear;    // index where the next element will be enqueued
    private int size;    // current number of elements
    private final int capacity;
 
    public Requestqueue(int capacity) 
    {
        this.capacity = capacity;
        this.data = new char[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }

    public boolean isEmpty() 
    {
        if (size == 0)
            return true;
        else 
            return false;
        
    }
 
    public boolean isFull() 
    {
        return size == capacity;
    }
 
    // Adds characyer "c" at the rear
    public boolean enqueue(char c) 
    {
        if (isFull()) 
        {
            System.out.println("QUEUE OVERFLOW! Cannot enqueue \'" + c + "\'");
            return false;
        }
        data[rear] = c;
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }
 
    // Removes and returns the front element dequeue 
    public char dequeue() 
    {
        if (isEmpty()) 
        {
            System.out.println("QUEUE UNDERFLOW! Nothing to dequeue");
            return '\0';
        }
        char c = data[front];
        front = (front + 1) % capacity;
        size--;
        return c;
    }



    
}
