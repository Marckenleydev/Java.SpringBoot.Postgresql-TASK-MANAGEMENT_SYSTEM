package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Project;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

import java.sql.Date;
import java.util.List;

public interface ProjectRepository {
    Integer  createProject( String name,
                                 String description,
                                 Integer teamId,
                                 Integer clientId,
                                 Date startDate,
                                 Date endDate,
                                 String status) throws EtBadRequestException;
    void updateProject(Integer projectId, Project project) throws EtBadRequestException;
    void deleteProject(Integer projectId) throws EtBadRequestException;
    Project findProjectById(Integer projectId) throws EtResourceNotFoundException;
    List<Project> findAllProjects()throws EtResourceNotFoundException;


}


