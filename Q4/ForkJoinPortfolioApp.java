package Q4;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;


public class ForkJoinPortfolioApp 
{
    private static final double RISK_CAP = 800.0;

    private static double serialSum(double[] a) 
    {
        double sum = 0.0;
        for (double v : a) 
        {
            sum += v;
        }
        return sum;
    }

    private static void serialStress(double[] a) 
    {
        for (int i = 0; i < a.length; i++) 
        {
            if (a[i] > RISK_CAP) 
            {
                a[i] -= a[i] * 0.05;
            }
        }
    }

    public static void main(String[] args) 
    {
        
    }
}