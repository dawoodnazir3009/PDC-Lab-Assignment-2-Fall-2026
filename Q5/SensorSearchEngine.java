package Q5;

public abstract class SensorSearchEngine 
{
    protected double[] sensorData;
    protected int startIdx, endIdx, numThreads;
    protected double result;
    protected long elapseNanos;


    SensorSearchEngine(double[] sensorData, int startIdx, int endIdx, int numThreads)
    {
        if (startIdx>=endIdx)
            throw new IllegalArgumentException("Empty Sensor Segment");

        this.startIdx = startIdx;
        this.endIdx = endIdx;
        this.sensorData = sensorData;
        this.numThreads = numThreads;
    }

    public abstract void processGrid();

    // getters
    public double getResult()
    {
        return result;
    }

    public double getElapseMillis()
    {
        return elapseNanos / 1e6;
    }

    protected int[] partitionBounds()
    {

        int length = endIdx - startIdx;
        int chunk = length / numThreads;
        int[] bounds = new int[numThreads + 1];

        for(int i =0; i < numThreads; i++)
        {

            bounds[i] = startIdx + i * chunk;
        }

        bounds[numThreads] = endIdx;
        return bounds;

    }


    
    
}
