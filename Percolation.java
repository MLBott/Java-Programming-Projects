package algs15.perc;

import stdlib.*;
import algs15.*;

// Uncomment the import statements above.

// You can test this using InteractivePercolationVisualizer and PercolationVisualizer
// All methods should make at most a constant number of calls to the UF data structure,
// except percolates(), which may make up to N calls to the UF data structure.
public class Percolation {
	int N;
	boolean[] open;
	// TODO: more fields to add here
	private UF uf;
	
	public Percolation(int N) {
		this.N = N;
		this.open = new boolean[N*N];
		// TODO: more to do here

	    this.uf = new WeightedUF((N * N) + 2);
	    int m = 0;
	    while (m < N) {
	    	this.open[m] = false;
	    	m++;
	    }
	}
	// open site (row i, column j) if it is not already
	public void open(int i, int j) {
		open[i*N+j] = true;
		// TODO: more to do here.
		int pos = (i * N) + j;
	      
	    if (i > 0) { 
	        if (isOpen(i - 1, j)) {
	        	uf.union(pos + 1, (pos - N) + 1);
	        }
	    } else { 
	    	uf.union(0, pos + 1);
	    }	       
	    if (i < N - 1) { 
	    	if (isOpen(i + 1, j)) {
	    		uf.union(pos + 1, pos + N + 1);
	        }
	    } else { 
	    	uf.union((N * N) + 1, pos + 1);
	    }
	    if (j > 0 && isOpen(i, j - 1)) { 
	    	uf.union(pos + 1, (pos - 1) + 1);
	    }   
	    if (j < N - 1 && isOpen(i, j + 1)) { 
	    	uf.union(pos + 1, pos + 1 + 1);
	    }
	}
	// is site (row i, column j) open?
	public boolean isOpen(int i, int j) {
		return open[(N * i) + j];
	}
	// is site (row i, column j) full?
	public boolean isFull(int i, int j) {
		// TODO
		return !open[(N * i) + j];
	}
	// does the system percolate?
	public boolean percolates() {
		// TODO
		return uf.connected(0, (N * N) + 1);
	}
}