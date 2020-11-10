import java.io.*;
import java.util.concurrent.*;

class bwrite extends Thread{
  private final BlockingQueue<byte[]> buffer;     //istanzio una coda circolare bloccante
  String file;    //nome del file di output
  //costruttore
  public bwrite(BlockingQueue<byte[]> buffer, String file){
    this.buffer = buffer;
    this.file = file;
  }
  //metodo eseguito dal thread
  public void run(){
    try{
    FileOutputStream fout = new FileOutputStream(file);
    byte[] b;
    while(true){
      try{
         b = buffer.take();     //inserisco la testa del buffer in b
        fout.write(b);            //scrivo b sul file
      }catch(InterruptedException ie){break;}
    }
  }catch(IOException e){System.out.println("Errore");}
  }
  }
