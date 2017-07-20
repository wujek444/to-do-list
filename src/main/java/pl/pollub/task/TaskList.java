package pl.pollub.task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class TaskList {

    private final List<Task> tasks = new ArrayList<>();

    private final AtomicInteger counter = new AtomicInteger();

    public Task add(NewTask newTask){
        Task created = new Task(generateId(), newTask.getContent());
        tasks.add(created);
        return created;
    }

    private int generateId() {
        return counter.incrementAndGet();
    }

    public List<Task> getAllTasks() {
        return tasks;
    }
}
