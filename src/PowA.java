import java.io.IOException;
import java.nio.MappedByteBuffer;

public class PowA implements Runnable {
    private Double a;
    private Object critical;
    private MappedByteBuffer out;
    public static int counterPow;

    public PowA(Double a, Object critical, MappedByteBuffer out) {
        this.a = a;
        this.critical = critical;
        this.out = out;
    }

    public static void setCounterPowA(int count){
        counterPow = count;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (critical) {
                while (out.position() != 0 + counterPow) {
                    try {
                        System.out.println("Counter: " + counterPow);
                        System.out.println("(Pow) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (out.position() == 0 + counterPow ) {
                    System.out.println("pow " + counterPow);

                    Double result = Math.pow(a, 2);
                    System.out.println("(Pow) Value is: " + result);
                    out.putDouble(result.doubleValue());
                    System.out.println("(Pow) Current position:  " + out.position());

                    out.position(out.position() - 8);
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
