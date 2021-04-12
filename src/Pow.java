import java.io.IOException;
import java.nio.MappedByteBuffer;

public class Pow implements Runnable {
    private Double a;
    private Object critical;
    private MappedByteBuffer out;

    public Pow(Double a, Object critical, MappedByteBuffer out) {
        this.a = a;
        this.critical = critical;
        this.out = out;
    }

    @Override
    public void run() {
        int count = 0;
        synchronized (critical) {
            while (out.position() != 0 && out.position() != 8) {
                try {
                    System.out.println("Current position:  " + out.position());
                    critical.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Double result = Math.pow(a, 2);
            System.out.println("Value is: " + result);
            out.putDouble(result.doubleValue());

            System.out.println("Count " + count);
            System.out.println("Current position:  " + out.position());

            try {
                out.position(out.position() - 8);
                System.out.println("Powed value: " + out.getDouble() + " ");
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("Negative index");
            }
            critical.notifyAll();
            count++;
        }
    }
}
