import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Tester {
    public Object critical = new Object();
    private int fileSize = 10240;
    private MappedByteBuffer out;

    public void action(){
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first parameter");
        Double a = in.nextDouble();

        System.out.println("Enter second parameter");
        Double b = in.nextDouble();

        File file = new File("largeFile.txt");

        if(file.exists())
        {
            file.delete();
            System.out.println("delete");
        }

        try {
            RandomAccessFile memoryMappedFile = new RandomAccessFile("largeFile.txt", "rw");

            out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

            Thread firstPow = new Thread(new Pow(a, critical, out));
            Thread secondPow = new Thread(new Pow(b, critical, out));

            firstPow.start();
            secondPow.start();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
