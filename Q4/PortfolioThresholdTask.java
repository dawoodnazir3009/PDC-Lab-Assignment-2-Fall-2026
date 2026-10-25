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

        
    }
        
}
 