package view;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainView extends Application {

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Election Tools");
		try {
			loadAndShowGameViewWindow(primaryStage);
		} catch (IOException e) {
			e.printStackTrace();
		}
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void loadAndShowGameViewWindow(Stage primaryStage) throws IOException {
		ClassLoader classLoader = getClass().getClassLoader();
		FXMLLoader defaultViewLoader = new FXMLLoader(classLoader.getResource("DefaultView.fxml"));
		Pane defaultViewPane = defaultViewLoader.load();
		MenuController controller = defaultViewLoader.getController();
		controller.init(primaryStage);
		Scene defaultViewScene = new Scene(defaultViewPane);
		primaryStage.setScene(defaultViewScene);
	}
}