package Q1;

public class TradingEngineApp 
{
    public static void main(String[] args) 
    {
        long now = System.currentTimeMillis();
 
        TradeEvent generic = new TradeEvent("SYS-CORE", now, 150.00);
        TradeEvent fixOrder = new FIXOrderEvent("FIX-GW-01", now + 5, 187.50, "CLORD-100234");
        TradeEvent quote = new ReutersQuoteEvent("REUTERS-RT", now + 10, 187.45, 187.40, 187.55);
 
        TradeEvent[] events = { generic, fixOrder, quote };
 
        System.out.println("\t\t\tTrading Engine: Dispatching Event\n");
        for (TradeEvent event : events) 
        {
            event.dispatch();   // dynamic method dispatch at run time
            System.out.println();
        }
    }
}