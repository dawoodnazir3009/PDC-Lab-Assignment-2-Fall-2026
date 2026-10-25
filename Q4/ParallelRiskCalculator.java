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
    protected Double compute() 
    {
        int length = end - start;
 
       
        if (length <= THRESHOLD) 
        {
            double sum = 0.0;

            int i = start;
            for (; i < end; i++) 
            {
                sum += assets[i];
            }
            return sum;
        }
 
        
        int mid = start + length / 2;
        ParallelRiskCalculator left = new ParallelRiskCalculator(assets, start, mid);
        ParallelRiskCalculator right = new ParallelRiskCalculator(assets, mid, end);
 
        left.fork();
        right.fork();
 
        double leftResult = left.join();
        double rightResult = right.join();
        
        return leftResult + rightResult;
    }
}
 