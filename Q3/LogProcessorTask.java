package Q3;


public class LogProcessorTask implements Runnable 
{
 
    @Override
    public void run() 
    {
        for (int i = 1; i <= 5; i++) 
        {
            System.out.println("[" + Thread.currentThread().getName() + "] "
                    + "Log analysis iteration " + i + "/5 - processed batch "
                    + (i * 250) + " log entries");
            Thread.sleep(1000);
            
        }
        System.out.println("[" + Thread.currentThread().getName() + "] Log processing complete.\n");
    }
}