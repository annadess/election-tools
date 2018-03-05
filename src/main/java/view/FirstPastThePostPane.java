package view;

import functional.AlternativeVote;
import functional.FirstPastThePost;
import javafx.collections.ObservableList;
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

public class FirstPastThePostPane extends AnchorPane implements VotingSystemPane {

    private int partiesCount;
    private FirstPastThePost fptpVote;

    @FXML
    private Label fptpLabel;

    @FXML
    private StackedBarChart<Integer,String> fptpStackedChart;

    public FirstPastThePostPane(){
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("FirstPastThePostPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    @Override
    public void setBallotsAndParties(Ballot[] ballots, int partiesCount) {
        this.partiesCount = partiesCount;
        int[] sumVotes = initWindowAndGetSumVotes(ballots);

        XYChart.Series<Integer,String> fptpStackedChartData = new XYChart.Series<Integer, String>();
        addDataToSeries(sumVotes, fptpStackedChartData, fptpStackedChart.getData());
    }

    private int[] initWindowAndGetSumVotes(Ballot[] ballots) {
        int[] sumVotes = new int[partiesCount];
        addVotesToSumArrayFromPosition(sumVotes, ballots, 0);
        fptpVote = new FirstPastThePost(partiesCount);
        Integer fptpFirstPlace = fptpVote.calculate(ballots).getPlaces()[0];
        fptpLabel.setText(fptpFirstPlace.toString());
        return sumVotes;
    }

    private void addVotesToSumArrayFromPosition(int[] sumVotes, Ballot[] ballots, int position) {
        for(Ballot iteratingBallot : ballots){
            sumVotes[iteratingBallot.getEntryBoxes()[position]-1]++;
        }
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
}
