import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Task {
        int arrival;
        int cylinderNumber;
        int deadline;

        public Task(int arrival,  int cylinderNumber)
        {
            this.arrival = arrival;
            this.cylinderNumber = cylinderNumber;
        }

        public Task(int arrival,  int cylinderNumber, int deadline)
        {
            this.arrival = arrival;
            this.cylinderNumber = cylinderNumber;
            this.deadline = deadline;

        }
        public Task(Task t)
        {
            this.arrival = t.arrival;
            this.deadline = t.deadline;
            this.cylinderNumber = t.cylinderNumber;
        }
        public int getArrival()
        {
            return arrival;

        }

        public int getCylinderNumber() {
            return cylinderNumber;
        }

        public int getDeadline() {
            return deadline;
        }

        public void setArrival(int arrival) {
            this.arrival = arrival;
        }

        public void setCylinderNumber(int cylinderNumber) {
            this.cylinderNumber = cylinderNumber;
        }

        public void setDeadline(int deadline) {
            this.deadline = deadline;
        }

        public static void compareWithCurrentBlock(final int currentBlock, ArrayList a)
        {
            Comparator<Task> closestTaskComparator= new Comparator<Task>()
            {

                public int compare(Task o1, Task o2)
                {

                    return Math.abs(o1.cylinderNumber - currentBlock) - Math.abs(o2.cylinderNumber - currentBlock);
                }
            };
            Collections.sort(a,closestTaskComparator );
        }

        public static Comparator<Task> deadlineComparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.deadline - o2.deadline;
            }
        };
        public static Comparator<Task> arrivalComparator = new Comparator<Task>() {
            @Override
            public int compare(Task o1, Task o2) {
                return o1.arrival - o2.arrival;
            }
        };

        public String toString()
        {
            return arrival + " " + cylinderNumber + " " + deadline;
        }
}
