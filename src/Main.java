
import java.util.ArrayList;

public class Main {

    public static void main(String[] args)
    {
        ArrayList <Task> queue = new ArrayList<>();
        ArrayList <Task> priorityQueue = new ArrayList<>();
// Random parameters
        Results r = new Results(3, 100, 20);
        System.out.println("*********************************************************************************************");
        System.out.println(r.averageResults());
        System.out.println("*********************************************************************************************");
//Defined parameters
/*
        priorityQueue.add(new Task(0, 35, 200));
        priorityQueue.add(new Task(0, 80, 180));
        priorityQueue.add(new Task(0, 45, 300));
        priorityQueue.add(new Task(0, 55, 20));
*/
/*
        queue.add(new Task(0, 98));
        queue.add(new Task(0, 183));
        queue.add(new Task(0, 37));
        queue.add(new Task(0, 122));
        queue.add(new Task(0, 14));
        queue.add(new Task(0, 124));
        queue.add(new Task(0, 65));
        queue.add(new Task(0, 67));
        Algorithms a = new Algorithms(queue, priorityQueue);

        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm FCFS_EDF. Suma przewiniętych cylindrów: " + a.FCFS_EDF());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm FCFS_FD_SCAN. Suma przewiniętych cylindrów: " + a.FCFS_FD_SCAN());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm SSTF_EDF. Suma przewiniętych cylindrów: " + a.SSTF_EDF());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm SSTF_FD_SCAN. Suma przewiniętych cylindrów: " + a.SSTF_FD_SCAN());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm SCAN_EDF. Suma przewiniętych cylindrów: " + a.SCAN_EDF());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm SCAN_FD_SCAN. Suma przewiniętych cylindrów: " + a.SCAN_FD_SCAN());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm C_SCAN_EDF. Suma przewiniętych cylindrów: " + a.C_SCAN_EDF());
        System.out.println("*********************************************************************************************");
        System.out.println("Algorytm C_SCAN_FD_SCAN. Suma przewiniętych cylindrów: " + a.C_SCAN_FD_SCAN());
        System.out.println("*********************************************************************************************");
*/
    }
}
