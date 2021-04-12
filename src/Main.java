import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Tester tester = new Tester();
        tester.action();
    }
}
