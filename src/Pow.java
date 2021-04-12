import java.io.IOException;
import java.nio.MappedByteBuffer;

public class Pow implements Runnable {
    private Double a;
    private Object critical;
    private MappedByteBuffer out;
    private volatile int counter;

    public Pow(Double a, Object critical, MappedByteBuffer out, int counter) {
        this.a = a;
        this.critical = critical;
        this.out = out;
        this.counter = counter;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (critical) {
                while (out.position() != 0*counter && out.position() != 8*counter) {
                    try {
                        System.out.println("(Pow) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(out.position() == 0 || out.position() == 8) {
                    Double result = Math.pow(a, 2);
                    System.out.println("(Pow) Value is: " + result);
                    out.putDouble(result.doubleValue());
                    System.out.println("(Pow) Current position:  " + out.position());

                    try {
                        out.position(out.position() - 8);
                        System.out.println("(Pow) Powed value: " + out.getDouble() + " ");
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Negative index");
                    } finally {
                        critical.notifyAll();
                    }
                }
            }
        }
    }
}
