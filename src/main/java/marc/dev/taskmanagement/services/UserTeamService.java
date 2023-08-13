package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.UserTeam;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

public interface UserTeamService {
    UserTeam addUserToTeam(Integer teamId, Integer userId )throws EtAuthException;
    void updateUserTeam(Integer teamId, Integer userId )throws EtAuthException;
    UserTeam fetchUseTeamById(Integer userteamId) throws EtResourceNotFoundException;
}
