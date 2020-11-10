import java.io.*;
import java.util.concurrent.*;

class fbyte extends Thread{
    private final BlockingQueue<byte[]> buffer; //istanzio una coda circolare bloccante
    File file;  //file di input
    public fbyte(BlockingQueue<byte[]> buffer, String file_n){
      this.buffer = buffer;
      this.file = new File(file_n);
    }
    public void run(){
      try{
        long length = file.length();        //calcola la lunghezza in byte del file
        InputStream fin = new FileInputStream(file);      //apro lo stream di lettura
        int n_bytes = 1024;     //dimensione degli array di byte da inserire nel buffer
       do{
          try{
              if(length < 1024){
                byte[] b = new byte[(int)length];       //se length è inferiore a 1kB allora b avrà dimensione length, per evitare la lettura di byte superflui
                n_bytes = fin.read(b,0,(int)length);   //leggo length bytes dal file e li inserisco in b. Il metodo ritorna il numero di bytes letti.
                buffer.put(b);      //inserisco b nel buffer
                break;    //se la condizione è vera o il file è più piccolo di 1kB oppure stiamo leggendo la parte terminale del file.
              }
              else{
                length -= n_bytes;              //calcolo i bytes rimasti
                byte[] b = new byte[n_bytes];
                n_bytes = fin.read(b,0,n_bytes);      //leggo n_bytes bytes dal file e li inserisco in b. Il metodo ritorna il numero di bytes letti.
                buffer.put(b);      //inserisco b nel buffer
              }
          }catch(InterruptedException ie){System.out.println("Errore: "+ ie); }
      }while(true);
      fin.close();
    }catch(IOException e){System.out.println("Errore apertura file: "+e);}
}
}
