package marc.dev.taskmanagement.entities;

public class Task {
    private Integer taskId;
    private String description ;
    private Integer userId;

    public Task(int taskId, String description, Integer userId) {
        this.taskId = taskId;
        this.description = description;
        this.userId = userId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
