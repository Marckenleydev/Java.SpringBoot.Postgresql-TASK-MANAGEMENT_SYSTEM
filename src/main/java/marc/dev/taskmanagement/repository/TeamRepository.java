package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;

import java.util.List;

public interface TeamRepository {
    Integer create(String name) throws EtAuthException;
    Team findTeamById(Integer teamId);
    List<Team> findAll();

    void updateTeamById(Integer teamId, Team team) throws EtBadRequestException;
    void deleteTeamById(Integer teamId)throws EtAuthException;


}
