import java.nio.MappedByteBuffer;

public class Add implements Runnable {
    private Object critical;
    private MappedByteBuffer out;
    private static int counterAdd;

    public Add(Object critical, MappedByteBuffer out) {
        this.critical = critical;
        this.out = out;
    }

    public static void setCounter(int counter)
    {
        counter = counter;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (critical) {
                //counter = SynchroCouter.count;
                while (out.position() != 16+counter) {
                    try {
                        System.out.println("(Add) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (out.position() == 16+counter) {

                        double sqrA;
                        double sqrB;

                        out.position(out.position() - 16+counter);
                        System.out.println("(Add) Current position:  " + out.position());
                        sqrA = out.getDouble();

                        System.out.println("(Add) Current position:  " + out.position());
                        sqrB = out.getDouble();

                        double sumSQR = sqrA + sqrB;
                        System.out.println("(Add) Sum sqr a and b: " + sumSQR);

                        out.putDouble(sumSQR);
                        System.out.println("(Add) Current position:  " + out.position());
                        System.out.println();

                    critical.notifyAll();
                }
            }
            System.out.println("End add");
            System.out.println();
        }
    }
}
