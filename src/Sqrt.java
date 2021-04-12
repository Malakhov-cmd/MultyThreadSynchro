import java.nio.MappedByteBuffer;

public class Sqrt implements Runnable{
    private Object critical;
    private MappedByteBuffer out;
    private volatile int counter;

    public Sqrt(Object critical, MappedByteBuffer out, int counter) {
        this.critical = critical;
        this.out = out;
        this.counter = counter;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (critical) {
                while (out.position() != 24) {
                    try {
                        System.out.println("(Sqrt) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(out.position() == 24) {
                    out.position(out.position() - 8);
                    System.out.println("(Sqrt) Current position:  " + out.position());
                    double sumSQR = out.getDouble();

                    double sqrtSum = Math.sqrt(sumSQR);

                    out.putDouble(sqrtSum);
                    System.out.println("(Sqrt) Current position:  " + out.position());
                    System.out.println("Answer: " + sqrtSum);
                    System.out.println();

                    out.position(out.position() - 32);

                    critical.notifyAll();
                }
            }
        }
    }
}
