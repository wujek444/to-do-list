package pl.pollub.task2;

import org.junit.Test;

public class TaskServiceTest {

    @Test
    public void sendEmailToOwnerAndContributors(){

        TaskService taskService = new TaskService(null, null);
        taskService.createTaskForUser(1, 2,3);

        taskService.completeTask(1);

        //check if email sent to users: [1,2,3]
    }

}