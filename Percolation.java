import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    // size
    private int n;
    // boolean array to keep track of open sites
    private boolean[][] open;
    // Weighted quick union find object
    private WeightedQuickUnionUF qf;
    // number of open sites
    private int openSites;
    // the sink index
    private int end;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be positive");
        }
        this.n = n;
        open = new boolean[n][n];
        // qf = new QuickFindUF(n * n + 2);
        qf = new WeightedQuickUnionUF(n * n + 2);
        end = n * n + 1;
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        if (row < 0 || row >= n || col < 0 || col >= n)
            throw new IllegalArgumentException("Outside Prescribed Range");

        if (!(isOpen(row, col))) {
            openSites++;
            if (!open[row][col])
                open[row][col] = true;

            // checking top boxes
            if (row == 0) {
                qf.union(indexOfGrid(row, col), 0);
            }

            // check bottom boxes
            if (row == n - 1) {
                qf.union(indexOfGrid(row, col), end);
            }


            // top
            if (row > 0 && isOpen(row - 1, col)) {
                qf.union(indexOfGrid(row, col), indexOfGrid(row - 1, col));
            }


            // left
            if (col > 0 && isOpen(row, col - 1)) {
                qf.union(indexOfGrid(row, col), indexOfGrid(row, col - 1));
            }

            // right
            if (col < n - 1 && isOpen(row, col + 1)) {
                qf.union(indexOfGrid(row, col), indexOfGrid(row, col + 1));
            }

            // bottom
            if (row < n - 1 && isOpen(row + 1, col)) {
                qf.union(indexOfGrid(row, col), indexOfGrid(row + 1, col));
            }


        }


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 0 || row >= n || col < 0 || col >= n)
            throw new IllegalArgumentException("Outside Prescribed Range");
        return open[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!(row >= 0 && row < n) || !(col >= 0 && col < n)) {
            throw new IllegalArgumentException("Outside Prescribed Range");
        }
        else {
            return qf.find(0) == qf.find(indexOfGrid(row, col));
        }
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return qf.find(0) == qf.find(end);
    }

    // finds the index of the location on the 2d array
    private int indexOfGrid(int row, int col) {
        return row * n + col + 1;
    }

    // unit testing (required)
    public static void main(String[] args) {
        int size = StdIn.readInt();
        Percolation test = new Percolation(size);
        while (!StdIn.isEmpty()) {
            int row = StdIn.readInt();
            int col = StdIn.readInt();
            test.open(row, col);

        }
        StdOut.println(test.percolates());
        int row = StdRandom.uniformInt(size);
        int col = StdRandom.uniformInt(size);
        StdOut.println("Is the site " + row + ", " + col +
                               " is open: " + test.isOpen(row, col));
        StdOut.println("Is the site " + row + ", " + col +
                               " is full: " + test.isFull(row, col));
        StdOut.println("Number of open sites: " + test.numberOfOpenSites());
        StdOut.println("Does it percolates: " + test.percolates());


    }
}
