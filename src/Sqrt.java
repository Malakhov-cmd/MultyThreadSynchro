import java.nio.MappedByteBuffer;

public class Sqrt implements Runnable{
    private Object critical;
    private MappedByteBuffer out;

    public Sqrt(Object critical, MappedByteBuffer out) {
        this.critical = critical;
        this.out = out;
    }

    @Override
    public void run() {
        synchronized (critical){
            while(out.position() != 24){
                try {
                    System.out.println("Current position:  " + out.position());
                    critical.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
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
