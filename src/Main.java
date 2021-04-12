import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Main {
    public static Object critical = new Object();
    private static int fileSize = 10240;
    static MappedByteBuffer out;

    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.action();
    }
}
