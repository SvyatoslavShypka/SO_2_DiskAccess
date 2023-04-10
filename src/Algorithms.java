
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Algorithms {

    final int DISK_SIZE = 200;
    public static int startingPointBlock;

    static {
// Random starting cylinder
        Random r = new Random();
        startingPointBlock = 1 + r.nextInt(199);
//    startingPointBlock = 53;
    }
    ArrayList<Task> priorityQueue = new ArrayList<Task>();
    ArrayList<Task> queue = new ArrayList<Task>();

    public Algorithms(ArrayList<Task> queue, ArrayList <Task> priorityQueue) {
        this.priorityQueue = priorityQueue;
        this.queue = queue;
    }

    public int FCFS_EDF() {
        int blocks = 0;
        int currentBlock = startingPointBlock; //start from the block
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("prioritytasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++)

            {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);
                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)
            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);

                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priority done" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {
                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }
                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * if pointer is moving forwards
                     */
                    if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    /**
                     * if pointer is moving backwards
                     */
                    else {
                        currentBlock--;
                    }
                    blocks++;
                    /**
                     * deadline is descending with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                    }
                }
            }
            /**
             * if there is no tasks in priority queue but are tasks without deadline
             */
            else if (waitingTasks.size() != 0) {
                System.out.println("Normalne: " + waitingTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (currentBlock == waitingTasks.get(0).cylinderNumber) {
                    System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }
                /**
                 * processing other tasks from normal queue, sorted by arrival time
                 */
                if (waitingTasks.size() != 0) {
                    Collections.sort(waitingTasks, Task.arrivalComparator);
                    if (waitingTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000);
        return blocks;
    }
    public int FCFS_FD_SCAN() {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("prorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++)

            {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);

                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)

            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);

                }
            }
            if (waitingPriorityTasks.size() != 0) {
                /**
                 * deadline is descending with time
                 */
                for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                    waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                    /**
                     * doing task along the way of pointer
                     */
                    if (currentBlock == waitingPriorityTasks.get(i).cylinderNumber) {
                        System.out.println("--------------------Removed on the way" + waitingPriorityTasks.get(i));
                        waitingPriorityTasks.remove(i);
                    }
                }
                /**
                 * if pointer is moving forwards
                 */
                if (waitingPriorityTasks.size() > 0) {
                    if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    /**
                     * if pointer is moving backwards
                     */
                    else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }

            /**
             * if there is no tasks in priority queue but are tasks without deadline
             */

            else if (waitingTasks.size() != 0) {
                System.out.println("Normal tasks: " + waitingTasks);
                /**
                 * removing task that has been done
                 */
                if (currentBlock == waitingTasks.get(0).cylinderNumber) {
                    System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }
                /**
                 *  other tasks sorted by arrival time
                 */
                if (waitingTasks.size() != 0) {
                    Collections.sort(waitingTasks, Task.arrivalComparator);
                    if (waitingTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    blocks++;
                }

            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }


        }
        while (currentTime != 100000);
        return blocks;
    }
    public int SSTF_EDF() {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("priorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {
                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);
                }
            }
            for (int i = 0; i < priorityTasks.size(); i++) {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {
                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);
                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priorytet wykonany" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {

                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }


                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * when pointer is moving forwards
                     */
                    if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    /**
                     * when pointer is moving backwards
                     */
                    else {
                        currentBlock--;
                    }
                    blocks++;

                    /**
                     * deadline is descending with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                    }
                }
            } else if (waitingTasks.size() != 0)
            {
                System.out.println("Normal tasks: " + waitingTasks);
                /**
                 * removing task that has been done
                 */
                if (currentBlock == waitingTasks.get(0).cylinderNumber) {
                    System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }
                /**
                 *  other tasks sorted by arrival time
                 */
                if (waitingTasks.size() != 0) {
                    Task.compareWithCurrentBlock(currentBlock, waitingTasks);
                    if (waitingTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    else {
                        currentBlock--;
                    }
                    blocks++;
                }

            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000);
        return blocks;
    }
    public int SSTF_FD_SCAN() {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("priorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++)

            {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);

                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)

            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);

                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priorytet wykonany" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);

                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {

                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }

                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * deadline is descending with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                        /**
                         * doing task along the way of pointer
                         */
                        if (currentBlock == waitingPriorityTasks.get(i).cylinderNumber) {
                            System.out.println("--------------------Removed on the way" + waitingPriorityTasks.get(i));
                            waitingPriorityTasks.remove(i);
                        }
                    }
                    if (waitingPriorityTasks.size() > 0) {
                        /**
                         * when pointer is moving forwards
                         */
                        if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                            currentBlock++;
                        }
                        /**
                         * when pointer is moving backwards
                         */
                        else {
                            currentBlock--;
                        }
                        blocks++;
                    }
                }
            } else if (waitingTasks.size() != 0)
            {
                System.out.println("Normal tasks: " + waitingTasks);
                /**
                 * removing task that has been done
                 */
                if (currentBlock == waitingTasks.get(0).cylinderNumber) {
                    System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(0));
                    waitingTasks.remove(0);
                }
                /**
                 *  other tasks sorted by arrival time
                 */
                if (waitingTasks.size() != 0) {
                    Task.compareWithCurrentBlock(currentBlock, waitingTasks);
                    if (waitingTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    else {
                        currentBlock--;
                    }
                    blocks++;
                }
            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000);
        return blocks;
    }
    public int SCAN_EDF() {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        boolean forwards = false;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("prorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);
                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)

            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);
                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priority done" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {
                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }
                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * if pointer is moving forwards
                     */
                    if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    /**
                     * if pointer is moving backwards
                     */
                    else {
                        currentBlock--;
                    }
                    blocks++;
                    /**
                     * deadline is descending with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                    }
                }
            }
            /**
             * if there is no tasks in priority queue but are tasks without deadline
             */
            else if (waitingTasks.size() != 0) {
                System.out.println("Normalne: " + waitingTasks + "forwards: " + forwards);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                for(int i = 0; i < waitingTasks.size(); i++)
                {
                    if (currentBlock == waitingTasks.get(i).cylinderNumber) {
                        System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(i));
                        waitingTasks.remove(i);
                    }
                }

                /**
                 * processing other tasks from normal queue, Scanning
                 */
                if (waitingTasks.size() != 0) {
                    if (forwards) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    if (currentBlock == DISK_SIZE) {
                        forwards = false;
                    }
                    if (currentBlock == 0) {
                        forwards = true;
                    }
                    blocks++;
                }
            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000) ;
        return blocks;


    }
    public int SCAN_FD_SCAN() {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        boolean forwards = false;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("priorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);
                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)

            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);
                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priority done" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {
                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }
                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * deadline is descending with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                        /**
                         * doing task along the way of pointer
                         */
                        if (currentBlock == waitingPriorityTasks.get(i).cylinderNumber) {
                            System.out.println("--------------------Removed on the way" + waitingPriorityTasks.get(i));
                            waitingPriorityTasks.remove(i);
                        }
                    }
                    /**
                     * if pointer is moving forwards
                     */
                    if (waitingPriorityTasks.size() > 0) {
                        if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                            currentBlock++;
                        }
                        /**
                         * if pointer is moving backwards
                         */
                        else {
                            currentBlock--;
                        }
                        blocks++;
                    }
                }
            }
            /**
             * if there is no tasks in priority queue but are tasks without deadline
             */
            else if (waitingTasks.size() != 0) {
                System.out.println("Normalne: " + waitingTasks + "forwards: " + forwards);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                for(int i = 0; i < waitingTasks.size(); i++)
                {
                    if (currentBlock == waitingTasks.get(i).cylinderNumber) {
                        System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(i));
                        waitingTasks.remove(i);
                    }
                }

                /**
                 * processing other tasks from normal queue, Scanning
                 */
                if (waitingTasks.size() != 0) {
                    if (forwards) {
                        currentBlock++;
                    } else {
                        currentBlock--;
                    }
                    if (currentBlock == DISK_SIZE) {
                        forwards = false;
                    }
                    if (currentBlock == 0) {
                        forwards = true;
                    }
                    blocks++;
                }
            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000) ;
        return blocks;
    }
    public int C_SCAN_EDF ()   {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("priorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);
                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)

            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);
                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priority done" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {
                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }
                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * if pointer is moving forwards
                     */
                    if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                        currentBlock++;
                    }
                    /**
                     * if pointer is moving backwards
                     */
                    else {
                        currentBlock--;
                    }
                    blocks++;
                    /**
                     * deadline is descanding with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                    }
                }
            }
            /**
             * if there is no tasks in priority queue but are tasks without deadline
             */
            else if (waitingTasks.size() != 0) {
                System.out.println("Normalne: " + waitingTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                for(int i = 0; i < waitingTasks.size(); i++)
                {
                    if (currentBlock == waitingTasks.get(i).cylinderNumber) {
                        System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(i));
                        waitingTasks.remove(i);
                    }
                }

                /**
                 * processing other tasks from normal queue, Scanning
                 */
                if (waitingTasks.size() != 0) {
                    if (currentBlock == DISK_SIZE) {
                        currentBlock = 0;
                    }
                    currentBlock++;
                    blocks++;
                }
            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000) ;
        return blocks;

    }
    public int C_SCAN_FD_SCAN()  {
        int blocks = 0;
        int currentBlock = startingPointBlock;
        int currentTime = 0;
        int rejected = 0;
        ArrayList<Task> tasks = new ArrayList<Task>();
        ArrayList<Task> priorityTasks = new ArrayList<Task>();
        ArrayList<Task> waitingTasks = new ArrayList<Task>();
        ArrayList<Task> waitingPriorityTasks = new ArrayList<Task>();
        for (Task aTask : queue) {
            tasks.add(new Task(aTask));
        }
        for (Task aTask : priorityQueue) {
            priorityTasks.add(new Task(aTask));
        }
        System.out.println("prorityTasks" + priorityTasks);
        do {
            /**
             * copying to waiting queues when current time equals arrival time
             */
            for (int i = 0; i < tasks.size(); i++) {
                if (currentTime == (tasks.get(i)).getArrival()) {

                    waitingTasks.add(tasks.get(i));
                    System.out.println("waiting" + waitingTasks);
                }
            }
            for (int i = 0; i < priorityTasks.size(); i++)

            {
                if (currentTime == (priorityTasks.get(i)).getArrival()) {

                    waitingPriorityTasks.add(priorityTasks.get(i));
                    System.out.println("waitingPriority" + waitingPriorityTasks);
                }
            }
            if (waitingPriorityTasks.size() != 0) {
                System.out.println("Priority: " + waitingPriorityTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                if (waitingPriorityTasks.get(0).cylinderNumber == currentBlock) {
                    System.out.println("---------------------------------------------Priority done" + waitingPriorityTasks.get(0));
                    waitingPriorityTasks.remove(0);
                }
                /**
                 * when new task has come or last task has been done
                 */
                Collections.sort(waitingPriorityTasks, Task.deadlineComparator);
                if (waitingPriorityTasks.size() != 0 && currentBlock == waitingPriorityTasks.get(0).cylinderNumber) {
                    /**
                     * if pointer wont meet deadline is rejected
                     */
                    if (waitingPriorityTasks.get(0).deadline < Math.abs(waitingPriorityTasks.get(0).cylinderNumber - currentBlock)) {
                        System.out.println("rejection");
                        rejected++;
                    }
                }
                if (waitingPriorityTasks.size() != 0) {
                    /**
                     * deadline is descending with time
                     */
                    for (int i = 0; i < waitingPriorityTasks.size(); i++) {
                        waitingPriorityTasks.get(i).setDeadline(waitingPriorityTasks.get(i).deadline - 1);
                        /**
                         * doing task along the way of pointer
                         */
                        if (currentBlock == waitingPriorityTasks.get(i).cylinderNumber) {
                            System.out.println("--------------------Removed on the way" + waitingPriorityTasks.get(i));
                            waitingPriorityTasks.remove(i);
                        }
                    }
                    /**
                     * if pointer is moving forwards
                     */
                    if (waitingPriorityTasks.size() > 0) {
                        if (waitingPriorityTasks.get(0).cylinderNumber > currentBlock) {
                            currentBlock++;
                        }
                        /**
                         * if pointer is moving backwards
                         */
                        else {
                            currentBlock--;
                        }
                        blocks++;
                    }
                }
            }
            /**
             * if there is no tasks in priority queue but are tasks without deadline
             */
            else if (waitingTasks.size() != 0) {
                System.out.println("Normalne: " + waitingTasks);
                /**
                 * if pointer reaches task cylinder number its being removed
                 */
                for(int i = 0; i < waitingTasks.size(); i++)
                {
                    if (currentBlock == waitingTasks.get(i).cylinderNumber) {
                        System.out.println("-----------------------------------------------Normal task has been done: " + waitingTasks.get(i));
                        waitingTasks.remove(i);
                    }
                }

                /**
                 * processing other tasks from normal queue, Scanning
                 */
                if (waitingTasks.size() != 0) {

                    if (currentBlock == DISK_SIZE) {
                        currentBlock = 0;
                    }
                    currentBlock++;
                    blocks++;
                }
            }
            currentTime++;
            if (waitingPriorityTasks.size() != 0 || waitingTasks.size() != 0) {
                System.out.println("Currenttime: " + currentTime);
                System.out.println("Currentblock: " + currentBlock);
                System.out.println("Blocks: " + blocks);
            } else {
//                System.out.println("Waiting for a task");
            }
        }
        while (currentTime != 100000) ;
        return blocks;
    }
}
