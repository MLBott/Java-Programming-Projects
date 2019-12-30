package algs13;
import stdlib.*;
import java.text.DecimalFormat;
import java.util.NoSuchElementException;

/**
 * This is a skeleton file for your homework.
 * Complete the functions below. 
 * You may also edit the function "main" to test your code.
 * 
 * You should not use any loops or recursions, except in "delete"
 * Delete may use one loop or recursive helper.
 *
 * You must not add fields or static variables.
 * As always, you must not change the declaration of any method.
 * Do not change any of the methods I have provided, such as "toString" and "check".
 */

public class MyDeque {
	Node first = null;
	Node last = null;
	int N = 0;

	static class Node {
		public Node() { }
		public double item;
		public Node next;
		public Node prev;
	}

	public MyDeque ()         { };
	public boolean isEmpty () { return N == 0; }
	public int size ()        { return N; }

	public void pushLeft (double item) {
		// TODO
		Node x = new Node();
		x.item = item;
		if (isEmpty()) {
			last = x;
			first = last;
		} else {
			Node oldfirst = first;
			first = x;
			x.next = oldfirst;
			oldfirst.prev = x;
		}
		N++;

	}

	public void pushRight (double item) {
		// TODO
		Node x = new Node();
        x.item = item;
        if(isEmpty())
        {
        	x.prev = last; 
        	x.next = null;
        	last = x;
			first = last;
			last = x;
        } else {
        	x.prev = last; 
        	x.next = null;
            last.next = x;
            last = x;
        }
        N++;
	}

	public double popLeft () {
		if (N == 0) throw new NoSuchElementException ();
		double result = first.item;
        first = first.next;
        if(first != null) {
        	first.prev = null;
        } else {
        	last = null;
        }
        N--;
        return result;
		// TODO
	}

	public double popRight () {
		if (N == 0) throw new NoSuchElementException ();
		double result = last.item;
        last = last.prev;
        if(last != null) {
        	last.next = null;
        } else {
        	first = null;
        }
        N--;
        return result;
		// TODO
	}

	/* The concat method should take the Nodes from "that" and add them to "this"
	 * After execution, "that" should be empty.
	 * See the tests in the main program.
	 *
	 * Do not use a loop or a recursive definition.
         * This method can declare Node variables, but may not create new node objects (using "new").
	 * Therefore the method should not call pushLeft or pushRight (which use create new objects).
	 */
	public void concat (MyDeque that) {
		// TODO
		if (that.isEmpty()) {
			return;
		}
		if (this.isEmpty()) {
			this.first = that.first;
		} else {
			that.first.prev = this.last;
			this.last.next = that.first;
		}
		that.first = null;
		this.last = that.last;
		that.last = null;
		        
		this.N += that.N;
		that.N = 0;
	}

	/* Delete should delete and return the kth element from the left (where k is between 0 and N-1).
	 * See the tests in the main program.
	 *
	 * You may use a loop or a recursive definition here.
         * This method can declare Node variables, but may not create new node objects (using "new").
	 * Therefore the method should not call pushLeft or pushRight (which use create new objects).
	 */
	public double delete (int k) {
		if (k < 0 || k >= N) throw new IllegalArgumentException ();
		// TODO 
		Node x = this.first;
        double result;
        for (int i = 0; i < k; i++)
        {
            x = x.next;
        }
        if(x.next == null)
        {
            if(x.prev == null)
            {
                first = null;
                last = first;
            } else {
                x.prev.next = null;
                last = x.prev;
            }
        } else {
            if(x.prev == null)
            {
                x.next.prev = null;
                first = x.next;
            } else {
                x.prev.next = x.next;
                x.next.prev = x.prev;
            }
        }
        result = x.item;
        N--;
        return result;
		
	}

	public MyDeque (String s) {
		String[] nums = s.split (" ");
		for (int i = nums.length-1; i >= 0; i--) {
			try { 
				pushLeft (Double.parseDouble (nums[i]));			
			} catch (NumberFormatException e) {	}
		}
	}
	public String toString () { 
		DecimalFormat format = new DecimalFormat ("#.###");
		StringBuilder result = new StringBuilder ("[ ");
		for (Node x = first; x != null; x = x.next) {
			result.append (format.format (x.item));
			result.append (" ");
		}
		result.append ("]");
		return result.toString ();
	}


