package view;

import functional.AlternativeVote;
import functional.FirstPastThePost;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Ballot;

public class DefaultController {

	@FXML
	private StackedBarChart<Integer,String> avStackedChart;
	
	@FXML
	private Label fptpLabel;	

	@FXML
	private Label avLabel;
	
    @FXML
    private PieChart pieChart;
    
    @FXML
    void backwardButton(ActionEvent event){
    	
    }
    
    @FXML
    void forwardButton(ActionEvent event){
    	
    } 
    
    @FXML
    void button(ActionEvent event){
    	pieChart.getData().clear(); 
    	int[] sumVotes = new int[10];
    	Ballot[] ballots = functional.BallotGenerator.generate(601, (byte) 5, 10);
    	for(Ballot iteratingBallot : ballots){
			sumVotes[iteratingBallot.getEntryBoxes()[0]-1]++;
		}
    	FirstPastThePost fptpVote = new FirstPastThePost(10);
    	AlternativeVote avVote = new AlternativeVote(10);
    	Integer fptpFirstPlace = fptpVote.calculate(ballots).getPlaces()[0];
    	Integer avFirstPlace = avVote.calculate(ballots).getPlaces()[0];
    	fptpLabel.setText(fptpFirstPlace.toString());
    	avLabel.setText(avFirstPlace.toString());
    	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    	for(int i=0; i<10;i++){
    		pieChartData.add(new PieChart.Data(new Integer(i+1).toString(), sumVotes[i]));
    	}
    	pieChart.getData().addAll(pieChartData);
    
    	
    	avStackedChart.getData().clear();
    	ObservableList<XYChart.Data<Integer,String>> stackedChartData = FXCollections.observableArrayList();
    	sumVotes = avVote.getSumVotesSequence().getFirst();
    	Integer counter = 1;
    	for(int iterateSum : sumVotes){
    		stackedChartData.add(new XYChart.Data<Integer, String>(new Integer(iterateSum), counter.toString()));
    		counter +=1;
    	}
    	XYChart.Series<Integer,String> chartSeries = new XYChart.Series<Integer,String>(stackedChartData);
    	avStackedChart.getData().add(chartSeries);
    }

}
