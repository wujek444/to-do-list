package pl.pollub.task2;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TaskService {

    private final UserService userService;

    private final EmailNotifier emailNotifier;

    private final Map<Integer, Task> tasks = new HashMap<>();

    private final AtomicInteger counter = new AtomicInteger();

    public void createTaskForUser(int userId, Integer... contributors){
        new Task(counter.incrementAndGet(), userId,
                 contributors != null ? Arrays.asList(contributors) : Collections.emptyList());
    }

    public void completeTask(int taskId){
        Task task = tasks.get(taskId);
        List<Integer> userIds = task.getContributors();
        userIds.add(task.getUserId());

        Set<String> emails = userIds.stream()
                                    .map(userService::getUserById)
                                    .map(User::getEmail)
                                    .collect(Collectors.toSet());

        emailNotifier.notify(taskId, emails);
    }

}
