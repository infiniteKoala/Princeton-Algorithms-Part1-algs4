import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private static final int top = 0;
    private final boolean[][] opened; // grid[i][j] == 0 for blocked, 1 for open
    private final int bottom;
    private final int size;
    private int openedSites;
    private final WeightedQuickUnionUF uf; // for percolates

    // creates n-by-n grid
    // matrix dimension => size x size
    //Virtual sites > 2 top/bottom
    // Matrix with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0)
            throw new java.lang.IllegalArgumentException();

        size = n;
        bottom = size * size + 1;
        uf = new WeightedQuickUnionUF(size * size + 2);
        opened = new boolean[n][n];

    }

    //Check illegal argument exception
    private void checkException(int row, int col) {
        if (row <= 0 || row > size || col <= 0 || col > size) {
            throw new IllegalArgumentException();
        }
    }

    // opens the site (row, col) if it is not open already
    //site opened if value => true
    //site blocked if value => false

    //if they are open, make a union call
    public void open(int row, int col) {
        checkException(row, col);
        opened[row - 1][col - 1] = true;
        //open the site

        //If any of the top row boxes are opened => union(site, top)
        if (row == 1) {
            uf.union(getUFIndex(row, col), top);
        }

        //If any of the bottom row sites are opened => union(site, bottom)
        if (row == size) {
            uf.union(getUFIndex(row, col), bottom);
        }

        //check if middle sites are adjacent to any open sites
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(getUFIndex(row, col), getUFIndex(row - 1, col));
        }

        if (row < size && isOpen(row + 1, col)) {
            uf.union(getUFIndex(row, col), getUFIndex(row + 1, col));
        }

        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(getUFIndex(row, col), getUFIndex(row, col - 1));
        }

        if (col < size && isOpen(row, col + 1)) {
            uf.union(getUFIndex(row, col), getUFIndex(row, col + 1));
        }
    }

    private int getUFIndex (int row, int col) {
        return size*(row - 1) + col;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkException(row, col);
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    //an open site that is connected to an open site in the top row
    public boolean isFull(int row, int col) {
       if ((row > 0 && row <= size) && (col > 0 && col <= size)) {
           return uf.find(top) == uf.find(getUFIndex(row, col));
       } else throw new IllegalArgumentException();
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return openedSites;
    }

    // does the system percolate?
    //the system percolates if there is a full site in the bottom row
    public boolean percolates(){

        //if top is connected to bottom, then the system percolates
        return uf.find(top) == uf.find(bottom);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
