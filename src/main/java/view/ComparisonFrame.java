package view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Ballot;
import model.VotingSystemPane;

import java.io.IOException;

public class ComparisonFrame extends AnchorPane {

    private int voters;
    private byte ratings;
    private int parties;
    private Ballot[] ballots;
    private VotingSystemPane leftSystem;
    private VotingSystemPane rightSystem;

    public void setAllGeneratorVariables(int voters, byte ratings, int parties){
        this.voters = voters;
        this.ratings = ratings;
        this.parties = parties;
        ballots = functional.BallotGenerator.generate(voters, ratings, parties);
    }

    public ComparisonFrame(){
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("ComparisonFrame.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private GridPane compareGrid;

    @FXML
    void recalculateVotes(ActionEvent event) {
        ballots = functional.BallotGenerator.generate(voters, ratings, parties);
        if(leftSystem!=null){
            leftSystem.setBallotsAndParties(ballots,parties);
        }
        if(rightSystem!=null){
            rightSystem.setBallotsAndParties(ballots,parties);
        }
    }

    public void addElementToTheRight(Node element){
        compareGrid.add(element,1,0);
        rightSystem = (VotingSystemPane) element;
    }

    public void addElementToTheLeft(Node element){
        compareGrid.add(element,0,0);
        leftSystem = (VotingSystemPane) element;
    }

}
