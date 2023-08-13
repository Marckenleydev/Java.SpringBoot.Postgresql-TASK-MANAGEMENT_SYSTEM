package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.User;
import marc.dev.taskmanagement.entities.UserTeam;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private static final String SQL_CREATE = "INSERT INTO ET_USERS(USER_ID, NAME, EMAIL, PASSWORD, ROLE, TEAM_ID, TASK_ID) VALUES(NEXTVAL('ET_USERS_SEQ'), ?, ?, ?, ?, ?, ?)";
    private static final String SQL_COUNT_BY_EMAIL = "SELECT COUNT(*) FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_FIND_BY_ID = "SELECT USER_ID, NAME, EMAIL, PASSWORD, ROLE, TEAM_ID, TASK_ID " +
            "FROM ET_USERS WHERE USER_ID = ?";
    private static final String SQL_UPDATE_BY_ID = "UPDATE ET_USERS SET NAME = ?, EMAIL = ?, ROLE = ? WHERE USER_ID = ?";
    private static  final String SQL_DELETE_BY_ID ="DELETE FROM ET_USERS WHERE USER_ID = ?";


    private static  final String SQL_FIND_ALL_USERS = "SELECT * FROM ET_USERS";
    private static final String SQL_FIND_BY_EMAIL = "SELECT USER_ID, NAME, EMAIL, PASSWORD, ROLE, TEAM_ID, TASK_ID " +
            "FROM ET_USERS WHERE EMAIL = ?";
    private static final String SQL_UPDATE_USER_TEAM = "UPDATE ET_USERS SET TEAM_ID = ? WHERE USER_ID = ?";
    private static final String SQL_FIND_USERS_BY_TEAM_ID ="SELECT * FROM ET_USERS WHERE  TEAM_ID = ?";
    private static final String SQL_FIND_CLIENTS =" SELECT * FROM ET_USERS WHERE role = 'client'";
    private static final String SQL_FIND_EMPLOYEES =" SELECT * FROM ET_USERS WHERE role = 'employee'";

@Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer create(String name, String email, String password, String role, Integer teamId, Integer taskId) throws EtAuthException {
       String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
       try{
           KeyHolder keyHolder = new GeneratedKeyHolder();
           jdbcTemplate.update(connection->{
               PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, name);
               ps.setString(2, email);
               ps.setString(3, hashedPassword); // Correct index for password
               ps.setString(4, role);
               ps.setInt(5, teamId); // Use ps.setInt for Integer values
               ps.setInt(6, taskId); // Use ps.setInt for Integer values
               return ps;
           },keyHolder);
           return (Integer) keyHolder.getKeys().get("USER_ID");

       }catch(Exception e){
           throw new EtAuthException("Invalid details. Failed to create account");

       }
    }

    public User findByEmailAndPassword(String email, String password) throws EtAuthException {
        try {
            User user = jdbcTemplate.queryForObject(SQL_FIND_BY_EMAIL, new Object[]{email}, userRowMapper);
            if(!BCrypt.checkpw(password, user.getPassword()))
                throw new EtAuthException("Invalid password");
            return user;
        }catch(EmptyResultDataAccessException e){
            throw new EtAuthException("Invalid email");
        }
    }

    @Override
    public Integer getCountByEmail(String email) {
        return jdbcTemplate.queryForObject(SQL_COUNT_BY_EMAIL, new Object[]{email}, Integer.class);
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(SQL_FIND_ALL_USERS, new Object[]{}, userRowMapper);
    }

    @Override
    public List<User> findAllClients() {
        List<User> clientList = jdbcTemplate.query(SQL_FIND_CLIENTS, new Object[]{},userRowMapper);
        return clientList;
    }

    @Override
    public List<User> findAllEmployee() {
        List<User> employeeList = jdbcTemplate.query(SQL_FIND_EMPLOYEES, new Object[]{},userRowMapper);
        return employeeList;
    }

    @Override
    public User findById(Integer userId) {
        return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{userId}, userRowMapper);
    }



    @Override
    public void updateById(Integer userId, User user) {

        try{
            jdbcTemplate.update(
                    SQL_UPDATE_BY_ID,
                    user.getName(),
                    user.getEmail(),
                    user.getRole(),
                    userId // Use userId for the WHERE clause
            );
        }catch(Exception e){
            throw new EtBadRequestException("Invalid request");

        }
    }

    @Override
    public List<User> findUserByTeam(Integer teamId) throws EtResourceNotFoundException {
        List<User> userList = jdbcTemplate.query(SQL_FIND_USERS_BY_TEAM_ID, new Object[]{teamId},userRowMapper);
        return userList;
    }

    @Override
    public void  DeleteById(Integer userId) {
         jdbcTemplate.update(SQL_DELETE_BY_ID, new Object[]{userId});
    }

    @Override
    public void updateUserTeam(Integer teamId, Integer userId) throws EtAuthException {
        jdbcTemplate.update(SQL_UPDATE_USER_TEAM,teamId ,userId);
    }

    private RowMapper<User> userRowMapper = ((rs, rowNum) -> {
        return new User(
                rs.getInt("USER_ID"),
                rs.getString("NAME"),
                rs.getString("EMAIL"),
                rs.getString("PASSWORD"),
                rs.getInt("TEAM_ID"),
                rs.getInt("TASK_ID"),
                rs.getString("ROLE"));
    });


}
