import java.nio.BufferOverflowException;
import java.nio.MappedByteBuffer;

public class PowA implements Runnable {
    private Double a;
    private Object critical;
    private MappedByteBuffer out;
    private static int counterPowA;

    public PowA(Double a, Object critical, MappedByteBuffer out) {
        this.a = a;
        this.critical = critical;
        this.out = out;
    }

    public static void setCounterPowA(int count) {
        counterPowA = count;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (critical) {
                while (out.position() != 0 + counterPowA) {
                    try {
                        System.out.println("Counter: " + counterPowA);
                        System.out.println("(Pow) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (out.position() == 0 + counterPowA) {
                        System.out.println("pow " + counterPowA);

                        double result = Math.pow(a, 2);
                        System.out.println("(Pow) Value is: " + result);
                        out.putDouble(result);
                        System.out.println("(Pow) Current position:  " + out.position());

                        out.position(out.position() - 8);
                        System.out.println("(Pow) Powed value: " + out.getDouble() + " ");
                        System.out.println();
                        critical.notifyAll();
                    }
                } catch (BufferOverflowException e) {
                    System.out.println("Overflow file");
                }
            }
            System.out.println("End pow");
            System.out.println();
        }
    }
}
