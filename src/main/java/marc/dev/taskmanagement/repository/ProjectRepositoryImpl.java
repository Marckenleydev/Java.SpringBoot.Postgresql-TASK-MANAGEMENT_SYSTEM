package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Project;
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
import java.sql.Date;
import java.util.List;
@Repository
public class ProjectRepositoryImpl implements ProjectRepository{
    private static final String SQL_CREATE = "INSERT INTO ET_PROJECTS (PROJECT_ID, NAME, DESCRIPTION, TEAM_ID, CLIENT_ID, START_DATE, END_DATE, STATUS) VALUES(NEXTVAL('ET_PROJECTS_SEQ'), ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_FIND_PROJECT_BY_ID = "SELECT * FROM ET_PROJECTS WHERE PROJECT_ID = ?";
    private static final String SQL_FIND_ALL_PROJECTS ="SELECT * FROM ET_PROJECTS";
    private static final String SQL_UPDATE = "UPDATE ET_PROJECTS SET NAME = ?, DESCRIPTION = ?, TEAM_ID = ?, CLIENT_ID = ?, START_DATE = ?, END_DATE = ?, STATUS = ? WHERE PROJECT_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM ET_PROJECTS WHERE PROJECT_ID = ?";
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Override
    public Integer createProject(String name, String description,Integer teamId, Integer clientId, Date startDate, Date endDate, String status) throws EtBadRequestException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                ps.setString(2,description);
                ps.setInt(3,teamId);
                ps.setInt(4,clientId);
                ps.setDate(5,startDate);
                ps.setDate(6,endDate);
                ps.setString(7, status);
                return ps;
            },keyHolder);

            return (Integer) keyHolder.getKeys().get("PROJECT_ID");
        }catch(Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }

    @Override
    public void updateProject(Integer projectId, Project project) throws EtBadRequestException {


        try{
            jdbcTemplate.update(
                    SQL_UPDATE,
                    project.getName(),
                    project.getDescription(),
                    project.getTeamId(),
                    project.getClientId(),
                    project.getStartDate(),
                    project.getEndDate(),
                    project.getStatus(),
                    projectId
            );
        }catch(Exception e){
            throw new EtBadRequestException("Invalid Request");
        }

    }

    @Override
    public void deleteProject(Integer projectId) throws EtBadRequestException {
        jdbcTemplate.update(SQL_DELETE, new Object[]{projectId});
    }

    @Override
    public Project findProjectById(Integer projectId) throws EtResourceNotFoundException {

        try{
            return  jdbcTemplate.queryForObject(SQL_FIND_PROJECT_BY_ID, new Object[]{projectId}, projectRowMapper);
        }catch(Exception e){
            throw new EtResourceNotFoundException("Project not founded");
        }
    }

    @Override
    public List<Project> findAllProjects() throws EtResourceNotFoundException {
        return jdbcTemplate.query(SQL_FIND_ALL_PROJECTS, new Object[]{},projectRowMapper);
    }

    private RowMapper<Project> projectRowMapper = ((rs, rowNum)->{
        return new Project((rs.getInt("PROJECT_ID")),
                rs.getString("NAME"),
                rs.getString("DESCRIPTION"),
                rs.getInt("TEAM_ID"),
                rs.getInt("CLIENT_ID"),
                rs.getDate("START_DATE"),
                rs.getDate("END_DATE"),
                rs.getString("STATUS"));
    });
}
