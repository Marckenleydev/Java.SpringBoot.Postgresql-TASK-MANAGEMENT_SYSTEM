package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.Project;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;
import marc.dev.taskmanagement.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService{
    @Autowired
    ProjectRepository projectRepository;
    @Override
    public Project createProject( String name, String description, Integer teamId, Integer clientId, Date startDate, Date endDate, String status) throws EtBadRequestException {
        int projectId = projectRepository.createProject(name,description,teamId,clientId,startDate,endDate,status);
        return projectRepository.findProjectById(projectId);
    }

    @Override
    public void updateProject(Integer projectId, Project project) throws EtBadRequestException {
          projectRepository.updateProject(projectId,project);
    }

    @Override
    public Project findProjectById(Integer projectId) throws EtResourceNotFoundException {
        return projectRepository.findProjectById(projectId);
    }

    @Override
    public void deleteProject(Integer projectId) throws EtBadRequestException {
        projectRepository.deleteProject(projectId);
    }

    @Override
    public List<Project> findAllProjects() throws EtResourceNotFoundException {
        return projectRepository.findAllProjects();
    }
}
