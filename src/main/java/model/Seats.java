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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Pair pair : seatList){
            sb.append("Party no.").append(pair.getKey().toString()).append(": ").append(pair.getValue()).append("  ");
        }
        return sb.toString()    ;
    }
}
