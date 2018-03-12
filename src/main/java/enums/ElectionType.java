package enums;

public enum ElectionType {
    SINGLE_DISTRICT_VOTING("Single District Voting"),
    PARLIAMENTARY_VOTING("Parliamentary Voting");

    private String label;

    ElectionType(String label){
        this.label = label;
    }

    public String toString(){
        return label;
    }
}
