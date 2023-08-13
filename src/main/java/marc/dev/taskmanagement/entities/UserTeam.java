package marc.dev.taskmanagement.entities;

public class UserTeam {
    private Integer UserTeam;
    private Integer teamId;
    private Integer userId;

    public UserTeam(Integer userTeam, Integer teamId, Integer userId) {
        UserTeam = userTeam;
        this.teamId = teamId;
        this.userId = userId;
    }

    public Integer getUserTeam() {
        return UserTeam;
    }

    public void setUserTeam(Integer userTeam) {
        UserTeam = userTeam;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
