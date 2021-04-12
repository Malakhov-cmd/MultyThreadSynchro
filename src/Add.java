import java.nio.MappedByteBuffer;

public class Add implements Runnable {
    private Object critical;
    private MappedByteBuffer out;

    public Add(Object critical, MappedByteBuffer out) {
        this.critical = critical;
        this.out = out;
    }

    @Override
    public void run() {
        synchronized (critical){
            while(out.position() != 16)
            {
                try {
                    System.out.println("Current position:  " + out.position());
                    critical.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                double sqrA;
                double sqrB;

                out.position(out.position() - 16);
                System.out.println("(Add) Current position:  " + out.position());
                sqrA = out.getDouble();

                System.out.println("(Add) Current position:  " + out.position());
                sqrB = out.getDouble();

                double sumSQR = sqrA + sqrB;
                System.out.println("Sum sqr a and b: " + sumSQR);

                out.putDouble(sumSQR);
                System.out.println("(Add) Current position:  " + out.position());
                System.out.println();
            } catch (IllegalArgumentException e) {
                System.out.println("Negative index");
            }
        }
    }
}
