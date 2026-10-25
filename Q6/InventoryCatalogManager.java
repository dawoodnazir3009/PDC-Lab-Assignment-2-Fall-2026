package Q6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;


public class InventoryCatalogManager 
{

    private static final int THREADS = 10;
    private static final int TOTAL_OPERATIONS = 50_000;
    private static final int OPS_PER_THREAD = TOTAL_OPERATIONS / THREADS;
    private static final int PRODUCT_COUNT = 1_000;
    private static final int INITIAL_STOCK = 10;
    private static final int UPDATE_PERCENT = 30;   // 70% lookups, 30% stock updates
    private static final int WARMUP_ROUNDS = 3;
    private static final int MEASURED_ROUNDS = 5;

    private final String[] productIds = new String[PRODUCT_COUNT];

    public InventoryCatalogManager() 
    {
        for (int i = 0; i < PRODUCT_COUNT; i++) 
        {
            productIds[i] = "PROD-" + i;
        }
    }

  
    public static class BenchmarkResult 
    {
        final String name;
        final double millis;
        final double opsPerSecond;

        BenchmarkResult(String name, double millis, double opsPerSecond) 
        {
            this.name = name;
            this.millis = millis;
            this.opsPerSecond = opsPerSecond;
        }
    }


    public BenchmarkResult benchmarkSynchronizedMap() 
    {
        return runBenchmark("Collections.synchronizedMap(HashMap)",
                () -> Collections.synchronizedMap(new HashMap<String, Integer>()));
    }

  
    public BenchmarkResult benchmarkConcurrentMap() 
    {
        return runBenchmark("ConcurrentHashMap",
                () -> new ConcurrentHashMap<String, Integer>());
    }

    private BenchmarkResult runBenchmark(String name, Supplier<Map<String, Integer>> mapFactory) 
    {
        for (int i = 0; i < WARMUP_ROUNDS; i++) 
        {
            runOnce(mapFactory.get());    
        }

        long totalNanos = 0;
        System.out.println("\n--- " + name + " ---");
        for (int r = 1; r <= MEASURED_ROUNDS; r++) 
        {
            long nanos = runOnce(mapFactory.get());
            totalNanos += nanos;
            System.out.printf("  Round %d: %.3f ms%n", r, nanos / 1e6);
        }

        double avgNanos = (double) totalNanos / MEASURED_ROUNDS;
        double millis = avgNanos / 1e6;
        double opsPerSecond = TOTAL_OPERATIONS / (avgNanos / 1e9);

        System.out.printf("  Average execution time : %.3f ms%n", millis);
        System.out.printf("  Throughput             : %,.0f operations/second%n", opsPerSecond);
        return new BenchmarkResult(name, millis, opsPerSecond);
    }

    
    private long runOnce(Map<String, Integer> catalog) 
    {
        for (String id : productIds) 
        {
            catalog.put(id, INITIAL_STOCK);
        }

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        CountDownLatch ready = new CountDownLatch(THREADS);
        CountDownLatch startGate = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREADS);
        long[] sold = new long[THREADS];     
        long[] sinks = new long[THREADS];    

        for (int t = 0; t < THREADS; t++) 
        {
            final int id = t;
            pool.submit(() -> {
                ThreadLocalRandom rnd = ThreadLocalRandom.current();
                long[] successes = {0};
                long sink = 0;
                ready.countDown();
                try 
                {

                    startGate.await();       // all workers start together
                } 
                catch (InterruptedException e) 
                {
                    Thread.currentThread().interrupt();
                    done.countDown();
                    return;
                }

                for (int i = 0; i < OPS_PER_THREAD; i++) 
                {
                    String product = productIds[rnd.nextInt(PRODUCT_COUNT)];
                    if (rnd.nextInt(100) < UPDATE_PERCENT) 
                    {
                        
                        catalog.compute(product, (k, stock) -> {
                            if (stock != null && stock > 0) {
                                successes[0]++;
                                return stock - 1;
                            }
                            return stock;
                        });
                    } 

                    else     
                    {
                        Integer stock = catalog.get(product);   // lookup
                        if (stock != null) 
                        {
                            sink += stock;
                        }
                    }
                }
                sold[id] = successes[0];
                sinks[id] = sink;
                done.countDown();
            });
        }

        long elapsed;
        try 
        {
            ready.await();
            long t0 = System.nanoTime();
            startGate.countDown();
            done.await();
            elapsed = System.nanoTime() - t0;
            
        } 
        catch (InterruptedException e) 
        {
            Thread.currentThread().interrupt();
            pool.shutdownNow();
            return 0;
        }
        pool.shutdown();

        //  check: stock removed from the catalog must equal successful updates
        long totalSold = 0;
        for (long s : sold) 
        {
            totalSold += s;
        }
        long remaining = 0;
        for (Integer stock : catalog.values()) 
        {
            remaining += stock;
        }
        long expectedRemaining = (long) PRODUCT_COUNT * INITIAL_STOCK - totalSold;
        if (remaining != expectedRemaining) 
        {
            throw new IllegalStateException("Inventory corrupted: expected "
                    + expectedRemaining + " but found " + remaining);
        }
        return elapsed;
    }

    public static void main(String[] args) 
    {
        InventoryCatalogManager manager = new InventoryCatalogManager();

        System.out.println("--- Inventory Catalog Concurrency Benchmark ---");
        System.out.println("Worker threads: " + THREADS + " | Total operations: " + TOTAL_OPERATIONS
                + " | Products: " + PRODUCT_COUNT + " | Mix: " + (100 - UPDATE_PERCENT)
                + "% lookups / " + UPDATE_PERCENT + "% updates");
        System.out.println("Each result is averaged over " + MEASURED_ROUNDS + " rounds after "
                + WARMUP_ROUNDS + " warm-up rounds.");

        BenchmarkResult sync = manager.benchmarkSynchronizedMap();
        BenchmarkResult conc = manager.benchmarkConcurrentMap();

        System.out.println("\n--- Summary ---");
        System.out.printf("%-38s %12s %22s%n", "Collection", "Time (ms)", "Throughput (ops/sec)");
        System.out.printf("%-38s %12.3f %,22.0f%n", sync.name, sync.millis, sync.opsPerSecond);
        System.out.printf("%-38s %12.3f %,22.0f%n", conc.name, conc.millis, conc.opsPerSecond);
        System.out.printf("%nConcurrentHashMap throughput is %.2fx that of the synchronized map.%n",
                conc.opsPerSecond / sync.opsPerSecond);
        System.out.println("Inventory integrity check passed for all rounds.");
    }
}