import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Main {
    private Object critical = new Object();
    private static int fileSize = 10240;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter first parameter");
        Double a = in.nextDouble();

        System.out.println("Enter second parameter");
        Double b = in.nextDouble();

        try {
            RandomAccessFile memoryMappedFile = new RandomAccessFile("largeFile.txt", "rw");

            MappedByteBuffer out = memoryMappedFile.getChannel().map(FileChannel.MapMode.READ_WRITE, 0, fileSize);

            out.put(a.byteValue());
            out.put(b.byteValue());


            System.out.println("Writing to Memory Mapped File is completed");


            //reading from memory file in Java
            for (int i = 0; i < 10; i++) {
                System.out.print((double) out.get(i) + " ");
            }

            System.out.println("Reading from Memory Mapped File is completed");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
