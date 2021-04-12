import java.nio.MappedByteBuffer;

public class Sqrt implements Runnable{
    private Object critical;
    private MappedByteBuffer out;
    public volatile int counter;

    public Sqrt(Object critical, MappedByteBuffer out) {
        this.critical = critical;
        this.out = out;
    }

    @Override
    public void run() {
        while(true) {
            synchronized (critical) {
                //counter = SynchroCouter.count;
                while (out.position() != 24+counter) {
                    try {
                        System.out.println("(Sqrt) Current position:  " + out.position());
                        critical.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(out.position() == 24+counter) {
                    out.position(out.position() - 8+counter);
                    System.out.println("(Sqrt) Current position:  " + out.position());
                    double sumSQR = out.getDouble();

                    double sqrtSum = Math.sqrt(sumSQR);

                    out.putDouble(sqrtSum);
                    SynchroCouter.count = counter + 32;

                    System.out.println("(Sqrt) Current position:  " + out.position());

                    System.out.println("Answer: " + sqrtSum);
                    System.out.println();

                    //out.position(out.position() - 32+counter);
                    critical.notifyAll();
                }
            }
            System.out.println("End sqrt");
            System.out.println();
        }
    }
}
