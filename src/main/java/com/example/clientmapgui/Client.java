package com.example.clientmapgui;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.clientmapgui.ControllerConnServer.client;

/**
 * Classe che gestisce la comunicazione tra client e server.
 */
public class Client {

    /**
     * Stream di output
     */
    private ObjectOutputStream out;
    /**
     * Stream di input.
     */
    private ObjectInputStream in ;

    /**
     * Costruttore della classe Client.
     * @param ip IP del server.
     * @param port Porta del server.
     * @throws IOException
     */
    public Client (String ip, int port) throws IOException {
        InetAddress addr = InetAddress.getByName(ip);
        System.out.println("addr = " + addr);
        Socket socket = new Socket(addr, port);
        System.out.println(socket);

        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());
    }

    /**
     * Metodo getter dello stream di input.
     * @return stream di input.
     */
    public ObjectInputStream getIn() {
        return in;
    }
    /**
     * Metodo getter dello stream di output.
     * @return stream di output.
     */
    public ObjectOutputStream getOut() {
        return out;
    }
    /**
     * Metodo che invia il nome della tabella selezionata al server.
     * @param s nome della tabella.
     * @return risposta del server.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String loadDataOnServer(String s) throws IOException, ClassNotFoundException {
        String tableName = s;
        out.writeObject(0);
        out.writeObject(tableName);
        String risposta=(String)(in.readObject());
        return risposta;
    }

    /**
     * Metodo che carica le informazioni del calcolo del dendogramma da file.
     * @param file file da cui ricavare le informazioni.
     * @return risposta del server.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String loadDedrogramFromFileOnServer(String file) throws IOException, ClassNotFoundException {
        String fileName = file;
        String message;
        out.writeObject(fileName);
        String risposta= (String) (in.readObject());
        if(risposta.equals("OK")){
            message = (String) in.readObject(); // stampo il dendrogramma che il server mi sta inviando
            return message;
        }
        else{
            return risposta;
        }
    }
    /**
     * Metodo che invia i dati necessari al calcolo del dendogramma al server.
     * @param profondita profondità del dendogramma.
     * @param scelta distanza da calcolare scelta.
     * @param nomefile nome del file.
     * @return risposta del server.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String mineDedrogramOnServer(int profondita , int scelta , String nomefile) throws IOException, ClassNotFoundException {
        String message = "";
        int depth = profondita;
        out.writeObject(depth);
        int dType=-1;
        dType=scelta;
        out.writeObject(dType);

        String risposta= (String) (in.readObject());
        if(risposta.equals("OK")) {
            message = (String) in.readObject();// stampo il dendrogramma che il server mi sta inviando
            String fileName = nomefile;
            out.writeObject(fileName);
            return message;
        }
        else
            return risposta;
    }

    /**
     * Metodo che recupera il nomi delle tabelle/nomi dei file dal server.
     * @return messaggio del server.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ArrayList<String> getData() throws IOException, ClassNotFoundException {
        Object object = in.readObject();
        ArrayList<String> lista = (ArrayList<String>) object;
        return lista;
    }
    /**
     * Metodo che crea una table sul DB e la popola sulla base di ciò che è stato inserito all'interno delle colonne.
     * @param q1 query di creazione della tabella.
     * @param q2 query di popolamento della tabella.
     * @param tbName nome della tabella.
     * @return messaggio del server.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String createTable(String q1, String q2, String tbName) throws IOException, ClassNotFoundException {
        String tableName = tbName;
        String query1 = q1;
        String query2 = q2;
        String msg;
        client.getOut().writeObject(tableName);
        client.getOut().writeObject(query1);
        client.getOut().writeObject(query2);
        msg = (String) client.getIn().readObject();
        return msg;
    }
}
