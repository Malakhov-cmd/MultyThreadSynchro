import java.nio.BufferOverflowException;
import java.nio.MappedByteBuffer;

public class PowB implements Runnable {
    private Double b;
    private Object critical;
    private MappedByteBuffer out;
    private static int counterPowB;

    public PowB(Double b, Object critical, MappedByteBuffer out) {
        this.b = b;
        this.critical = critical;
        this.out = out;
    }

    public static void setCounterPowB(int count) {
        counterPowB = count;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (critical) {
                while (out.position() != 8 + counterPowB) {
                    try {
                        System.out.println("Counter: " + counterPowB);
                        System.out.println("(Pow) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    if (out.position() == 8 + counterPowB) {
                        System.out.println("pow " + counterPowB);

                        double result = Math.pow(b, 2);
                        System.out.println("(Pow) Value is: " + result);
                        out.putDouble(result);
                        System.out.println("(Pow) Current position:  " + out.position());

                        out.position(out.position() - 8);
                        System.out.println("(Pow) Powed value: " + out.getDouble() + " ");
                        System.out.println();
                        critical.notifyAll();
                    }
                } catch (BufferOverflowException e){
                    System.out.println("Overflow file");
                }
            }
            System.out.println("End pow");
            System.out.println();
        }
    }
}
