package view;

import java.io.IOException;

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
    	ClassLoader classLoader = getClass().getClassLoader();
		FXMLLoader viewLoader = new FXMLLoader(classLoader.getResource("ExpView.fxml"));
		Pane viewPane = (Pane) viewLoader.load();
		Scene viewScene = new Scene(viewPane);
		DefaultController controller = viewLoader.getController();
		int voters = Integer.parseInt(this.voters.getText());
		byte ratings = (byte) Integer.parseInt(this.ratings.getText());
		int parties = Integer.parseInt(this.parties.getText());
		primaryStage.setScene(viewScene);
		controller.setAllGeneratorVariables(voters, ratings, parties);
    }

}
