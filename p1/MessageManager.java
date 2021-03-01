package p1;
/*
 * MessageManager extendar Thread. Den har en PropertyChangeSupport som lägger till
 * skickar vidare en listner.
 * I run-metoden så kollar vi så tråden inte är interrupted och hämtar meddelandern
 * och meddelandet inte är null så sätter vi ett nytt namn på listnern som vi kollar
 * i P1Viewer
 */

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class MessageManager extends Thread{
    private Buffer<Message> messageBuffer;
    private PropertyChangeSupport changes;

    public void addMessageManagerListener(PropertyChangeListener listener){
        changes.addPropertyChangeListener(listener);
    }

    public MessageManager(Buffer<Message> messageBuffer){
        this.messageBuffer=messageBuffer;
        changes = new PropertyChangeSupport(this);
    }

    public void run(){
        while(!Thread.interrupted()) {
            try {
                Message message = messageBuffer.get();
                if (message != null) {
                    changes.firePropertyChange("New message", null, message);
                }
            } catch (InterruptedException e) { }
        }
    }

}
