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
        System.out.println("Generic Trade Event");
        System.out.println("Sender ID is : " + SenderID);
        System.out.println("Timestamp is : " + timestamp);
        System.out.println("Base Price is : " + price);

    }
    
}
