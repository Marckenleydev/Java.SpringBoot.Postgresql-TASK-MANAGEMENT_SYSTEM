package marc.dev.taskmanagement.entities;

import java.sql.Date;
import java.time.LocalDate;

public class Project {
    private Integer ProjectId;
    private String name;
    private String description;
    private Integer teamId;
    private Integer clientId;
    private Date startDate;
    private Date endDate;
    private String status;

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Project(Integer projectId, String name, String description, Integer teamId, Integer clientId, Date startDate, Date endDate, String status) {
        ProjectId = projectId;
        this.name = name;
        this.description = description;
        this.teamId = teamId;
        this.clientId = clientId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public Integer getProjectId() {
        return ProjectId;
    }

    public void setProjectId(Integer projectId) {
        ProjectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
