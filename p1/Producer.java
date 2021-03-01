package p1;

/**
 * En klass som ärver Thread och hämtar och lägger in meddelanden till buffrar.
 * @version 1.1
 * @author Linn Borgström Systemutveckling - ak5587
 * */
public class Producer extends Thread{
    private Buffer<MessageProducer> producerBuffer;
    private Buffer<Message> messageBuffer;
    private Thread thread = new Thread();
    private int delay;
    private int times;
    private int size;
    private Message message;
    private MessageProducer mp;

    /**
     * Konstruktor som initierar Buffrarna av typen Message och MessageProducer
     * @param producerBuffer är en Buffer av MessageProducer
     * @param messageBuffer är en Buffer av Message
     */

    public Producer(Buffer<MessageProducer> producerBuffer, Buffer<Message> messageBuffer) {
        this.messageBuffer=messageBuffer;
        this.producerBuffer=producerBuffer;
    }

    /**
     * Run-metoden som kör tråden och gettar från producerBuffer.
     *
     *
     *  Man kollar i run-metoden om den inte är interrupted().
     *  Om den inte är det så gettar man från Buffern producerBuffer och instatsierar delay, times och size.
     *  Eftersom denna pratar med interfacet MessageProducer så kan vi få times,
     *  delay och size direkt därifrån istället för att läsa från filen.
     *  Jag valde att göra en ny variabel som är itarationen som är times*size som jag använder för
     *  att loopa och lägga till alla meddelanden till MessageBuffern
     *
     */
    @Override
    public void run(){
        try {
            while(!Thread.interrupted()) {
                mp = producerBuffer.get();
                delay = mp.delay();
                times = mp.times();
                size = mp.size();

                int iterations = times * size;

                for (int i = 0; i < iterations; i++) {
                    message = mp.nextMessage();
                    //skriv ut meddelandet och sen sov. Då får vi show gubbe i 3 sec.
                    messageBuffer.put(message);
                    Thread.sleep(delay);

                }
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
