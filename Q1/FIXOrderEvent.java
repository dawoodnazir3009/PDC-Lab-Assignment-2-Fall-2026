package Q1;

public class FIXOrderEvent extends TradeEvent 
{
    
    private static final double TRANSACTION_FEE_RATE = 0.0015; // 0.15%
    
        private String clOrdId;
    
        public FIXOrderEvent(String SenderID, long timestamp, double basePrice, String clOrdId) {
            super(SenderID, timestamp, basePrice);
            this.clOrdId = clOrdId;
        }

        @Override 
        public void dispatch()
        {
            double fee = price * TRANSACTION_FEE_RATE;
            double totalcost = price + fee;
            System.out.println("[FIX ORDER] Executing stock purchase request");
            System.out.println("\tSender        : " + SenderID);
            System.out.println("\tClOrdID       : " + clOrdId);
            System.out.println("\tTimestamp     : " + timestamp);
            System.out.println("\tBase Price    : $" + String.format("%.2f", price));
            System.out.println("\tFee (0.15%)   : $" + String.format("%.4f", fee));
            System.out.println("\tTotal Cost    : $" + String.format("%.4f", totalcost));
            System.out.println("\tStatus        : ORDER EXECUTED successfully");

        }

}
