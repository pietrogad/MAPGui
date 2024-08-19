package com.example.clientmapgui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    /**
     * @param args
     */
    private ObjectOutputStream out;
    private ObjectInputStream in ; // stream con richieste del client


    public Client (String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip); //ip
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port); //Port
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());	; // stream con richieste del client
    }

    public ObjectInputStream getIn() {
        return in;
    }
    public ObjectOutputStream getOut() {
        return out;
    }

    public void loadDataOnServer(String s) throws IOException, ClassNotFoundException {
        boolean flag=false;
        do {
            String tableName = s;
            out.writeObject(0);
            out.writeObject(tableName);
            String risposta=(String)(in.readObject());
            if(risposta.equals("OK"))
                flag=true;
            else System.out.println(risposta);

        }while(flag==false);
    }

    void loadDedrogramFromFileOnServer() throws IOException, ClassNotFoundException {
        System.out.println("Inserire il nome dell'archivio (comprensivo di estensione):");
        String fileName=Keyboard.readString();

        out.writeObject(1);
        out.writeObject(fileName);
        String risposta= (String) (in.readObject());
        if(risposta.equals("OK"))
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }

    void mineDedrogramOnServer(int profondita , int scelta , String nomefile) throws IOException, ClassNotFoundException {


        out.writeObject(2);
        int depth = profondita;
        out.writeObject(depth);
        int dType=-1;
        dType=scelta;
        out.writeObject(dType);

        String risposta= (String) (in.readObject());
        if(risposta.equals("OK")) {
            System.out.println(in.readObject()); // stampo il dendrogramma che il server mi sta inviando
            String fileName = nomefile;
            out.writeObject(fileName);
        }
        else
            System.out.println(risposta); // stampo il messaggio di errore
    }
}
