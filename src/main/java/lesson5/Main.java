package lesson5;

public class Main {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;

    public static void main(String[] args) {
        feelArr1();
        feelArr2();
    }

    public static void feelArr1() {
        float[] arr = new float[SIZE];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(" 1 thread : " + (System.currentTimeMillis() - a) + " ms.");
    }

    public static void feelArr2() {
        float[] arr = new float[SIZE];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1.0f;
        }
        long a = System.currentTimeMillis();

        float[] leftSide = new float[HALF];
        float[] rightSide = new float[HALF];
        System.arraycopy(arr, 0, leftSide, 0, HALF);
        System.arraycopy(arr, HALF, rightSide, 0, HALF);
        Thread t1 = new Thread(() -> calc(leftSide));
        Thread t2 = new Thread(() -> calc2(rightSide));
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(leftSide, 0, arr, 0, HALF);
        System.arraycopy(rightSide, 0, arr, HALF, HALF);
        System.out.println(" 2 threads : " + (System.currentTimeMillis() - a) + " ms.");
    }

    private static void calc(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }

    private static void calc2(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
        }
    }
}



