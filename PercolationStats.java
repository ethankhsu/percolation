import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {

    // double array that keeps track of the fraction of opensites
    private double[] fraction;
    // zscore for 95 percent confidence intervals
    private static final double NINETYFIVE = 1.96;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("Invalid inputs");

        fraction = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation test = new Percolation(n);
            int openSites = 0;
            while (!test.percolates()) {
                int row = StdRandom.uniformInt(n);
                int col = StdRandom.uniformInt(n);
                if (!test.isOpen(row, col)) {
                    openSites++;
                    test.open(row, col);
                }
            }
            fraction[i] = (double) openSites / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fraction);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        return mean() - NINETYFIVE * stddev() / Math.sqrt(fraction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        return mean() + NINETYFIVE * stddev() / Math.sqrt(fraction.length);
    }

    // test client (see below)
    public static void main(String[] args) {
        Stopwatch time = new Stopwatch();
        int size = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats test = new PercolationStats(size, trials);
        StdOut.printf("%-16s = %.6f\n", "mean()", test.mean());
        StdOut.printf("%-16s = %.6f\n", "stddev()", test.stddev());
        StdOut.printf("%-16s = %.6f\n", "confidenceLow()",
                      test.confidenceLow());
        StdOut.printf("%-16s = %.6f\n", "confidenceHigh()",
                      test.confidenceHigh());
        StdOut.printf("%-16s = %.6f\n", "elapsed time",
                      time.elapsedTime());
    }

}
