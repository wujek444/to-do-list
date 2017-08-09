package pl.pollub.task2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TaskServiceTest {

    @Mock
    UserService userService;

    @Mock
    EmailNotifier emailNotifier;

    @InjectMocks
    TaskService taskService;

    @Test
    public void sendEmailToOwnerAndContributors(){
        Task task = taskService.createTaskForUser(1, 2, 3);

        Mockito.when(userService.getUserById(Mockito.anyInt())).then(invocationOnMock -> {
            Integer id = invocationOnMock.getArgumentAt(0, int.class);
            return new User(id, "user" + id + "@wp.pl");
        });

        taskService.completeTask(task.getId());
        //check if email sent to users: [1,2,3]
    }

}