package Q3;

public class TelemetryThread extends Thread {
 
    @Override
    public void run()
    {
        Runtime rt = Runtime.getRuntime();

        
        System.out.println("[" + getName() + "] Telemetry reporting complete.");
    }
}
 