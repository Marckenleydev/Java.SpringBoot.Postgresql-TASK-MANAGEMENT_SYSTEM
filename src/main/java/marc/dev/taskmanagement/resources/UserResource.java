package marc.dev.taskmanagement.resources;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import marc.dev.taskmanagement.entities.User;
import marc.dev.taskmanagement.services.UserService;
import marc.dev.taskmanagement.utils.constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")

public class UserResource {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUSer(@RequestBody Map<String, Object> userMap){
        String name = (String) userMap.get("name");
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        String role = (String) userMap.get("role");
        Integer teamId = (Integer) userMap.get("teamId"); // Use Integer instead of String
        Integer taskId = (Integer) userMap.get("taskId"); // Use Integer instead of String

        User user = userService.registerUser(name,email,password,role,teamId,taskId);


        return  new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }
    @PostMapping("/auth")
    public ResponseEntity<Map<String, String>> authUser(@RequestBody Map<String , Object> userMap){
        String email = (String) userMap.get("email");
        String password = (String) userMap.get("password");
        User user = userService.validateUser(email,password);

        return new ResponseEntity<>(generateJWTToken(user), HttpStatus.OK);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(HttpServletRequest request, @PathVariable("userId") Integer userId){
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(HttpServletRequest request){
       List<User> users = userService.fetchAllUsers();
       return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/clients")
    public ResponseEntity<List<User>> getAllClients(HttpServletRequest request){
        List<User> clients = userService.findAllClients();
        return new ResponseEntity<>(clients, HttpStatus.OK);
    }
    @GetMapping("/employees")
    public ResponseEntity<List<User>> getAllEmployees(HttpServletRequest request){
        List<User> employees = userService.findAllEmployee();
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }
    @GetMapping("/{teamId}/users")
    public ResponseEntity<List<User>> getUsersByTeam(HttpServletRequest request, @PathVariable Integer teamId){
        List<User> userList = userService.fetchUsersByTeam(teamId);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }


    @PutMapping("/user/{userId}")
    public ResponseEntity<Map<String, Boolean>> updateUser(HttpServletRequest request,@PathVariable("userId") Integer userId,
                                                           @RequestBody User user){
        userService.updateUser(userId, user);
        Map<String, Boolean> map = new HashMap<>();
        map.put("user has been updated successfully", true);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Map<String, Boolean>> deleteUser(HttpServletRequest request, @PathVariable("userId") Integer userId){
        userService.removeUser(userId);
        Map<String, Boolean> map = new HashMap<>();
        map.put("user has been deleted successfully", true);
        return  new ResponseEntity<>(map, HttpStatus.OK);
    }

    private Map<String, String> generateJWTToken(User user){
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + constants.TOKEN_VALIDITY))
                .claim("userId",user.getUserId())
                .claim("email",user.getEmail())
                .claim("name",user.getName())
                .claim("role",user.getRole())
                .claim("teamId",user.getTeamId())
                .claim("task",user.getTaskId())
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token",token);
        return map;
    }
}
