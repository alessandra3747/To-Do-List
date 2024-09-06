import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    @Override
    public int compare(Task t1, Task t2) {

        if (t1.isDone() && !t2.isDone()) {

            return 1;

        } else if (!t1.isDone() && t2.isDone()) {

            return -1;

        }

        return t1.getDate().compareTo(t2.getDate());
    }

}
