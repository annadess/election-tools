package enums;

import functional.AlternativeVote;
import functional.FirstPastThePost;
import model.VotingSystem;

public enum SingleDistrictVotingSystems {

    ALTERNATIVE_VOTE("Alternative Vote"),
    FIRST_PAST_THE_POST("First Past The Post");

    private String name;

    SingleDistrictVotingSystems(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
