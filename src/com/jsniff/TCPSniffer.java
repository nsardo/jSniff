/**
 * com.jsniff.TCPSniffer.java
 * 
 * @author Nick Sardo <gcc.programmer@gmail.com>
 *
 */
package com.jsniff;
import java.net.*;
import java.io.*;

/**
 * Simple TCP Port sniffer operating under the assumption that if a socket can
 * connect, it is open on the server.
 */
class TCPSniffer implements Runnable {
  private String _host;
  int begin, end;
  
  public TCPSniffer ( int begin, int end, String host ) {
    this.begin  = begin;
    this.end    = end;
    setHost(host);
  }

  @Override
  public void run() {
    
    for ( int i = begin; i <= end; i++ ) {
      Thread.yield();
      try {
	     Socket s = new Socket();
	     s.connect( new InetSocketAddress( getHost(), i ), 100 );
       Sniff.tcparr.add( i );      
       s.close();
      }
      catch ( UnknownHostException e ) {
	     System.out.println( "UnknownHostException" );
       System.err.println( e.getStackTrace() );
      }
      catch ( IOException e ) {
	     System.out.println("port " + i + " is not open.");
      }
      catch ( SecurityException e ) {
	     System.out.println( "SecurityException" );
	     System.err.println( e.getStackTrace() );
      }
      Thread.yield();
    }//for
  }
  
  String getHost() {
    return _host;
  }
  
  void setHost( String h ) {
    //no error checking as we're all pal's here
    _host = h;
  }
  
}