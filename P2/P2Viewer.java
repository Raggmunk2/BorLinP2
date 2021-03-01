package P2;

import p1.Buffer;
import p1.Message;
import p1.Viewer;


public class P2Viewer extends Viewer implements Icallback{
    private Buffer<Message> message = new Buffer<>();
    private MessageClient messageClient;


    public P2Viewer(MessageClient messageClient,int width, int height){
        super(width, height);
        this.messageClient=messageClient;
        messageClient.addCallbacks(this);
    }
    @Override
    public void setMessageFromCallback(Message message) {
        setMessage(message);
    }

}