	public static void checkInvariants (String message, MyDeque that) {
		int N = that.N;
		MyDeque.Node first = that.first;
		MyDeque.Node last = that.last;

		if (N < 0) throw new Error ();
		if (N == 0) {
			if (first != null || last != null) {
				showError (String.format ("%s: N==0, Expected first,last == null.", message));
			}
		} else {
			if (first == null || last == null) {
				showError (String.format ("%s: N!=0, Expected first,last != null.", message));
			}
		}
		if (N > 0) {
			MyDeque.Node prev = null;
			MyDeque.Node current = first;
			for (int i = 0; i < N; i++) {
				if (current == null) {
					showError (String.format ("%s: N==%d, Expected %d next nodes, but got less.", message, N, N));
				}
				if (current.prev != prev) { 
					showError (String.format ("%s: i=%d, Broken prev link.", message, i));
				}
				prev = current;
				current = current.next;
			}
			if (current != null) {
				showError (String.format ("%s: N==%d, Expected %d next nodes, but got more.", message, N, N));
			}
			MyDeque.Node next = null;
			current = last;
			for (int i = 0; i < N; i++) {
				if (current == null) {
					showError (String.format ("%s: N==%d, Expected %d prev nodes, but got less.", message, N, N));
				}
				if (current.next != next) {
					showError (String.format ("%s: i=%d, Broken next link.", message, i));
				}
				next = current;
				current = current.prev;
			}
			if (current != null) {
				showError (String.format ("%s: N==%d, Expected %d prev nodes, but got more.", message, N, N));
			}
		}
	}
	private static void check (String message, MyDeque actual, String expected) {
		checkInvariants (message, actual);
		if (expected != null) {
			if (!expected.equals (actual.toString ())) {
				showError ("Expected \"" + expected + "\", got \"" + actual + "\"");
			}
		}
	}
	private static void check (String message, MyDeque actual, String expected, double dActual, double dExpected) {
		if (dExpected != dActual) {
			showError ("Expected \"" + dExpected + "\", got \"" + dActual + "\"");
		}
		check (message, actual, expected);
	}
	
