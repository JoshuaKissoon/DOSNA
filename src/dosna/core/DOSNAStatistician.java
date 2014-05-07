package dosna.core;

import java.text.DecimalFormat;

/**
 * Statistician for DOSNA.
 *
 * @author Joshua Kissoon
 * @since 20140507
 */
public class DOSNAStatistician
{
    /* Activity Stream load times */

    private int numActivityStreamLoads;
    private long totalActivityStreamLoadTime;

    
    {
        this.numActivityStreamLoads = 0;
        this.totalActivityStreamLoadTime = 0;
    }

    /**
     * Add information about a loading of the activity stream
     *
     * @param loadTime How long the stream took to load in nanoseconds
     */
    public void addActivityStreamLoad(long loadTime)
    {
        this.numActivityStreamLoads++;
        this.totalActivityStreamLoadTime += loadTime;
    }

    /**
     * Compute the average time it takes for an instance of the activity stream to load
     *
     * @return The average time in milliseconds
     */
    public double avgActivityStreamLoadTime()
    {
        Double avg = (double) ((double) totalActivityStreamLoadTime / (double) numActivityStreamLoads) / 1000000D;
        DecimalFormat df = new DecimalFormat("#.00");
        return new Double(df.format(avg));
    }
}
