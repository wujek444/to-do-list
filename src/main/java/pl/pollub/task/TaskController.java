package pl.pollub.task;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.pollub.Greeting;

@Controller
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskList taskList;

//    @RequestMapping(method= RequestMethod.POST)
//    public void addTask(@RequestBody Task task) {
//        taskList.add(task);
//    }

    public List<Task> getAllTasks(){
        return taskList.getAllTasks();
    }

}