import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private Percolation perc;
    private int trialCount;
    private double [] fractions;

    // perform independent  on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("Out of bounds");
        }

        trialCount = trials;
        fractions = new double[trialCount];

        for (int currentTrial = 0; currentTrial < trialCount; currentTrial++) {
            perc = new Percolation(n);
            int numberOfOpenedSites = 0;

            while (!perc.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    numberOfOpenedSites++;
                }
            }

            //fraction of open sites in computational experiment
            double fraction = (double) numberOfOpenedSites / (n*n);
            fractions[currentTrial] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (1.96*stddev()/Math.sqrt(trialCount));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (1.96*stddev()/Math.sqrt(trialCount));
    }

    // test client (see below)
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(N, T);

        String confidence = ps.confidenceLo() + ", " + ps.confidenceHi();
        StdOut.println("mean                          = " + ps.mean());
        StdOut.println("stddev                        = " + ps.stddev());
        StdOut.println("95% confindence interval      = " + confidence);

    }
}
