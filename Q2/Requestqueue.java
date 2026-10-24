package Q2;

public class RequestQueue 
{
    private final char[] data;
    private int front;   // index of the next element to dequeue
    private int rear;    // index where the next element will be enqueued
    private int size;    // current number of elements
    private final int capacity;
 
    public RequestQueue(int capacity) 
    {
        this.capacity = capacity;
        this.data = new char[capacity];
        this.front = 0;
        this.rear = 0;
        this.size = 0;
    }


    
    
}
