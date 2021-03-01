package p1;

import java.io.Serializable;

public interface MessageProducer extends Serializable {
	public int delay();
	public int times();
	public int size();
	public Message nextMessage();
	
	default void info() {
		System.out.println("times="+times()+", delay="+delay()+", size="+size()+"]");
	}
}
