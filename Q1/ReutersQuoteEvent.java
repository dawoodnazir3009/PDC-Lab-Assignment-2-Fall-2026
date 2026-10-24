package Q1;

public class ReutersQuoteEvent extends TradeEvent {
    private double bidprice;
    private double askprice;

    public ReutersQuoteEvent(String SenderID, long timestamp, double price, double bidprice, double askprice)
    {
        this.bidprice = bidprice;
        this.askprice = askprice;
        super(SenderID, timestamp, price);
    }


    @Override 
    public void dispatch()
    {
        double s = askprice - bidprice;
        double midprice = (askprice + bidprice) / 2.0;
        double spreadPCT = (s / midprice) * 100.0; //percentage

        System.out.println("[REUTERS QUOTE] Real-time bid-ask analytics");
        System.out.println("\tSender        : " + SenderID);
        System.out.println("\tTimestamp     : " + timestamp);
        System.out.println("\tBase Price    : $" + String.format("%.2f", price));
        System.out.println("\tBid / Ask     : $" + String.format("%.2f", bidprice) + " / $" + String.format("%.2f", askprice));
        System.out.println("\tSpread        : $" + String.format("%.4f", s));
        System.out.println("\tMid Price     : $" + String.format("%.4f", midprice));
        System.out.println("\tSpread (%)    : " + String.format("%.4f", spreadPCT) + "% ");


    }
    
}
