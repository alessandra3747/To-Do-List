import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class TaskLog implements Iterable<Task> {

    private static TaskLog taskLogInstance = null;

    private ArrayList<Task> taskLog;


    public static TaskLog getInstance() {

        if (taskLogInstance == null)
            taskLogInstance = new TaskLog();

        return taskLogInstance;

    }

    private TaskLog(){
       this.taskLog = new ArrayList<>();
    }



    public void addTask(Task task) {
        taskLog.add(task);
    }

    public Task getTask(int index) {
        return taskLog.get(index);
    }


    public void removeTaskById(int taskId) throws NoTaskFoundException {

        boolean exists = taskLog.stream().anyMatch(task -> task.getId() == taskId);

        if (!exists)
            throw new NoTaskFoundException();

        taskLog.removeIf(e -> e.getId() == taskId);

    }

    public void sortTasks(){
        this.taskLog.sort(new TaskComparator());
    }


    @Override
    public Iterator<Task> iterator() {

        return new Iterator<>() {
            int counter = 0;

            @Override
            public boolean hasNext() {
                return counter < taskLog.size();
            }

            @Override
            public Task next() {
                if(!hasNext())
                    throw new NoSuchElementException();

                return taskLog.get(counter++);
            }
        };

    }

    @Override
    public void forEach(Consumer<? super Task> action) {
        Iterable.super.forEach(action);
    }


}
