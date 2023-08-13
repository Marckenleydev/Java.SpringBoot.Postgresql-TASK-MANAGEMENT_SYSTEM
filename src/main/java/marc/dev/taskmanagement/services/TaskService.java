package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.Task;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;

public interface TaskService {
    Task createTask( String description, Integer userId)throws EtBadRequestException;
    Task findTask(Integer taskId);
    void updateTask(Integer taskId, Task task) throws EtBadRequestException;
}
