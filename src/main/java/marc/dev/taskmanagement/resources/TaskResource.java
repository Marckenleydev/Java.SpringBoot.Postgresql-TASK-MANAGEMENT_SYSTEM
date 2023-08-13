package marc.dev.taskmanagement.resources;

import jakarta.servlet.http.HttpServletRequest;
import marc.dev.taskmanagement.entities.Task;
import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskResource {
    @Autowired
    TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(HttpServletRequest request,
                                           @RequestBody Map<String, Object> taskMap) {
        String description = (String) taskMap.get("description");
        Integer userId = (Integer) taskMap.get("userId");


        Task task = taskService.createTask(description, userId);

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTransactionById(HttpServletRequest request,
                                                          @PathVariable("taskId") Integer taskId
                                                         ){
        Task task = taskService.findTask(taskId);
        return new ResponseEntity<>(task, HttpStatus.OK);

    }
}
