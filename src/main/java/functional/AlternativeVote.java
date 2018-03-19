package functional;

import java.util.Arrays;
import java.util.LinkedList;

import model.Ballot;
import model.Results;
import model.VotingSystem;
import model.WinnerResult;

public class AlternativeVote implements VotingSystem {

	private int[] sumVotes;
	private int firstPlace;
	private int max;
	private int numOfCandidates = 0;
	private int totalSumOfVotes = 0;
	private LinkedList<int[][]> ballotCountingSequence;
	private LinkedList<int[]> sumVotesSequence;
	
	public Results calculate(Ballot[] ballots) {
		this.sumVotesSequence = new LinkedList<int[]>();
		searchForMaxIfZero(ballots);
		sumVotes = getInitialSumVotes(ballots);
		int[][] ballots2D = getArrayFromBallots(ballots);
		this.sumVotesSequence.add(sumVotes);
		while(true){
			//System.out.println(Arrays.toString(sumVotes));
			numOfCandidates = 0;
			totalSumOfVotes = 0;
			setCandidatesAndTotalVotes();
			if(isSomeoneOver50Percent()){
				break;
			}else if(numOfCandidates==1){
				findTheLastRemaining();
			}else{
				int minIndex=getMinIndexFromSumArray();
				eliminateWeakestCandidate(minIndex, ballots2D);
				countSumVotes(ballots2D);
				this.sumVotesSequence.add(sumVotes);
			}
		}
		
		return WinnerResult.getResultFromFirstPlace(firstPlace);
	}

	public LinkedList<int[]> getSumVotesSequence() {
		return sumVotesSequence;
	}
	
	public LinkedList<int[][]> getBallotCountingSequence() {
		return ballotCountingSequence;
	}

	private void countSumVotes(int[][] ballots2D) {
		sumVotes = new int[max];
		for(int[] iteratingBallot : ballots2D){
			if(iteratingBallot[0]-1>-1){
				sumVotes[iteratingBallot[0]-1]++;
			}
		}
	}

	private void eliminateWeakestCandidate(int minIndex, int[][] ballots2D) {
		for(int x=0;x<ballots2D.length;x++){
			for(int y=ballots2D[0].length-1;y>-1;y--){
				if(ballots2D[x][y]==minIndex && y+1<ballots2D[0].length){
					ballots2D[x][y]=ballots2D[x][y+1];
				}else if(ballots2D[x][y]==minIndex){
					ballots2D[x][y]=0;
				}
			}
		}
	}

	private int getMinIndexFromSumArray() {
		int minIndex = 0;
		int min=totalSumOfVotes;
		for(int i=0;i<sumVotes.length;i++){
			if(min>sumVotes[i] && sumVotes[i]!=0){
				min=sumVotes[i];
				minIndex=i+1;
			}
		}
		return minIndex;
	}

	private void findTheLastRemaining() {
		for(int i=0;i<sumVotes.length;i++){
			if(sumVotes[i]!=0){
				firstPlace=i+1;
				break;
			}
		}
	}

	private void setCandidatesAndTotalVotes() {
		for(int iterateSum : sumVotes){
			totalSumOfVotes+=iterateSum;
			if(iterateSum!=0){
				numOfCandidates++;
			}
		}
	}

	private int[][] getArrayFromBallots(Ballot[] ballots) {
		int[][] ballots2D = new int[ballots.length][ballots[0].getEntryBoxes().length];
		for(int i=0;i<ballots2D.length;i++){
			ballots2D[i] = ballots[i].getEntryBoxes().clone();
		}
		return ballots2D;
	}

	private boolean isSomeoneOver50Percent() {
		for(int i=0;i<sumVotes.length;i++){
			if(sumVotes[i]>totalSumOfVotes/2){
				//System.out.println(sumVotes[i] + " " + totalSumOfVotes/2);
				firstPlace = i+1;
				return true;
			}
		}
		return false;
	}
	
	private int[] getInitialSumVotes(Ballot[] ballots) {
		sumVotes = new int[this.max];
		for(Ballot iteratingBallot : ballots){
			sumVotes[iteratingBallot.getEntryBoxes()[0]-1]++;
		}
		return sumVotes;
	}
	
	private void searchForMaxIfZero(Ballot[] ballots) {
		if(max == 0){
			for(Ballot iteratingBallot : ballots){
				if(max<iteratingBallot.getEntryBoxes()[0])
					max=iteratingBallot.getEntryBoxes()[0];
			}
		}
	}

	public AlternativeVote() {
		super();
	}
	
	public AlternativeVote(int max) {
		super();
		this.max = max;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
