package view;

import java.util.List;

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
	
	private List<int[]> sumVoteSequence;
	private FirstPastThePost fptpVote;
	private AlternativeVote avVote;
	private int sequencePosition;

	@FXML
	private StackedBarChart<Integer,String> avStackedChart;
	
	@FXML
	private Label fptpLabel;	

	@FXML
	private Label avLabel;
	
	@FXML
	private StackedBarChart<Integer,String> fptpStackedChart;
    
    @FXML
    void backwardButton(ActionEvent event){
	    if(sequencePosition > 0){
    		sequencePosition--;
			avStackedChart.getData().clear();
	    	ObservableList<XYChart.Data<Integer,String>> stackedChartData = FXCollections.observableArrayList();
	    	int[] sumVotes = avVote.getSumVotesSequence().get(sequencePosition);
	    	Integer counter = 1;
	    	for(int iterateSum : sumVotes){
	    		stackedChartData.add(new XYChart.Data<Integer, String>(new Integer(iterateSum), counter.toString()));
	    		counter +=1;
	    	}
	    	XYChart.Series<Integer,String> chartSeries = new XYChart.Series<Integer,String>(stackedChartData);
	    	avStackedChart.getData().add(chartSeries);
    	}
    }
    
    @FXML
    void forwardButton(ActionEvent event){
    	if(sequencePosition < avVote.getSumVotesSequence().size()-1){
    		sequencePosition++;
    		avStackedChart.getData().clear();
        	ObservableList<XYChart.Data<Integer,String>> stackedChartData = FXCollections.observableArrayList();
        	int[] sumVotes = avVote.getSumVotesSequence().get(sequencePosition);
        	Integer counter = 1;
        	for(int iterateSum : sumVotes){
        		stackedChartData.add(new XYChart.Data<Integer, String>(new Integer(iterateSum), counter.toString()));
        		counter +=1;
        	}
        	XYChart.Series<Integer,String> chartSeries = new XYChart.Series<Integer,String>(stackedChartData);
        	avStackedChart.getData().add(chartSeries);
    	}
    } 
    
    @FXML
    void button(ActionEvent event){
    	int[] sumVotes = new int[10];
    	Ballot[] ballots = functional.BallotGenerator.generate(601, (byte) 5, 10);
    	for(Ballot iteratingBallot : ballots){
			sumVotes[iteratingBallot.getEntryBoxes()[0]-1]++;
		}
    	fptpVote = new FirstPastThePost(10);
    	avVote = new AlternativeVote(10);
    	Integer fptpFirstPlace = fptpVote.calculate(ballots).getPlaces()[0];
    	Integer avFirstPlace = avVote.calculate(ballots).getPlaces()[0];
    	fptpLabel.setText(fptpFirstPlace.toString());
    	avLabel.setText(avFirstPlace.toString());
    
    	sequencePosition=0;
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
    	
    	fptpStackedChart.getData().clear();
    	ObservableList<XYChart.Data<Integer,String>> fptpStackedChartData = FXCollections.observableArrayList();
    	Integer fptpCounter = 1;
    	for(int iterateSum : sumVotes){
    		fptpStackedChartData.add(new XYChart.Data<Integer, String>(new Integer(iterateSum), fptpCounter.toString()));
    		fptpCounter +=1;
    	}
    	XYChart.Series<Integer,String> fptpChartSeries = new XYChart.Series<Integer,String>(fptpStackedChartData);
    	fptpStackedChart.getData().add(fptpChartSeries);
    }

}
