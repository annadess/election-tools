package functional;

import model.Ballot;
import model.PostitionalResult;
import model.Results;
import model.VotingSystem;

public class FirstPastThePost implements VotingSystem {

	private int[] sumVotes;
	private int max;
	
	public Results calculate(Ballot[] ballots) {
		searchForMaxIfNull(ballots);
		sumVotes = getSumVotes(ballots);
		int[] returnResults = calculatePositions();
		return new PostitionalResult(returnResults);
	}
	
	private int[] calculatePositions() {
		int[] returnResults = new int[max];
		for(int i=0; i<max;i++){
			int maxIndex=getNextMaxtPositionIndex();
			returnResults[i]=maxIndex+1;
			sumVotes[maxIndex]=0;
		}
		return returnResults;
	}

	private int getNextMaxtPositionIndex() {
		int maxIndex=0;
		int localMax=0;
		for(int j=0; j<max;j++){
			if(localMax<sumVotes[j]){
				localMax=sumVotes[j];
				maxIndex=j;
			}
		}
		return maxIndex;
	}

	private int[] getSumVotes(Ballot[] ballots) {
		sumVotes = new int[this.max];
		for(Ballot iteratingBallot : ballots){
			sumVotes[iteratingBallot.getEntryBoxes()[0]-1]++;
		}
		return sumVotes;
	}

	private void searchForMaxIfNull(Ballot[] ballots) {
		if(max == 0){
			for(Ballot iteratingBallot : ballots){
				if(max<iteratingBallot.getEntryBoxes()[0])
					max=iteratingBallot.getEntryBoxes()[0];
			}
		}
	}

	public FirstPastThePost() {
		super();
	}
	
	public FirstPastThePost(int max) {
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
