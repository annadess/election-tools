package view;

import functional.AlternativeVote;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import model.Ballot;
import model.VotingSystemPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlternativeVotePane extends AnchorPane implements VotingSystemPane {

    private int partiesCount;
    private AlternativeVote avVote;
    private int sequencePosition;
    private List<XYChart.Series> avSeriesList = new ArrayList<XYChart.Series>();

    public AlternativeVotePane(){
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("AlternativeVotePane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    private Label avLabel;

    @FXML
    private StackedBarChart<Integer, String> avStackedChart;

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

    @Override
    public void setBallotsAndParties(Ballot[] ballots,int partiesCount) {
        this.partiesCount = partiesCount;
        int[] sumVotes = initWindowAndGetSumVotes(ballots);

        sequencePosition=0;
        resetAvChart(sumVotes);
    }

    private int[] initWindowAndGetSumVotes(Ballot[] ballots) {
        avSeriesList = new ArrayList<XYChart.Series>();
        int[] sumVotes = new int[partiesCount];
        addVotesToSumArrayFromPosition(sumVotes, ballots, 0);
        avVote = new AlternativeVote(partiesCount);
        Integer avFirstPlace = avVote.calculate(ballots).getPlaces()[0];
        avLabel.setText(avFirstPlace.toString());
        //sumVotes = avVote.getSumVotesSequence().getFirst();
        return sumVotes;
    }

    private void addVotesToSumArrayFromPosition(int[] sumVotes, Ballot[] ballots, int position) {
        for(Ballot iteratingBallot : ballots){
            sumVotes[iteratingBallot.getEntryBoxes()[position]-1]++;
        }
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
