package chap08;

public class Object {
	
	static {
		registerNatives();
	}
	
	public final native Class<?> getClass();
	
	public native int hashCode();
	
	public boolean equals(Object obj) {
		return (this == obj);
	}
	
	protected native Object clone() throws CloneNotSupportedException;
	
	public String toString() {
		return getClass().getName() + "0" +Integer.toHexString(hashCode());
	}
	
	public final native void notify();
	
	public final native void notifyAll();
	
	public final native void wait(long timeout) throws InterruptedException;
	
	public final void wait(long timeout, int nanos) throws InterruptedException {
		if (timeout < 0) {
			throw new IllegalArgumentException("timeout value is negative");
			
		}
	}
	
	public static void main(String[] args) {
		
	}

}
