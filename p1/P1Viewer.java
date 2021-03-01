package p1;
/*
 * Jag valde att extenda Viewer för att det kom ganska naturligt och lätt, likaså implementera PropertyChangeListner.
 * Därefter lade jag till en listener som är kopplad till MessageManager.
 * I propertyChange så kollar jag evt namn är "New message" för att setta & "casta" ett nytt värder på evt.
 */

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class P1Viewer extends Viewer implements PropertyChangeListener {
    private Buffer<Message> message = new Buffer<>();
    private MessageManager messageBuffer;


    public P1Viewer(MessageManager messageBuffer,int width, int height){
        super(width, height);
        this.messageBuffer=messageBuffer;
        messageBuffer.addMessageManagerListener(this);
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if(evt.getPropertyName().equals("New message")){
            setMessage( (Message) evt.getNewValue());
        }
    }
}
