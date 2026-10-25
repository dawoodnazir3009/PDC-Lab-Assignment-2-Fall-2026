package Q4;
import java.util.concurrent.RecursiveTask;
 

public class ParallelRiskCalculator extends RecursiveTask<Double> 
{
    private static final int THRESHOLD = 1000;
 
    private final double[] assets;
    private final int start;   // inclusive
    private final int end;     // exclusive
 
    public ParallelRiskCalculator(double[] assets, int start, int end) 
    {
        this.assets = assets;
        this.start = start;
        this.end = end;

    }

    @Override 
    public Double compute()
    {
        double sum = 0.0;

        return sum;

    }






}


