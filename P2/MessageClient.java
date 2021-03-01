package P2;

import p1.Message;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Denna klass har en Arraylist med callback-interfacet. Har en inre klass som ärver Thread
 * @version 1.0
 * @author Linn Borgström, Systemutveckling - ak5587
 */
public class MessageClient {

    private ArrayList<Icallback> callbacks;

    /**
     * Konstruktor som skapar ArrayList med callbacks och
     * skapar ett nytt objekt av den inre klassen Connection
     * @param ip String, vilken ip som anges
     * @param port int, vilken port som anges
     */
    public MessageClient(String ip, int port) {
        callbacks = new ArrayList<>();
        new Connection(ip,port);
    }

    /**
     * Adderar callback till vår Array av callbacks
     * @param callback en instans av interfacet Icallback
     */
    public void addCallbacks(Icallback callback){
        callbacks.add(callback);
    }

    /**
     * Inre klass som ärver Thread. Reprecenterar en anslutning mellan server och klient
     */
    private class Connection extends Thread{
        private String ip;
        private int port;

        /**
         * Konstruktor som sätter ip och port och startar tråden
         * @param ip String, den ip som används
         * @param port int, den port som används
         */
        public Connection(String ip, int port){
            this.ip=ip;
            this.port=port;
            start();
        }

        /**
         * Run-metod med en try, catch, while-loop och for-sats.
         * Här skapar du en Socket m.hj.a. variablarna ip och port och en ObjectInputStream m.hj.a
         * Socketen.
         * Så länge tråden är aktiv så läser du in objektet genom strömmen och castar det till en Message.
         * Man itererar igenom ArrayListen av callbacks och skickar en callback till alla som lyssnar
         * på denna callback
         */
        public void run(){
            ObjectInputStream ois;
            try{
                Socket socket = new Socket(ip,port);
                ois = new ObjectInputStream(socket.getInputStream());
                Message message;
                while(true){
                    message = (Message) ois.readObject();
                    for(Icallback icallback : callbacks){
                        icallback.setMessageFromCallback(message);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }//run

    }//Listener
}//Klassen
