package it.copyFile;

import java.io.*;
import java.nio.*;
import java.util.concurrent.*;

class Main {

  public static final int BUFFER_SIZE = 4;

  public static void main(String args[]) throws Exception {
    BlockingQueue<byte[]> buf = new ArrayBlockingQueue<byte[]>(BUFFER_SIZE); //istanzio un oggetto di classe ArrayBlockinQueue di tipo byte[],
    //che è una lista circolare bloccante.
    Fbyte leggi = new Fbyte(buf, args[0]);
    Bwrite scrivi = new Bwrite(buf, args[1]);
    try {
      leggi.start();
      scrivi.start();
      scrivi.join(); //attendo che il thread 0 muoia
      leggi.join(); //attendo che il thread 1 muoia
    } catch (InterruptedException ie) {
      System.out.println("Errore: " + ie);
    }
  }
}
