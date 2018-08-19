package view;

import java.util.ArrayList;
import java.util.List;

import functional.AlternativeVote;
import functional.FirstPastThePost;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import model.Ballot;

public class DefaultController {

	private FirstPastThePost fptpVote;
	private AlternativeVote avVote;
	private int sequencePosition;
	private List<XYChart.Series> avSeriesList = new ArrayList<XYChart.Series>();

	private int voters;
	private byte ratings;
	private int parties;
	private int districts;
	
	public void setAllGeneratorVariables(int voters, byte ratings, int parties, int districts){
		this.voters = voters;
		this.ratings = ratings;
		this.parties = parties;
		this.districts = districts;
	}
	
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
			avSeriesList = new ArrayList<XYChart.Series>();
			int[] sumVotes = avVote.getSumVotesSequence().getFirst();

			resetAvChart(sumVotes);

			int tempSeqPosition = sequencePosition;
			sequencePosition = 0;
			for(;sequencePosition<tempSeqPosition;) {
				forwardButton(new ActionEvent());
			}
    	}
    }
    
    @FXML
    void forwardButton(ActionEvent event){
    	if(sequencePosition < avVote.getSumVotesSequence().size()-1){
    		sequencePosition++;
    		avStackedChart.getData().clear();
    		addNextElementToSeriesList();
			for(XYChart.Series series : avSeriesList){
				avStackedChart.getData().add(series);
			}
    	}
    }

	@FXML
    void button(ActionEvent event){
		int[] sumVotes = initWindowAndGetSumVotes();
    
    	sequencePosition=0;
    	resetAvChart(sumVotes);

		XYChart.Series<Integer,String> fptpStackedChartData = new XYChart.Series<Integer, String>();
		addDataToSeries(sumVotes, fptpStackedChartData, fptpStackedChart.getData());
    }

	private int[] initWindowAndGetSumVotes() {
		avSeriesList = new ArrayList<XYChart.Series>();
		int[] sumVotes = new int[parties];
		Ballot[] ballots = functional.BallotGenerator.generate(voters, ratings, parties);
		addVotesToSumArrayFromPosition(sumVotes, ballots, 0);
		fptpVote = new FirstPastThePost(parties);
		avVote = new AlternativeVote(parties);
		Integer fptpFirstPlace = fptpVote.calculate(ballots).getPlaces()[0];
		Integer avFirstPlace = avVote.calculate(ballots).getPlaces()[0];
		fptpLabel.setText(fptpFirstPlace.toString());
		avLabel.setText(avFirstPlace.toString());
		sumVotes = avVote.getSumVotesSequence().getFirst();
		return sumVotes;
	}

	private void resetAvChart(int[] sumVotes) {
		XYChart.Series<Integer,String> stackedChartData = new XYChart.Series<Integer, String>();
		addDataToSeries(sumVotes, stackedChartData, avStackedChart.getData());
		avSeriesList.add(stackedChartData);
	}

	private void addDataToSeries(int[] sumVotes, XYChart.Series<Integer, String> stackedChartData, ObservableList<XYChart.Series<Integer, String>> data) {
		data.clear();
		stackedChartData.setName("From first preference");
		Integer counter = 1;
		for (int iterateSum : sumVotes) {
			stackedChartData.getData().add(new XYChart.Data<Integer, String>(new Integer(iterateSum), counter.toString()));
			counter += 1;
		}
		data.add(stackedChartData);
	}

	private void addVotesToSumArrayFromPosition(int[] sumVotes, Ballot[] ballots, int position) {
		for(Ballot iteratingBallot : ballots){
            sumVotes[iteratingBallot.getEntryBoxes()[position]-1]++;
        }
	}

	private void addNextElementToSeriesList() {
    	XYChart.Series<Integer,String> nextSeries = new XYChart.Series<>();
		int[] currentSum = avVote.getSumVotesSequence().get(sequencePosition).clone();
    	int[] previousSum = avVote.getSumVotesSequence().get(sequencePosition-1).clone();
		XYChart.Series previousSeries = avSeriesList.get(sequencePosition-1);
    	for (int i=0; i<currentSum.length;i++){
    		currentSum[i] = currentSum[i] - previousSum[i];
			if(currentSum[i]<0) {
				nextSeries.setName(String.valueOf(i + 1));
				currentSum[i]=0;
				removeColumnFromSeriesList(i);
			}
			nextSeries.getData().add(new XYChart.Data<Integer, String>(new Integer(currentSum[i]), String.valueOf(i+1)));
    	}
		avSeriesList.add(nextSeries);
    }

	private void removeColumnFromSeriesList(int removeIndex) {
    	for(int i=0;i<sequencePosition;i++){
			XYChart.Data<Integer, String> removingData = (XYChart.Data<Integer, String>) avSeriesList.get(i).getData().get(removeIndex);
    		removingData.setXValue(0);
		}
	}

}
