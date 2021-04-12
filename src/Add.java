import java.nio.MappedByteBuffer;

public class Add implements Runnable {
    private Object critical;
    private MappedByteBuffer out;
    private volatile int counter;

    public Add(Object critical, MappedByteBuffer out, int counter) {
        this.critical = critical;
        this.out = out;
        this.counter = counter;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (critical) {
                while (out.position() != 16) {
                    try {
                        System.out.println("(Add) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (out.position() == 16) {
                    try {
                        double sqrA;
                        double sqrB;

                        out.position(out.position() - 16);
                        System.out.println("(Add) Current position:  " + out.position());
                        sqrA = out.getDouble();

                        System.out.println("(Add) Current position:  " + out.position());
                        sqrB = out.getDouble();

                        double sumSQR = sqrA + sqrB;
                        System.out.println("(Add) Sum sqr a and b: " + sumSQR);

                        out.putDouble(sumSQR);
                        System.out.println("(Add) Current position:  " + out.position());
                        System.out.println();
                    } catch (IllegalArgumentException e) {
                        System.out.println("Negative index");
                    }
                    critical.notifyAll();
                }
            }
        }
    }
}
