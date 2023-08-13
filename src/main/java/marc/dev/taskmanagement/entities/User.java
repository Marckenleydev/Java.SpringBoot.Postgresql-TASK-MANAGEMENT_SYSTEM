package marc.dev.taskmanagement.entities;

public class User {

    private Integer userId;
    private String name;
    private String email;
    private String password;
    private Integer teamId;
    private Integer taskId;
    private String role;

    public User(Integer userId, String name, String email, String password, Integer teamId, Integer taskId, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.teamId = teamId;
        this.taskId = taskId;
        this.role = role;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
