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

    public Task createTaskForUser(int userId, Integer... contributors){
        Task task = new Task(counter.incrementAndGet(), userId,
                             contributors != null ? Arrays.asList(contributors) : Collections.emptyList());
        tasks.put(task.getId(), task);
        return task;
    }

    public void completeTask(int taskId, int idOfUserWhoCompletedTask){
        Task task = tasks.get(taskId);
        task.setIdOfUserWhoCompletedTask(idOfUserWhoCompletedTask);
        List<Integer> userIds = new ArrayList(task.getContributors());
        userIds.add(task.getUserId());

        Set<String> emails = userIds.stream()
                                    .filter(userId -> userId != task.getIdOfUserWhoCompletedTask())
                                    .map(userService::getUserById)
                                    .map(User::getEmail)
                                    .collect(Collectors.toSet());

        emailNotifier.notify(taskId, emails);
    }

}
