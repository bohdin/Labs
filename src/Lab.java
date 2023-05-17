import DataInfo.DataReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Lab {

    public static double[][]  MT, MZ;
    public static double[][]  B, D;

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        DataReader dataReader = new DataReader();
        int n = 100;

        MT = dataReader.readMatrix("Data/MT.txt", n, n);
        MZ = dataReader.readMatrix("Data/MZ.txt", n, n);

        B = dataReader.readVector("Data/B.txt", 1, n);
        D = dataReader.readVector("Data/D.txt", 1, n);

        CountDownLatch latch = new CountDownLatch(2);
        CyclicBarrier barrier = new CyclicBarrier(2);

        Thread thread1 = new Thread(MatrixFunctions.firstThread(latch, barrier));
        Thread thread2 = new Thread(MatrixFunctions.secondThread(latch, barrier));

        int test;
        thread1.start();
        thread2.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Program finished");
        String text = "Total time: " + (endTime - startTime) + " ms";
        System.out.println(text);
        MatrixFunctions.writeTimeToFile("Lab2_Time", text);
    }
}

