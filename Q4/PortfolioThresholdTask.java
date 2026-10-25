package Q4;

import java.util.concurrent.RecursiveAction;

public class PortfolioThresholdTask extends RecursiveAction
{
    private static final int THRESHOLD = 1000;
    private static final double PENALTY = 0.05; //5 percent

    private final double[] assets;
    private final int start;
    private final int end;
    private final double riskCap;


     public PortfolioThresholdTask (double[] assets, int start, int end, double riskCap)
    {
        this.assets = assets;
        this.start = start;
        this.end = end;
        this.riskCap = riskCap;
    }


    @Override
    protected void compute() 
    {
        int length = end - start;
 
        if (length <= THRESHOLD) 
        {
            for (int i = start; i < end; i++) 
            {
                if (assets[i] > riskCap) 
                {
                    assets[i] -= assets[i] * PENALTY;   // apply 5% penalty
                }
            }
            return;
        }
 
        int mid = start + length / 2;
        PortfolioThresholdTask left = new PortfolioThresholdTask(assets, start, mid, riskCap);
        PortfolioThresholdTask right = new PortfolioThresholdTask(assets, mid, end, riskCap);
 
        // Fork both halves and wait for them to finish
        invokeAll(left, right);
    }
}
 