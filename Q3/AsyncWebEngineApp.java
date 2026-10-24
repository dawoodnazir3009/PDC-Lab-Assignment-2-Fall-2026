package Q3;

public class AsyncWebEngineApp 
{
 
    public static void main(String[] args) 
    {
        // Default JVM thread name
        System.out.println("[main] Default JVM thread name: " + Thread.currentThread().getName());
 
        // Thread created by extending Thread
        TelemetryThread telemetry = new TelemetryThread();
        telemetry.setName("Telemetry-Worker");
 
        // Thread created by passing a Runnable
        Thread logWorker = new Thread(new LogProcessorTask());
        logWorker.setName("Log-Worker");
 
        System.out.print("[main] Before start -> " + telemetry.getName() + ": ");
        System.out.print( telemetry.getState() + " | " + logWorker.getName() + ": ");
        System.out.println(logWorker.getState() );
 
        // Start both threads back to back so they run concurrently
        telemetry.start();
        logWorker.start();
 
        System.out.print("[main] After start  -> " + telemetry.getName() + ": " + telemetry.getState());
        System.out.print(" (alive=" + telemetry.isAlive() + ") | " + logWorker.getName() + ": ");
        System.out.println(logWorker.getState() + " (alive=" + logWorker.isAlive() + ")");
 
        // main() waits for both workers to exit cleanly
        try 
        {
            telemetry.join();
            logWorker.join();
        } 
        
        catch (InterruptedException e) 
        
        {
            System.out.println("[main] Interrupted while waiting.");
            Thread.currentThread().interrupt();
        }
 
        System.out.print("[main] Final states -> " + telemetry.getName() + ": " + telemetry.getState());
        System.out.println(" | " + logWorker.getName() + ": " + logWorker.getState());
        System.out.println("[main] All workers finished. Terminating.");
    }
}
 