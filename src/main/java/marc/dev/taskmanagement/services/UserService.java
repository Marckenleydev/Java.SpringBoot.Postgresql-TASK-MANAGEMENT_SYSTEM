package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.User;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface UserService {
    User validateUser(String email, String password) throws EtAuthException;

    User registerUser(String name,String email, String password, String role,Integer teamId,Integer taskId);
    User getUserById(Integer userId);
    void updateUser(Integer userId, User user) throws EtBadRequestException;
    void removeUser(Integer userId) throws EtBadRequestException;
    List<User> fetchAllUsers();
    List<User> fetchUsersByTeam(Integer teamId) throws EtResourceNotFoundException;
    List<User> findAllClients();
    List<User> findAllEmployee();

}
