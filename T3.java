public class T3 extends Thread {
    private final Monitor monitor;
    private int a3;
    private int e3;
    private int p3;

    public T3(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread T3 started");

            // Введення R, MD
            Data.fillR();
            Data.fillMD();

            // Сигнал задачам T1, T2, T4 про введення R, MD
            monitor.signalInput();

            // Чекати на введення даних в задачах T1, T2 та T4
            monitor.waitInput();

            // Обчислення1: a3 = max(C * (MA * MDн))
            int[][] subMD = Data.getColumnsOfSubmatrix(Data.MD, Data.H * 2, Data.H * 3 - 1);
            int[][] productMA_MD = Data.multiplyMatrixByMatrix(Data.MA, subMD);
            int[] vectorProduct = Data.multiplyVectorByMatrix(Data.C, productMA_MD);
            a3 = Data.getMaxElementOfSubvector(vectorProduct);

            // Обчислення2: a = max(a, a3)
            monitor.maxA(a3);

            // Сигнал задачам T1, T2, T4 про завершення обчислення a
            monitor.signalCalculateA();

            // Чекати на завершення обчислення a в задачах T1, T2, T4
            monitor.waitCalculateA();

            // Копія а3 = а
            a3 = monitor.getA();

            // Копія e3 = d
            e3 = monitor.getE();

            // Копія p3 = p
            p3 = monitor.getP();

            // Обчислення3: Хн = p3 * a3 * Rн + e3 * Bн
            int[] temp = Data.getSubvector(Data.R, Data.H * 2, Data.H * 3 - 1);
            int[] firstSubvector = Data.multiplyVectorByScalar(temp, a3 * p3); // p3 * a3 * Rн

            temp = Data.getSubvector(Data.B, Data.H * 2, Data.H * 3 - 1);
            int[] secondSubvector = Data.multiplyVectorByScalar(temp, e3); // e3 * Bн

            int[] Xh = Data.sumTwoVectors(firstSubvector, secondSubvector);
            Data.mergeTwoVectors(Data.X, Xh, Data.H * 2);

            // Сигнал задачі Т1 про завершення обчислення Xн
            monitor.signalOutput();

            System.out.println("Thread T3 finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
