package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.UserTeam;
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
public class UserTeamRepositoryImpl implements UserTeamRepository{

    private static final String SQL_ADD_USER = "INSERT INTO ET_USER_TEAMS (USER_TEAM_ID, TEAM_ID, USER_ID) VALUES(NEXTVAL('ET_USER_TEAMS_SEQ'), ?, ?)";
    private static final String SQL_FIND_USERTEAM = "SELECT USER_TEAM_ID, TEAM_ID, USER_ID " + "FROM ET_USER_TEAMS WHERE USER_TEAM_ID = ?";
    private static final String SQL_CHECK_IF_USER_EXIST_ALREADY ="SELECT COUNT(*) FROM ET_USER_TEAMS WHERE TEAM_ID = ? AND USER_ID = ?";



    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public boolean isUserAlreadyExist(Integer teamId, Integer userId){
        Integer count = jdbcTemplate.queryForObject(SQL_CHECK_IF_USER_EXIST_ALREADY, new Object[]{teamId,userId}, Integer.class);
         return  count != null && count > 0;
    }
    @Override
    public Integer addUserToTeam(Integer teamId, Integer userId) throws EtAuthException {
        try{
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection->{
                PreparedStatement ps = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1,teamId);
                ps.setInt(2,userId);
                return ps;
            },keyHolder);
            return (Integer) keyHolder.getKeys().get("USER_TEAM_ID");
        }catch(Exception e){
            throw new EtBadRequestException("Invalid Request");
        }
    }



    @Override
    public UserTeam findUserTeam(Integer UserTeamId) throws EtResourceNotFoundException {
        return jdbcTemplate.queryForObject(SQL_FIND_USERTEAM, new Object[]{UserTeamId},userteamRowMapper);
    }



    private RowMapper<UserTeam> userteamRowMapper = ((rs, rowNum)->{
        return new UserTeam(rs.getInt("USER_TEAM_ID"),
                rs.getInt("TEAM_ID"),
                rs.getInt("USER_ID"));

    });
}
