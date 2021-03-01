package P2;

import p1.MessageProducer;
import p1.MessageProducerInput;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
/*
Detta är en iterativ server då den kopplar ner anslutningen efter man läst objektet
 */
public class MessageProducerServer {
    private MessageProducerInput mpInput;
    private int port;


    public MessageProducerServer(MessageProducerInput mpInput, int port) {
        this.mpInput=mpInput;
        this.port=port;

    }

    public void startServer() {
        new Connection(port).start();
    }
    private class Connection extends Thread{
        private int port;

        public Connection(int port){
            this.port=port;
        }
        public void run(){
            Socket socket = null;
            try(ServerSocket serverSocket = new ServerSocket(port)){
                while(true){
                    try{
                        socket = serverSocket.accept();
                        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                        MessageProducer mp = (MessageProducer) ois.readObject();
                        mpInput.addMessageProducer(mp);
                    } catch (IOException e) {
                        e.printStackTrace();
                        if(socket!=null){
                            try {
                                socket.close();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }//while
            }catch (IOException e){  }
        }//run
    }//conn

}//klass