	private static void showError (String message) {
		Trace.draw ();
		StdOut.println (message);
		throw new Error (); // stops execution
	}
	public static void main (String args[]) {
		//Trace.drawStepsOfMethod ("main");
		Trace.run ();

		// Here are some tests to get you started.
		// You can edit this all you like.
		MyDeque d1, d2, d3;
		double k;

		////////////////////////////////////////////////////////////////////
		// push/pop tests
		////////////////////////////////////////////////////////////////////
		d1 = new MyDeque ();
		d1.pushLeft (11);
		check ("left1", d1, "[ 11 ]");
		d1.pushLeft (12);
		check ("left2", d1, "[ 12 11 ]");
		d1.pushLeft (13);
		check ("left3", d1, "[ 13 12 11 ]");
		k = d1.popLeft ();
		check ("left4", d1, "[ 12 11 ]", k, 13);
		k = d1.popLeft ();
		check ("left5", d1, "[ 11 ]", k, 12);
		k = d1.popLeft ();
		check ("left6", d1, "[ ]", k, 11);

		d1 = new MyDeque ();
		d1.pushRight (11);
		check ("right1", d1, "[ 11 ]");
		d1.pushRight (12);
		check ("right2", d1, "[ 11 12 ]");
		d1.pushRight (13);
		check ("right3", d1, "[ 11 12 13 ]");
		k = d1.popRight ();
		check ("right4", d1, "[ 11 12 ]", k, 13);
		k = d1.popRight ();
		check ("right5", d1, "[ 11 ]", k, 12);
		k = d1.popRight ();
		check ("right6", d1, "[ ]", k, 11);
		
		d1 = new MyDeque ();
		d1.pushLeft (11);
		check ("left/right1", d1, "[ 11 ]");
		d1.pushRight (21);
		check ("left/right2", d1, "[ 11 21 ]");
		d1.pushLeft (12);
		check ("left/right3", d1, "[ 12 11 21 ]");
		d1.pushRight (22);
		check ("left/right4", d1, "[ 12 11 21 22 ]");
		k = d1.popLeft ();
		check ("left/right5", d1, "[ 11 21 22 ]", k, 12);
		k = d1.popLeft ();
		check ("left/right6", d1, "[ 21 22 ]", k, 11);
		k = d1.popLeft ();
		check ("left/right7", d1, "[ 22 ]", k, 21);
		k = d1.popLeft ();
		check ("left/right8", d1, "[ ]", k, 22);
		
		d1 = new MyDeque ();
		d1.pushLeft (11);
		check ("left/right9", d1, "[ 11 ]");
		d1.pushRight (21);
		check ("left/right10", d1, "[ 11 21 ]");
		d1.pushLeft (12);
		check ("left/right11", d1, "[ 12 11 21 ]");
		d1.pushRight (22);
		check ("left/right12", d1, "[ 12 11 21 22 ]");
		k = d1.popRight ();
		check ("left/right13", d1, "[ 12 11 21 ]", k, 22);
		k = d1.popRight ();
		check ("left/right14", d1, "[ 12 11 ]", k, 21);
		k = d1.popRight ();
		check ("left/right15", d1, "[ 12 ]", k, 11);
		k = d1.popRight ();
		check ("left/right16", d1, "[ ]", k, 12);

		////////////////////////////////////////////////////////////////////
		//  test exceptions
		////////////////////////////////////////////////////////////////////
		try {
			d1.popLeft ();
			showError ("Expected exception1");
		} catch (NoSuchElementException e) {}
		try {
			d1.popRight ();
			showError ("Expected exception2");
		} catch (NoSuchElementException e) {}

		////////////////////////////////////////////////////////////////////
		// concat tests (and more push/pop tests)
		////////////////////////////////////////////////////////////////////
		d1 = new MyDeque ();
		d1.concat (new MyDeque ());
		check ("concat1", d1, "[ ]");
		d1.pushLeft (11);
		d1.concat (new MyDeque ());
		check ("concat2", d1, "[ 11 ]");

		d1 = new MyDeque ();
		d2 = new MyDeque ();
		d2.pushLeft (11);
		d1.concat (d2);
		check ("concat3", d1, "[ 11 ]");

		d1 = new MyDeque ();
		d2 = new MyDeque ();
		d1.pushLeft (11);
		d1.pushLeft (12);
		d2.pushLeft (21);
		d2.pushLeft (22);
		d1.concat (d2);
		check ("concat4", d1, "[ 12 11 22 21 ]");
		check ("concat5", d2, "[ ]");
		
		d1 = new MyDeque ();
		for (int i = 10; i < 13; i++) { d1.pushLeft (i); checkInvariants ("left1", d1); }
		for (int i = 20; i < 23; i++) { d1.pushRight (i); checkInvariants ("right2", d1); }
		check ("concat6", d1, "[ 12 11 10 20 21 22 ]");
		d2 = new MyDeque ();
		d1.concat (d2);
		check ("concat7", d1, "[ 12 11 10 20 21 22 ]");
		check ("concat8", d2, "[ ]");

		for (int i = 30; i < 33; i++) { d2.pushLeft (i); checkInvariants ("left3", d2); }
		for (int i = 40; i < 43; i++) { d2.pushRight (i); checkInvariants ("right4", d2); }
		check ("concat9", d2, "[ 32 31 30 40 41 42 ]");

		d3 = new MyDeque ();
		d2.concat (d3);
		check ("concat10", d2, "[ 32 31 30 40 41 42 ]");
		check ("concat11", d3, "[ ]");

		d1.concat (d2);
		check ("concat12", d1, "[ 12 11 10 20 21 22 32 31 30 40 41 42 ]");
		check ("concat13", d2, "[ ]");
		for (int i = 0; i < 12; i++) { d1.popLeft (); checkInvariants ("left5", d1); }
		////////////////////////////////////////////////////////////////////
		// delete tests
		////////////////////////////////////////////////////////////////////
		d1 = new MyDeque ();
		d1.pushLeft (11);
		k = d1.delete (0);
		check ("delete1", d1, "[ ]", k, 11);
		for (int i = 10; i < 20; i++) { d1.pushRight (i); checkInvariants ("right6", d1); }
		k = d1.delete (0);
		check ("delete2", d1, "[ 11 12 13 14 15 16 17 18 19 ]", k, 10);
		k = d1.delete (8);
		check ("delete3", d1, "[ 11 12 13 14 15 16 17 18 ]", k, 19);
		k = d1.delete (4);
		check ("delete4", d1, "[ 11 12 13 14 16 17 18 ]", k, 15);
		StdOut.println ("Finished tests");
	}
}
