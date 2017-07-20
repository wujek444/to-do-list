package pl.pollub.task;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;
import org.mockito.Mockito;

public class TaskListTest {

    @Test
    public void whenICreateNewTaskThenThisTaskIsOnTheTaskList() throws Exception {
        TaskList taskList = new TaskList();
        Task created1 = taskList.add(new NewTask("task1"));
        taskList.add(new NewTask( "task2"));

        assertEquals(2, taskList.getAllTasks().size());
        assertTrue(taskList.getAllTasks().contains(created1));
    }


    public void ICanRemoveExistingTask(){
        //given: a task
        TaskList taskList = new TaskList();
        taskList.add(new NewTask( "task1"));

        //when: i remove it
        Task task = taskList.getAllTasks().get(0);

        //then: it diesappears
    }

}