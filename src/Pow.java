import java.io.IOException;
import java.nio.MappedByteBuffer;

public class Pow implements Runnable {
    private Double a;
    private Object critical;
    private MappedByteBuffer out;
    public int counter;

    public Pow(Double a, Object critical, MappedByteBuffer out) {
        this.a = a;
        this.critical = critical;
        this.out = out;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (critical) {
                counter = SynchroCouter.count;
                while (out.position() != 0 + counter && out.position() != 8 + counter) {
                    try {
                        System.out.println("Counter: " + counter);
                        System.out.println("(Pow) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (out.position() == 0 + counter || out.position() == 8 + counter) {
                    System.out.println("pow " + counter);

                    Double result = Math.pow(a, 2);
                    System.out.println("(Pow) Value is: " + result);
                    out.putDouble(result.doubleValue());
                    System.out.println("(Pow) Current position:  " + out.position());

                    out.position(out.position() - 8 + counter);
                    System.out.println("(Pow) Powed value: " + out.getDouble() + " ");
                    System.out.println("Itigo  " + out.position());
                    System.out.println();
                    critical.notifyAll();
                }
            }
            System.out.println("End pow");
            System.out.println();
        }
    }
}
