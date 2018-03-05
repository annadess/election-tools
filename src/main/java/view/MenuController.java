package view;

import java.io.IOException;

import functional.AlternativeVote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MenuController {

	private Stage primaryStage;
	
	public void init(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
    @FXML
    private TextField voters;

    @FXML
    private TextField ratings;

    @FXML
    private TextField parties;

    @FXML
    void openCompareView(ActionEvent event) throws IOException {

        ComparisonFrame controller = new ComparisonFrame();
		controller.addElementToTheLeft(new AlternativeVotePane());
		controller.addElementToTheRight(new FirstPastThePostPane());
		primaryStage.setScene(new Scene(controller));
		primaryStage.setTitle("Comparing Voting Systems");
		primaryStage.show();

        int voters = Integer.parseInt(this.voters.getText());
        byte ratings = (byte) Integer.parseInt(this.ratings.getText());
        int parties = Integer.parseInt(this.parties.getText());
        controller.setAllGeneratorVariables(voters, ratings, parties);
        controller.recalculateVotes(new ActionEvent());

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
