package marc.dev.taskmanagement.resources;

import jakarta.servlet.http.HttpServletRequest;
import marc.dev.taskmanagement.entities.UserTeam;
import marc.dev.taskmanagement.services.UserTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController()
@RequestMapping("/api/v1/user_teams")
public class UserTeamResource {
    @Autowired
    UserTeamService userTeamService;
    @PostMapping("/{teamId}")
    public ResponseEntity<UserTeam> addNewUser(HttpServletRequest request,
                                               @PathVariable("teamId")Integer teamId,
                                                @RequestBody Map<String, Object> userteamMap){

        Integer userId = (Integer) userteamMap.get("userId");

        UserTeam userTeam = userTeamService.addUserToTeam(teamId,userId);
        userTeamService.updateUserTeam(teamId,userId);
        return new ResponseEntity<>(userTeam, HttpStatus.OK);

    }

}
