package model;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Seats {
    private List<Pair<Integer,Integer>> seatList = new ArrayList<Pair<Integer, Integer>>();

    public Seats(List<Pair<Integer, Integer>> seatList) {
        this.seatList = seatList;
    }

    public List<Pair<Integer, Integer>> getSeatList() {
        return seatList;
    }
}
