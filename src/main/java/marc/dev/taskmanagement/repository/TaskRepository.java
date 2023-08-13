package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Task;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;

public interface TaskRepository {
    Integer createTask(String description, Integer userId)throws EtBadRequestException;
    Task findTask(Integer taskId);
    void updateTask(Integer taskId, Task task) throws EtBadRequestException;
}
