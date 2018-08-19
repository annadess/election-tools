package functional;

import javafx.util.Pair;
import model.Ballot;
import model.ParliamentaryVotingSystem;
import model.Seats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ParliamentFirstPastThePost implements ParliamentaryVotingSystem {

    private int numberOfDistricts;
    private int numberOfParties;
    private int numberOfTotalVoters;
    private Ballot[] ballots;

    private Pair<Integer,Integer>[] districtDividers;

    public Pair<Integer, Integer>[] getDistrictDividers() {
        return districtDividers;
    }

    public ParliamentFirstPastThePost(int numberOfDistricts, int numberOfParties, Ballot[] ballots) {
        this.numberOfDistricts = numberOfDistricts;
        this.numberOfParties = numberOfParties;
        this.numberOfTotalVoters = ballots.length;
        this.ballots = ballots;

        int votesPerDistrict = (numberOfTotalVoters / numberOfDistricts);
        int remainingVotes = (numberOfTotalVoters % numberOfDistricts);
        districtDividers = new Pair[numberOfDistricts];

        for (int i = 0; i < numberOfDistricts; i++)
        {
            if(i == 0){
                districtDividers[i] = new Pair<>(0,votesPerDistrict+((i+1 <= remainingVotes) ? 1:0));
            }else {
                districtDividers[i] = new Pair<>(districtDividers[i-1].getValue(), districtDividers[i-1].getValue()+votesPerDistrict+((i+1 <= remainingVotes) ? 1:0));
            }
        }
    }

    @Override
    public Seats calculate() {
        List<Pair<Integer,Integer>> seats = new ArrayList<>();
        FirstPastThePost[] districts = new FirstPastThePost[numberOfDistricts];
        int[] sumDistricts = new int[numberOfParties];
        for(int i=0; i<numberOfDistricts; i++){
            districts[i] = new FirstPastThePost();
            int winnerCandidate = districts[i].calculate(Arrays.copyOfRange(ballots,districtDividers[i].getKey(),districtDividers[i].getValue())).getPlaces()[0];
            sumDistricts[winnerCandidate-1]++;
        }
        for(int j=1; j<=sumDistricts.length;j++){
            seats.add(new Pair<>(j,sumDistricts[j-1]));
        }
        return new Seats(seats);
    }

    public static void main(String[] args){
        Ballot[] ballots = BallotGenerator.generate(100, (byte) 1,5);
        System.out.println(new ParliamentFirstPastThePost(10,5,ballots).calculate().getSeatList());
        System.out.println(Arrays.toString(new FirstPastThePost(5).calculate(ballots).getPlaces()));
    }
}
