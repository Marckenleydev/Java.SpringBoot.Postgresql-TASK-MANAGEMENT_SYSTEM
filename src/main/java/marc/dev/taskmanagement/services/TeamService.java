package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.entities.User;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface TeamService {
    List<Team> fetchAllTeam();
    Team fetchTeamById(Integer teamId) throws EtResourceNotFoundException;
    Team addTeam(String name) throws EtBadRequestException;
    void updateTeam(Integer teamId, Team team) throws EtBadRequestException;

    void removeTeam(Integer teamId) throws EtBadRequestException;
}
