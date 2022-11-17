package it.copyFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.BlockingQueue;

class Bwrite extends Thread {

  private final BlockingQueue<byte[]> buffer; //istanzio una coda circolare bloccante
  private final String file; //nome del file di output
  
  private static final int BUFFER_LEN = 8192; //Leggo 32 byte alla volta

  public Bwrite(BlockingQueue<byte[]> buffer, String file) {
    this.buffer = buffer;
    this.file = file;
  }

  //metodo eseguito dal thread
  public void run() {
    try {
      FileOutputStream fout = new FileOutputStream(file);
      byte[] b;
      while (isAlive()) {
        try {
          b = buffer.take(); //inserisco la testa del buffer in b
          fout.write(b); //scrivo b sul file
          System.out.println("Buffer written: " + b + " | " + new String(b, StandardCharsets.UTF_8) + " | ");
          if (b.length < BUFFER_LEN) {
            fout.close();
            System.exit(0);
          }
        } catch (Exception e) {
          System.out.println("Errore: " + e);
          fout.close();
          System.exit(1);
        }
      }
    } catch (IOException e) {
      System.out.println("Errore");
    }
  }
}
