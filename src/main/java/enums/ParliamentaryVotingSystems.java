package enums;

public enum ParliamentaryVotingSystems {

    FIRST_PAST_THE_POST("First Past The Post");

    private String name;

    ParliamentaryVotingSystems(String name){
        this.name = name;
    }

    public String toString(){
        return name;
    }
}
