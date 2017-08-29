package pl.pollub.task2;

import java.util.*;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    UserService userService;

    @Mock
    EmailNotifier emailNotifier;

    @InjectMocks
    TaskService taskService;

    @Captor
    private ArgumentCaptor<List<String>> emailsCaptor;

    @Test
    public void sendEmailToOwnerAndContributors() {
        Task task = taskService.createTaskForUser(1, 2, 3);

        Mockito.when(userService.getUserById(Mockito.anyInt())).then(invocationOnMock -> {
            Integer id = invocationOnMock.getArgumentAt(0, int.class);
            return new User(id, "user" + id + "@wp.pl");
        });

        //then user1 completes the task:
        taskService.completeTask(task.getId(), 1);
        //check if email sent to users: [1,2,3]

        Mockito.verify(emailNotifier).notify(Mockito.eq(task.getId()), emailsCaptor.capture());

        HashSet<String> notified = new HashSet<>(emailsCaptor.getValue());
        HashSet<String> expected = new HashSet<>(Arrays.asList("user2@wp.pl", "user3@wp.pl"));

        assertEquals(expected, notified);
    }

    @Test
    public void sendEmailToOwnerAndContributorsUsingNoMockito(){
        //we've got 3 users:
        User user1 = new User(1, "user1@wp.pl");
        User user2 = new User(2, "user2@wp.pl");
        User user3 = new User(3, "user3@wp.pl");

        //but we need a taskService in order to create task for user (with contributors):
        TaskService taskService = null;

        //but to create taskService, we need userService and emailNotifier:
        UserService userService = new UserServiceMockImplementation();
        userService.addUsers(user1, user2, user3);
        EmailNotifier mockEmailNotifier = new EmailNotifierMockImplementation();

        //having all of these, now we can create needed taskService:
        taskService = new TaskService(userService, mockEmailNotifier);

        //and using it, we can create a task for user and his collaborators:
        Task sharedTask =
                taskService.createTaskForUser(user1.getId(), user2.getId(), user3.getId());

        //then user1 completes this task:
        taskService.completeTask(sharedTask.getId(), user1.getId());

        //and finally, using emailNotifier's mock implementation, we can look into 'completeTask'
        // call args:
        MockEmailNotification mockNotification = mockEmailNotifier.getResultNotification();

        Collection<String> expectedEmails =
                Lists.newArrayList(user2.getEmail(), user3.getEmail());

        //and see if notifications were sent to certain users (not the task's owner):
        assertEquals(sharedTask.getId(), mockNotification.getTaskId());
        assertEquals(2, mockNotification.getEmails().size());
        assertTrue(mockNotification.getEmails().containsAll(expectedEmails));
    }

}