package P2;


import p1.MessageProducer;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessageProducerClient {

    private String ip;
    private int port;
    private ObjectOutputStream oos;

    public MessageProducerClient(String ip, int port) {
        this.ip=ip;
        this.port=port;

    }
    public void send(MessageProducer messageProducer) throws IOException {
        try(Socket socket = new Socket(ip,port)){
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(messageProducer);
            oos.flush();
        } catch(UnknownHostException e){

        } catch (IOException e) {}


    }
}//MPC
