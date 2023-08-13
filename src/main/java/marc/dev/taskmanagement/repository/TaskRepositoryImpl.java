package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Task;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class TaskRepositoryImpl implements TaskRepository{
    private static final String SQL_CREATE =  "INSERT INTO ET_TASKS (TASK_ID, DESCRIPTION, USER_ID) VALUES(NEXTVAL('ET_TASKS_SEQ'), ?, ?)";
    private static final String SQL_FIND_BY_ID = "SELECT TASK_ID, DESCRIPTION, USER_ID " +
            "FROM ET_TASKS WHERE TASK_ID = ?";

    private static final String SQL_UPDATE = "UPDATE ET_TASKS SET DESCRIPTION = ?, USER_ID = ? WHERE TASK_ID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer createTask(String description, Integer userId) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1,description);
                ps.setInt(2,userId);
                return ps;

            },keyHolder);
            return  (Integer) keyHolder.getKeys().get("TASK_ID");


        }catch(Exception e){
            throw  new EtBadRequestException("Invalid request");
        }
    }

    @Override
    public Task findTask(Integer taskId) {
       try {
           return jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new Object[]{taskId},taskRowMapper);
       }catch(Exception e){
           throw new EtResourceNotFoundException("Transaction not found");
       }
    }

    @Override
    public void updateTask(Integer taskId, Task task) throws EtBadRequestException {
        try {
            jdbcTemplate.update(SQL_UPDATE,new Object[]{
                    task.getDescription(),
                    task.getUserId(),
                    taskId});
        }catch(Exception e){
            throw new EtBadRequestException("Invalid request");
        }

    }

    private final RowMapper<Task> taskRowMapper = ((rs, rowNum)->{
        return new Task(
                rs.getInt("TASK_ID"),
                rs.getString("DESCRIPTION"),
                rs.getInt("USER_ID")
                );
    });
}
