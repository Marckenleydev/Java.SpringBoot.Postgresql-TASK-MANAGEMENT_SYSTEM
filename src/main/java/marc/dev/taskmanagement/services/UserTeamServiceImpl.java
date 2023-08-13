package marc.dev.taskmanagement.services;

import marc.dev.taskmanagement.entities.UserTeam;
import marc.dev.taskmanagement.exceptions.EtAuthException;
import marc.dev.taskmanagement.exceptions.EtBadRequestException;
import marc.dev.taskmanagement.exceptions.EtResourceNotFoundException;
import marc.dev.taskmanagement.repository.UserRepository;
import marc.dev.taskmanagement.repository.UserTeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserTeamServiceImpl implements UserTeamService {
    @Autowired
    UserTeamRepository userTeamRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public UserTeam addUserToTeam(Integer teamId, Integer userId) throws EtAuthException {
      if(userTeamRepository.isUserAlreadyExist(teamId,userId)){
          throw new EtBadRequestException("User is already in the team");
      }
        int userteamId =  userTeamRepository.addUserToTeam(teamId,userId);
        return userTeamRepository.findUserTeam(userteamId);
    }

    @Override
    public void updateUserTeam(Integer teamId, Integer userId) throws EtAuthException {
        userRepository.updateUserTeam(teamId,userId);
    }

    @Override
    public UserTeam fetchUseTeamById(Integer userteamId) throws EtResourceNotFoundException {
        return userTeamRepository.findUserTeam(userteamId);
    }
}
