package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import model.Ballot;

public class DefaultController {

    @FXML
    private PieChart pieChart;
    
    @FXML
    void button(ActionEvent event){
    	pieChart.getData().clear(); 
    	int[] sumVotes = new int[10];
    	for(Ballot iteratingBallot : functional.BallotGenerator.generate(600, (byte) 1, 10)){
			sumVotes[iteratingBallot.getEntryBoxes()[0]-1]++;
		}
    	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    	for(int i=0; i<10;i++){
    		pieChartData.add(new PieChart.Data(new Integer(i+1).toString(), sumVotes[i]));
    	}
    	 pieChart.getData().addAll(pieChartData);
    }

}
