package Q3;

public class TelemetryThread extends Thread {
 
    @Override
    public void run()
    {
        Runtime rt = Runtime.getRuntime();

        for (int i = 1; i <= 5; i++) 
        {
            long usedMb = (rt.totalMemory() - rt.freeMemory()) / (1024 * 1024);
            long maxMb = rt.maxMemory() / (1024 * 1024);
            double load = java.lang.management.ManagementFactory.getOperatingSystemMXBean().getSystemLoadAverage();
            String cpu;
            if  (load < 0)
                cpu = "n/a"; 
            else 
                cpu = String.format("%.2f", load);
            int cores = rt.availableProcessors();
            System.out.println("[" + getName() + "] Health update " + i + "/5");
            System.out.println(" | CPU cores: " + cores + " | CPU load avg: " + cpu);
            System.out.println(" | Memory: " + usedMb + "MB / " + maxMb + "MB");
            
            try 
            {
                Thread.sleep(800);
            } 
            catch (InterruptedException e) 
            {
                System.out.println("[" + getName() + "] Interrupted.");
                Thread.currentThread().interrupt();
                return;
            }
        }
        System.out.println("[" + getName() + "] Telemetry reporting complete.");
    }
}
 