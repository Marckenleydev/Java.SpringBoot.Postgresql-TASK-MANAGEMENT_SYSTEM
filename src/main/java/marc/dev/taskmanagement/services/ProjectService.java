package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.Project;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

import java.sql.Date;
import java.util.List;

public interface ProjectService {
    Project  createProject( String name,
                           String description,
                           Integer teamId,
                           Integer clientId,
                           Date startDate,
                           Date endDate,
                           String status) throws EtBadRequestException;
    void updateProject(Integer projectId, Project project) throws EtBadRequestException;
    Project findProjectById(Integer projectId) throws EtResourceNotFoundException;
    void deleteProject(Integer projectId) throws EtBadRequestException;
    List<Project> findAllProjects()throws EtResourceNotFoundException;
}
