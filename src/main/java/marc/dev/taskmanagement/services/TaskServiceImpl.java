package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.Task;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {
    @Autowired
    TaskRepository taskRepository;
    @Override
    public Task createTask(String description, Integer userId) throws EtBadRequestException {
        Integer taskId = taskRepository.createTask(description,userId);
        return taskRepository.findTask(taskId);
    }

    @Override
    public Task findTask(Integer taskId) {
        return taskRepository.findTask(taskId);
    }

    @Override
    public void updateTask(Integer taskId, Task task) throws EtBadRequestException {
        taskRepository.updateTask(taskId, task);

    }
}
