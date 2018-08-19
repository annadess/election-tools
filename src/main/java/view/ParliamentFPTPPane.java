package view;

import functional.ParliamentFirstPastThePost;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import model.Ballot;
import model.Seats;
import model.VotingSystemPane;

import java.io.IOException;

public class ParliamentFPTPPane extends AnchorPane implements VotingSystemPane {

    private ParliamentFirstPastThePost system;
    private int partiesCount;
    private Ballot[] ballots;
    private int numOfDistricts;

    @FXML
    private Label fptpLabel;

    @FXML
    private Label percentLabel;

    @FXML
    private PieChart pieChart;

    public ParliamentFPTPPane(){
        ClassLoader classLoader = getClass().getClassLoader();
        FXMLLoader fxmlLoader = new FXMLLoader(classLoader.getResource("ParliamentFPTPPane.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    private void addDataToPieChart(ObservableList<PieChart.Data> data, Seats seats) {
        data.clear();
        for(Pair<Integer,Integer> pair : seats.getSeatList()){
            data.add(new PieChart.Data(pair.getKey().toString(),pair.getValue().doubleValue()));
        }
    }

    @Override
    public void setBallots(Ballot[] ballots) {
        this.ballots = ballots;
    }

    @Override
    public void setNumberOfParties(int partiesCount) {
        this.partiesCount = partiesCount;
    }

    @Override
    public void setDistricts(int numOfDistricts) {
        this.numOfDistricts = numOfDistricts;
    }

    @Override
    public void init() {
        system = new ParliamentFirstPastThePost(numOfDistricts,partiesCount,ballots);
        Seats seats = system.calculate();
        addDataToPieChart(this.pieChart.getData(),seats);
        fptpLabel.setText(seats.toString());

        /*for (final PieChart.Data data : pieChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    (MouseEvent e) ->{
                            System.out.println(e.getSceneX());
                            System.out.println(e.getSceneY());
                            percentLabel.setTranslateX(e.getSceneX());
                            percentLabel.setTranslateY(e.getSceneY());
                            System.out.println(data.getPieValue());
                            percentLabel.setText(String.valueOf(data.getPieValue()) + "%");
            });
        }*/
    }

}
