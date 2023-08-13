package marc.dev.taskmanagement.resources;

import jakarta.servlet.http.HttpServletRequest;
import marc.dev.taskmanagement.entities.Project;
import marc.dev.taskmanagement.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectResource {
    @Autowired
    ProjectService projectService;

    @PostMapping

    public ResponseEntity<Project> createProject(
                                                 @RequestBody Map<String,Object> projectMap){
      String name = (String) projectMap.get("name");
        String description = (String) projectMap.get("description");
        Integer teamId = (Integer) projectMap.get("teamId");
        Integer clientId = (Integer) projectMap.get("clientId");
        String startDateStr = (String) projectMap.get("startDate");
        String endDateStr = (String) projectMap.get("endDate");

        Date startDate = Date.valueOf(startDateStr);
        Date endDate = Date.valueOf(endDateStr);
        String status = (String) projectMap.get("status");



        Project project = projectService.createProject(name, description, teamId, clientId, startDate, endDate, status);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Project> getProjectById(HttpServletRequest request, @PathVariable Integer projectId){
        Project project = projectService.findProjectById(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(HttpServletRequest request){
        List<Project> projects = projectService.findAllProjects();
        return new ResponseEntity<>(projects, HttpStatus.OK);

    }

    @PutMapping("/{projectId}")
    public ResponseEntity<Map<String, Boolean>> updateProject(HttpServletRequest request, @PathVariable Integer projectId,
                                                 @RequestBody Project project){
        projectService.updateProject(projectId, project);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Project has been updated successfully",true);
        return new ResponseEntity<>(map, HttpStatus.OK);

    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<Map<String, Boolean>> deleteProject(HttpServletRequest request, @PathVariable Integer projectId
                                                              ) {
        projectService.deleteProject(projectId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Project has been deleted successfully", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }


}







