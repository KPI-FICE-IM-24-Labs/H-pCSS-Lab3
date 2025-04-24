/**
* Лабораторна робота №3
* Варіант 8
* X = p * max(C * (MA * MD)) * R + e * B
* Якимчук Кіріл Сергійович
* Група ІМ-24
* 24.04.2025
*/
public class Program {
    public static void main(String[] args) {
        try {
            long startTime = System.currentTimeMillis();

            Monitor monitor = new Monitor();

            Thread T1 = new T1(monitor);
            Thread T2 = new T2(monitor);
            Thread T3 = new T3(monitor);
            Thread T4 = new T4(monitor);

            T1.start();
            T2.start();
            T3.start();
            T4.start();

            T1.join();
            T2.join();
            T3.join();
            T4.join();

            long endTime = System.currentTimeMillis();
            System.out.println("Execution time: " + (endTime - startTime) + " ms");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
