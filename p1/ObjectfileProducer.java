package p1;
/*
Denna klass används bara när man kör testmetoden i TestMessageProducer.
Den läser in objekt istället för textrader. Anledningen att vi kan "readInt"
är för att i TestManagerProducer har skrivit att de första 3 raderna är times,delay och size int.
Annars gör vi samma sak här som i TextfileProducer fast att vi läser in objekt.
 */
import java.io.*;

public class ObjectfileProducer implements MessageProducer{
    private String filename;
    private int times;
    private int delay;
    private int size;
    private Message message;
    private Message[] messageArray;
    private int currMessage;


    public ObjectfileProducer(String filename){
        this.filename=filename;
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
            times = ois.readInt();
            delay = ois.readInt();
            size = ois.readInt();

            messageArray = new Message[size];

            for(int i =0;i < size;i++){
                message = (Message) ois.readObject();
                messageArray[i] = message;
            }
        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }catch (ClassNotFoundException e){ }
    }


    @Override
    public int delay() {

        return delay;
    }

    @Override
    public int times() {

        return times;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public Message nextMessage() {
        //detta betyder att när currMessage kommer till size 10 så nollar vi den så körs den igen
        if(currMessage == size){
            currMessage = 0;
        }
        return messageArray[currMessage++];
    }

    @Override
    public void info() {
        System.out.println("times="+times()+", delay="+delay()+", size="+size()+"]");


    }
}
