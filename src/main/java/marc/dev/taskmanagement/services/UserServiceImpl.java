package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.User;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;
import marc.dev.taskmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;
@Service
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;


    @Override
    public User validateUser(String email, String password) throws EtAuthException {
        if(email != null) email = email.toLowerCase();
        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String name, String email, String password, String role, Integer teamId, Integer taskId) {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null) email = email.toLowerCase();
        if(!pattern.matcher(email).matches())
            throw  new EtAuthException("Invalid email format");
        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new EtAuthException("Email already exist");
        Integer userId = userRepository.create(name,email,password,role,teamId,taskId);
        return userRepository.findById(userId);
    }

    @Override
    public User getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(Integer userId, User user) {
        userRepository.updateById(userId, user);
    }

    @Override
    public void removeUser(Integer userId) throws EtBadRequestException {
        userRepository.DeleteById(userId);
    }

    @Override
    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> fetchUsersByTeam(Integer teamId) throws EtResourceNotFoundException {
        return userRepository.findUserByTeam(teamId);
    }

    @Override
    public List<User> findAllClients() {
        return userRepository.findAllClients();
    }

    @Override
    public List<User> findAllEmployee() {
        return userRepository.findAllEmployee();
    }
}
