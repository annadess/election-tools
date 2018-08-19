package model;

public interface VotingSystemPane {

    void setBallots(Ballot[] ballots);

    void setNumberOfParties(int partiesCount);

    void setDistricts(int numOfDistricts);

    void init();
}
