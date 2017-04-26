/**
 * com.jsniff.Sniff.java
 * 
 * @author Nick Sardo <gcc.programmer@gmail.com>
 *
 */
package com.jsniff;
import java.util.*;

/**
 * The actual business-end of the sample:  create and run 
 * TCP Port Scans
 */
public class Sniff {
	
	static volatile List<Integer> tcparr = new ArrayList<Integer>();
	static volatile List<Integer> udparr = new ArrayList<Integer>();
	
	/*
	 * Utility Method
	 * Unoptimized
	 * Simplistic method to break the range of port numbers entered at the command
	 * line into roughly 10 even sub-ranges that can then be distributed among
	 * the ten threads, one sub-range per thread.
	 */
	static int[][] createSubRanges ( int b, int e ) {
		int[][] sub_ranges = new int[ 10 ][ 2 ];
		int beg = b;
		int end = e;
		final int range = ( end - beg ) / 10;
		int old = beg;
	
		for ( int i = 0; i < 10; i++ ) {
			int irange = 0;
			
			irange = old + range;
			
			if ( i == 9 )
				if ( irange < end ) irange = end;
			
			sub_ranges[ i ][ 0 ] = beg;
			sub_ranges[ i ][ 1 ] = irange;
			
			old = irange;
			beg = irange + 1;
		}
		return sub_ranges;
	}
	
	/*
	 * Worker method to spin up thread classes and display results
	 */
	static void doTCP( int[][] sr, String h ) {
		System.out.println( "Starting...\n" );
		final long startTime = System.currentTimeMillis();

		for ( int i = 0; i < 10; i++ ) {
			TCPSniffer s 	= new TCPSniffer( sr[ i ][ 0 ], sr[ i ][ 1 ], h );
			Thread t 			= new Thread( s );
			t.start();
			try {
				t.join();
			}
			catch ( InterruptedException e ) {}
		}
		if ( tcparr.size() <= 0 ) {
			System.out.println( "No TCP Ports were found" );
		} else {
			Collections.sort(tcparr);
			for( int i = 0; i < tcparr.size(); i++)
				System.out.printf( "There is a TCP port open at: %s\n", tcparr.get( i ).toString() + "\n" );
		}
		final long endTime = System.currentTimeMillis();
		System.out.println( "\nTotal approx. execution time: " + ( endTime - startTime ) / 1000.0 + " seconds" );
	}
	
	/*
	 * Dumbed down method for test
	 * Does all essential functions, but only returns one result for ease of testing
	 */
	static String doTCPTest( int[][] sr, String h ) {
		for ( int i = 0; i < 10; i++ ) {
			TCPSniffer s 	= new TCPSniffer( sr[ i ][ 0 ], sr[ i ][ 1 ], h );
			Thread t 			= new Thread( s );
			t.start();
			try {
				t.join();
			}
			catch ( InterruptedException e ) {}
		}
		
		return tcparr.get( 0 ).toString();
	}

	
	/*
	 * Main Class Entry Method
	 */
	public static void main( String[] args ) {

		int[][] sub_ranges = new int[ 10 ][ 2 ];
		
		if ( args.length < 4 ) { 
			System.out.println( "\nUsage: tcp ip beg_Range end_Range\n"
								+ "beg_range 1 - 65535\n"
								+ "end_range 1 - 65535\n" );
			System.exit( 1 );
		}
		if ( Integer.parseInt( args[ 2 ] ) < 1 ) {
			System.out.println( "\nPlease enter a beginning port from 1 to 65535\n" );
			System.exit( 1 );
		}
		if ( Integer.parseInt( args[ 3 ] ) > 65535 ) {
			System.out.println( "\nPlease enter an ending port from 1 to 65535\n" );
			System.exit( 1 );
		}
		
		String ip = args[ 1 ];
		int beg   = Integer.parseInt( args[ 2 ] );
		int end   = Integer.parseInt( args[ 3 ] );
	
		sub_ranges = Sniff.createSubRanges( beg, end );
		Sniff.doTCP( sub_ranges, ip );
		 
	}
}
