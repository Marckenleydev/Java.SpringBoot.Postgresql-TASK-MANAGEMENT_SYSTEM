package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.Team;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;
import marc.dev.taskmanagement.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;
    @Override
    public List<Team> fetchAllTeam() {

        return teamRepository.findAll();
    }

    @Override
    public Team fetchTeamById(Integer teamId) throws EtResourceNotFoundException {
        return teamRepository.findTeamById(teamId);
    }

    @Override
    public Team addTeam(String name) throws EtBadRequestException {
        int teamId = teamRepository.create(name);
        return teamRepository.findTeamById(teamId);
    }

    @Override
    public void updateTeam(Integer teamId, Team team) throws EtBadRequestException {
        teamRepository.updateTeamById(teamId,team);

    }



    @Override
    public void removeTeam(Integer teamId) throws EtBadRequestException {
        teamRepository.deleteTeamById(teamId);

    }
}
