package view;

import enums.ElectionType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Ballot;
import model.VotingSystemPane;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class ComparisonFrame extends AnchorPane {

    private int[] extraVotes;
    private ElectionType type;
    private int voters;
    private byte ratings;
    private int parties;
    private Ballot[] ballots;
    private VotingSystemPane leftSystem;
    private VotingSystemPane rightSystem;
    private int districts;
    
    public void setAllGeneratorVariables(int voters, byte ratings, int parties, ElectionType type, int districts){ //TODO who the fuck writes code like this
        this.voters = voters;
        this.ratings = ratings;
        this.parties = parties;
        this.type = type;
        this.districts = districts;
        recalculateVotes(new ActionEvent());
    }

    private void fillExtraVotes() {
        Random randGen = ThreadLocalRandom.current();
        extraVotes = new int[voters];
        for(int i=0;i<voters;i++){
            extraVotes[i] = ballots[i].getEntryBoxes()[randGen.nextInt(2)];
            //System.out.println(ballots[i].getEntryBoxes()[0]);
        }
        //System.out.println(Arrays.toString(extraVotes));
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
        if (type == ElectionType.SINGLE_DISTRICT_VOTING) {
            setParliamentarySystem(leftSystem);
            setParliamentarySystem(rightSystem);
        }
        else if(type.equals(ElectionType.PARLIAMENTARY_VOTING)){
            if(ratings>1) {
                fillExtraVotes();
            }
            setParliamentarySystem(leftSystem);
            setParliamentarySystem(rightSystem);
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

    private void setParliamentarySystem(VotingSystemPane pane){
        if(pane!=null){
            pane.setBallots(ballots);
            pane.setNumberOfParties(parties);
            pane.setDistricts(districts);
            pane.init();
        }
    }
}
