package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.entities.User;
import marc.dev.taskmanagement.entities.UserTeam;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface UserRepository {
    Integer create(String name,String email,String password, String role,Integer teamId, Integer taskId  ) throws EtAuthException;
    User findByEmailAndPassword(String email,String password) throws EtAuthException;
    Integer getCountByEmail(String email);
    List<User> findAll();
    List<User> findAllClients();
    List<User> findAllEmployee();
    User findById(Integer userId);
    void updateById (Integer userId, User user);
    List<User> findUserByTeam(Integer teamId) throws EtResourceNotFoundException;
    void DeleteById(Integer userId);
    void updateUserTeam(Integer teamId, Integer userId )throws EtAuthException;
}
