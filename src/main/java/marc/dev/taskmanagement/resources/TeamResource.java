package marc.dev.taskmanagement.resources;

import jakarta.servlet.http.HttpServletRequest;
import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/teams")
public class TeamResource {
    @Autowired
    TeamService teamService;
    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestBody Map<String,Object> teamMap){
        String name = (String) teamMap.get("name");

        Team team = teamService.addTeam(name);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @GetMapping("/{teamId}")
    public ResponseEntity<Team> getTeam(HttpServletRequest request, @PathVariable("teamId") Integer teamId){


        Team team = teamService.fetchTeamById(teamId);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams(HttpServletRequest request){


       List<Team>  teams = teamService.fetchAllTeam();
        return new ResponseEntity<>(teams, HttpStatus.OK);
    }

    @PutMapping("/{teamId}")
    public ResponseEntity<Map<String, Boolean>> updateTeam(HttpServletRequest request, @PathVariable("teamId") Integer teamId,
                                           @RequestBody Team team){
        teamService.updateTeam(teamId, team);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Team has been updated successfully",true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @DeleteMapping("/{teamId}")
    public  ResponseEntity<Map<String,Boolean>> updateTeam(HttpServletRequest request,@PathVariable("teamId") Integer teamId){
        teamService.removeTeam(teamId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("Team has been deleted successfully",true);
        return new ResponseEntity<>(map,HttpStatus.OK);
    }

}
