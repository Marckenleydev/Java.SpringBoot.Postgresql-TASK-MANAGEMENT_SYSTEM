package marc.dev.taskmanagement.repository;

import marc.dev.taskmanagement.entities.UserTeam;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;

import java.util.List;

public interface UserTeamRepository {
    boolean isUserAlreadyExist(Integer teamId, Integer userId);

    Integer addUserToTeam(Integer teamId, Integer userId )throws EtAuthException;

    UserTeam findUserTeam(Integer UserTeam) throws EtResourceNotFoundException;

}
