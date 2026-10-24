package Q1;

public class TradeEvent {

    protected String SenderID;
    protected long timestamp;
    protected double price;



    TradeEvent(String SenderID, long timestamp, double price)
    {
        this.SenderID = SenderID;
        this.timestamp = timestamp;
        this.price = price;
    }

    void dispatch()
    {
        
    }
    
}
