public class T4 extends Thread {
    private final Monitor monitor;
    private int a4;
    private int e4;
    private int p4;

    public T4(Monitor monitor) {
        this.monitor = monitor;
    }

    @Override
    public void run() {
        try {
            System.out.println("Thread T4 started");

            // Введення B, p
            Data.fillB();
            monitor.setP(1);

            // Сигнал задачам T1, T2, T3 про введення B, p
            monitor.signalInput();

            // Чекати на введення даних в задачах T1, T2 та T3
            monitor.waitInput();

            // Обчислення1: a4 = max(C * (MA * MDн))
            int[][] subMD = Data.getColumnsOfSubmatrix(Data.MD, Data.H * 3, Data.H * 4 - 1);
            int[][] productMA_MD = Data.multiplyMatrixByMatrix(Data.MA, subMD);
            int[] vectorProduct = Data.multiplyVectorByMatrix(Data.C, productMA_MD);
            a4 = Data.getMaxElementOfSubvector(vectorProduct);

            // Обчислення2: a = max(a, a4)
            monitor.maxA(a4);

            // Сигнал задачам T1, T2, T3 про завершення обчислення a
            monitor.signalCalculateA();

            // Чекати на завершення обчислення a в задачах T1, T2, T3
            monitor.waitCalculateA();

            // Копія а4 = а
            a4 = monitor.getA();

            // Копія e4 = d
            e4 = monitor.getE();

            // Копія p4 = p
            p4 = monitor.getP();

            // Обчислення3: Хн = p4 * a4 * Rн + e4 * Bн
            int[] temp = Data.getSubvector(Data.R, Data.H * 3, Data.H * 4 - 1);
            int[] firstSubvector = Data.multiplyVectorByScalar(temp, a4 * p4); // p4 * a4 * Rн

            temp = Data.getSubvector(Data.B, Data.H * 3, Data.H * 4 - 1);
            int[] secondSubvector = Data.multiplyVectorByScalar(temp, e4); // e4 * Bн

            int[] Xh = Data.sumTwoVectors(firstSubvector, secondSubvector);
            Data.mergeTwoVectors(Data.X, Xh, Data.H * 3);

            // Сигнал задачі Т1 про завершення обчислення Xн
            monitor.signalOutput();

            System.out.println("Thread T4 finished");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
