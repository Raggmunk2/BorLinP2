package P2;

import p1.Buffer;
import p1.Message;
import p1.MessageManager;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * En server-klass som implementerar Runnable.
 * Har en inre klass som extendar Thread och implementerar PropertyChangeListener
 * @version 1.0
 * @author Linn Borgström, Systemutveckling, ak5587
 */
public class MessageServer implements Runnable{
    private Thread server = new Thread(this);
    private MessageManager messageManager;
    private int port;

    /**
     * Konstruktor som sätter messangeManager, port och startar server-tråden
     * @param messageManager en instans av MessageManager
     * @param port en int-variabel
     * @throws IOException
     */
    public MessageServer(MessageManager messageManager, int port) throws IOException {
        this.messageManager=messageManager;
        this.port=port;
        server.start();

    }

    /**
     * Run-metod med en try,catch och en while-loop
     * Här skapas en ny ServerSocket med variabeln port
     * Om tråden är igång så startar man en Socket m.h.a. Serversocketen
     * Därefter skapar man ett nytt objekt av den inre klassen ClientHandler
     * och startar den m.h.a. socketen
     */
    public void run(){
        try{
            ServerSocket serverSocket = new ServerSocket(port);
            while(true){
                Socket socket = serverSocket.accept(); //Här accepteras anslutningen från klienten
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * En inre klass som ärver Thread och implementerar PropertyChangeListener
     *  Här implementera vi PropertyChangeListener(istället för i den yttre klassen)
     *  för att annars ändras datan i messageBuffern när nästa client kommer åt den.
     *  På detta sätt så synkar vi alla fönstrerna.
     *  Den har en Socket, ObjectOutputStream och en Message-buffer
     */
    private class ClientHandler extends Thread implements PropertyChangeListener{
        private Socket socket;
        private ObjectOutputStream oos;
        //Buffern måste ligga här ist för där uppe för att alla clients delar på den
        // och därav ändrar datan för nästa
        private Buffer<Message> messageBuffer;

        /**
         * Konstruktor som instansierar socket. Har en try och catch.
         * Här skapar man en objekt-ström m.h.a. socketen och en ny messageBuffer.
         * Man startar också listern
         * @param socket en socket som hjälper oss hämta strömmen
         */
        public ClientHandler(Socket socket){
            this.socket= socket;
            try {
                oos = new ObjectOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            messageBuffer = new Buffer<Message>();
            messageManager.addMessageManagerListener(this);
        }

        /**
         * Run-metod med en try, catch och en while-loop
         * Här gettar man från messagebufferten, lägger det i en variabel av typen Message,
         * skickar vidare det i en objekt-ström och flushar.
         */
        public void run(){
            try{
                while(true){
                    Message message = messageBuffer.get();
                    oos.writeObject(message);
                    oos.flush();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Här kollar vi om namnet på meddelandet är "New message",
         * i så fall castar vi om den från ett objekt till ett Message och puttar den till bufferten
         * @param evt av typen PropertyChangeEvent
         */
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if(evt.getPropertyName().equals("New message")){
                Message message = (Message) evt.getNewValue();
                messageBuffer.put(message);
            }
        }

    }//clientHandler
}//Klassen
