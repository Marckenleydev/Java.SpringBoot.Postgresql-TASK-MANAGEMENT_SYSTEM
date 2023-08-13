package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.exceptions.EtAuthException;
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
import java.util.List;
@Repository
public class TeamRepositoryImpl implements TeamRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;
    private static final String SQL_CREATE= "INSERT INTO ET_TEAMS (TEAM_ID, NAME) VALUES(NEXTVAL('ET_TEAMS_SEQ'), ?)";
    private static final String SQL_FIND_BY_ID = "SELECT TEAM_ID, NAME FROM ET_TEAMS WHERE TEAM_ID = ?";
    private static final String SQL_UPDATE ="UPDATE ET_TEAMS SET NAME = ? " + "WHERE TEAM_ID = ?";
    private static final String SQL_DELETE = "DELETE FROM ET_TEAMS WHERE TEAM_ID = ?";
    private static final String SQL_ALL = "SELECT * FROM ET_TEAMS";

    @Override
    public Integer create(String name) throws EtAuthException {
       try{
           KeyHolder keyHolder = new GeneratedKeyHolder();
           jdbcTemplate.update(connection->{
               PreparedStatement ps = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS);
               ps.setString(1, name);
               return ps;
           },keyHolder);
           return (Integer) keyHolder.getKeys().get("TEAM_ID");
       }catch(Exception e){
           throw new EtBadRequestException("Invalid request");
       }
    }

    @Override
    public Team findTeamById(Integer teamId) {
        try{
         return    jdbcTemplate.queryForObject(SQL_FIND_BY_ID,new Object[]{teamId},teamRowMapper);

        }catch(Exception e){
            throw new EtResourceNotFoundException("Team not found");
        }
    }

    @Override
    public List<Team> findAll() {

        return jdbcTemplate.query(SQL_ALL,new Object[]{},teamRowMapper);

    }

    @Override
    public void updateTeamById(Integer teamId, Team team) throws EtBadRequestException {
        try{
            jdbcTemplate.update(
                    SQL_UPDATE, team.getName(),
                    teamId // Use teamId for the WHERE clause
            );
        }catch(Exception e){
            throw new EtBadRequestException("Invalid Request");
        }

    }

    @Override
    public void deleteTeamById(Integer teamId) throws EtAuthException {
        jdbcTemplate.update(SQL_DELETE, new Object[]{teamId});


    }



    private RowMapper<Team> teamRowMapper = ((rs, rowNum)->{
        return new Team(rs.getInt("TEAM_ID"),
                rs.getString("NAME"));
    });
}
