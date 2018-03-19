package view;

import java.io.IOException;

import enums.ElectionType;
import enums.SingleDistrictVotingSystems;
import functional.AlternativeVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {

	private Stage primaryStage;
	
	public void init(Stage primaryStage){
		this.primaryStage = primaryStage;
        cmbBox.getItems().setAll(ElectionType.values());
	}

	@FXML
    private ComboBox<ElectionType> cmbBox;

    @FXML
    private ComboBox<SingleDistrictVotingSystems> leftBox;

    @FXML
    private ComboBox<SingleDistrictVotingSystems> rightBox;
	
    @FXML
    private TextField voters;

    @FXML
    private TextField ratings;

    @FXML
    private TextField parties;

    @FXML
    void resetChoices(ActionEvent event){
        if(cmbBox.getValue() == ElectionType.SINGLE_DISTRICT_VOTING){
            leftBox.getItems().setAll(SingleDistrictVotingSystems.values());
            rightBox.getItems().setAll(SingleDistrictVotingSystems.values());
        }
        else {
            leftBox.getItems().clear();
            rightBox.getItems().clear();
        }
    }

    @FXML
    void openCompareView(ActionEvent event) throws IOException {

        ComparisonFrame controller = new ComparisonFrame();

        if(leftBox.getValue()!= null &&
                leftBox.getValue().equals(SingleDistrictVotingSystems.FIRST_PAST_THE_POST)){
            controller.addElementToTheLeft(new FirstPastThePostPane());
        }
        else if(leftBox.getValue()!= null &&
                leftBox.getValue().equals(SingleDistrictVotingSystems.ALTERNATIVE_VOTE)){
            controller.addElementToTheLeft(new AlternativeVotePane());
        }
        if(rightBox.getValue() != null &&
                rightBox.getValue().equals(SingleDistrictVotingSystems.FIRST_PAST_THE_POST)){
            controller.addElementToTheRight(new FirstPastThePostPane());
        }
        else if(rightBox.getValue()!= null &&
                rightBox.getValue().equals(SingleDistrictVotingSystems.ALTERNATIVE_VOTE)){
            controller.addElementToTheRight(new AlternativeVotePane());
        }

        primaryStage.setScene(new Scene(controller));
		primaryStage.setTitle("Comparing Voting Systems");
		primaryStage.show();

        int voters = Integer.parseInt(this.voters.getText());
        byte ratings = (byte) Integer.parseInt(this.ratings.getText());
        int parties = Integer.parseInt(this.parties.getText());
        controller.setAllGeneratorVariables(voters, ratings, parties,cmbBox.getValue());

    	/*ClassLoader classLoader = getClass().getClassLoader();
		FXMLLoader viewLoader = new FXMLLoader(classLoader.getResource("AlternativeVotePane.fxml"));
		Pane viewPane = viewLoader.load();
		Scene viewScene = new Scene(viewPane);
		AlternativeVotePane controller = viewLoader.getController();
		int voters = Integer.parseInt(this.voters.getText());
		byte ratings = (byte) Integer.parseInt(this.ratings.getText());
		int parties = Integer.parseInt(this.parties.getText());
		primaryStage.setScene(viewScene);
		controller.setAllGeneratorVariables(voters, ratings, parties);*/
    }

}
