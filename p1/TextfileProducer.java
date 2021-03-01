package p1;


import javax.swing.*;
import java.io.*;
/**
 * En klass som implemeterar MessageProducer. Läser från en fil och returnerar olika värden.
 * @version 1.1
 * @author Linn Borgström - Systemutveckling - ak5587
 */
public class TextfileProducer implements MessageProducer{

    private int delay;
    private int times;
    private int size;
    private String text;
    private String image;
    private String filename;
    private Message[] messageArray;
    private int currMessage;

    /**
     * Konstruktor som läser från filen, initierar time, delay och size.
     * @param filename är filnamnet som vi hämtar filen ifrån när vi skapar en nytt objekt
     * av TexfileProducer i MainP1.
     * Sen gör vi en ny Message[] som är lika stor som size, loopar igenom raderna i filen och
     * läser in varannan rad som text och varannan rad som en bild-text. Därefter lägger vi in
     * dem i Message[] och skapar ett nytt Message-objekt av den med en text och "castar om" bild-texten till
     * en ImageIcon. I nextMessage så returnerar vi Message[] med nuvarande meddelande.
     */
    public TextfileProducer(String filename){
        this.filename=filename;
        try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))){
            times = Integer.parseInt(br.readLine());
            delay = Integer.parseInt(br.readLine());
            size =  Integer.parseInt(br.readLine());
            messageArray = new Message[size];

            for(int i =0;i < size;i++){
                text = br.readLine();
                image = br.readLine();
                messageArray[i] = new Message(text, new ImageIcon(image));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * En metod som visar hur lång tid som objekt ska visas
     * @return delay - fördröjning i millisekunder
     */
    @Override
    public int delay() {

        return delay;
    }

    /**
     * En metod som visar hur många gånger objektet ska visas
     * @return times - antal gånger
     */
    @Override
    public int times() {
        return times;
    }

    /**
     * En metod som visa hur många par av text + bild som finns i filen
     * @return size - storlek
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * En metod som returnerar nästa meddelande från arrayen
     * @return nextMessage
     */
    @Override
    public Message nextMessage() {
        //detta betyder att när det kommer till size(10) så nollar vi den så den hoppar tillbaka
        if(currMessage == size){
            currMessage = 0;
        }
        return messageArray[currMessage++];
    }

    /**
     *
     * En metod som skriver ut times, delay och size i konsollen
     */
    @Override
    public void info() {
        System.out.println("times="+times()+", delay="+delay()+", size="+size()+"]");

    }
}
