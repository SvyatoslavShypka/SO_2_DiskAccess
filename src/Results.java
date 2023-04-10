
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Results {
    int cycles;
    int tasks;
    int priorityProcent;

    public Results(int cycles, int tasks, int priorityProcent){
        this.priorityProcent = priorityProcent;
        this.cycles = cycles;
        this.tasks = tasks;
    }
    public ArrayList<Result> averageResults() {

        ArrayList<Result> lista = new ArrayList<Result>();
        ArrayList<Task> randomTasks = new ArrayList<Task>();
        ArrayList<Task> randomPriorityTasks = new ArrayList<Task>();
        int sum_FCFS_EDF = 0;
        int sum_FCFS_FD = 0;
        int sum_SSTF_EDF = 0;
        int sum_SSTF_FD = 0;
        int sum_SCAN_EDF = 0;
        int sum_SCAN_FD = 0;
        int sum_C_SCAN_EDF = 0;
        int sum_C_SCAN_FD = 0;
        for (int j = 0; j < cycles; j++) {

            for (int i = 0; i <tasks*(100 - priorityProcent)/100; i++) {
                Random r = new Random();
                int d = 1 + r.nextInt(1000);
                int m = 1 + r.nextInt(199);
                randomTasks.add(new Task(d, m));
            }

            for (int i = 0; i < tasks*priorityProcent/100; i++) {
                Random r = new Random();
                int d = 1+ r.nextInt(1000);
                int m = 1 + r.nextInt(199);
                int k = 300 + r.nextInt(100);
                randomPriorityTasks.add(new Task(d, m, k));
            }
            Algorithms pr = new Algorithms(randomTasks, randomPriorityTasks);
            sum_FCFS_EDF  += pr.FCFS_EDF();
            sum_FCFS_FD  += pr.FCFS_FD_SCAN();
            sum_SSTF_EDF  += pr.SSTF_EDF();
            sum_SSTF_FD  += pr.SSTF_FD_SCAN();
            sum_SCAN_EDF  += pr.SCAN_EDF();
            sum_SCAN_FD  += pr.SCAN_FD_SCAN();
            sum_C_SCAN_EDF  += pr.C_SCAN_EDF();
            sum_C_SCAN_FD  += pr.C_SCAN_FD_SCAN();
            randomTasks.clear();
            randomPriorityTasks.clear();
        }

        lista.add(new Result("FCFS_EDF", averageByCycle(sum_FCFS_EDF)));
        lista.add(new Result("FCFS_FD_SCAN", averageByCycle(sum_FCFS_FD)));
        lista.add(new Result("SSTF_EDF", averageByCycle(sum_SSTF_EDF)));
        lista.add(new Result("SSTF_FD_SCAN", averageByCycle(sum_SSTF_FD)));
        lista.add(new Result("SCAN_EDF", averageByCycle(sum_SCAN_EDF)));
        lista.add(new Result("SCAN_FD_SCAN", averageByCycle(sum_SCAN_FD)));
        lista.add(new Result("C_SCAN_EDF", averageByCycle(sum_C_SCAN_EDF)));
        lista.add(new Result("C_SCAN_FD_SCAN", averageByCycle(sum_C_SCAN_FD)));
        System.out.println("\nWszystkie wyniki posortowane roznąco:");

        Collections.sort(lista);
        return lista;
    }

    private double averageByCycle(int x) {
        return Math.round((double) x / cycles);
    }
}
