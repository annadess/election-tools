package functional;

import java.util.Arrays;

import model.Ballot;
import model.Results;
import model.VotingSystem;
import model.WinnerResult;

public class AlternativeVote implements VotingSystem {

	private int[] sumVotes;
	private int firstPlace;
	
	public Results calculate(Ballot[] ballots) {
		
		int max =0;
		for(Ballot iteratingBallot : ballots){
			if(max<iteratingBallot.getEntryBoxes()[0])
				max=iteratingBallot.getEntryBoxes()[0];
		}
		
		sumVotes = new int[max];
		
		for(Ballot iteratingBallot : ballots){
			sumVotes[iteratingBallot.getEntryBoxes()[0]-1]++;
		}
		
		int[][] ballots2D = new int[ballots.length][ballots[0].getEntryBoxes().length];
		for(int i=0;i<ballots2D.length;i++){
			ballots2D[i] = ballots[i].getEntryBoxes().clone();
		}
		
		while(true){

			//System.out.println(Arrays.deepToString(ballots2D));
			System.out.println(Arrays.toString(sumVotes));
			
			int numOfCandidates = 0;
			int totalSumOfVotes =0;
			for(int iterateSum : sumVotes){
				totalSumOfVotes+=iterateSum;
				if(iterateSum!=0){
					numOfCandidates++;
				}
			}
			//System.out.println(numOfCandidates + " " + totalSumOfVotes);
			if(isSomeoneOver50Percent(totalSumOfVotes)){
				int[] returnPlaces = new int[1];
				returnPlaces[0] = firstPlace;
				return new WinnerResult(returnPlaces);
			}else if(numOfCandidates==1){
				for(int i=0;i<sumVotes.length;i++){
					if(sumVotes[i]!=0){
						firstPlace=i+1;
						int[] returnPlaces = new int[1];
						returnPlaces[0] = firstPlace;
						return new WinnerResult(returnPlaces);
					}
				}
			}else{
				int min=totalSumOfVotes;
				int minIndex=0;
				for(int i=0;i<sumVotes.length;i++){
					if(min>sumVotes[i] && sumVotes[i]!=0){
						min=sumVotes[i];
						minIndex=i+1;
					}
				}
				//System.out.println("Mins:"+min+" "+minIndex);
				for(int x=0;x<ballots2D.length;x++){
					for(int y=ballots2D[0].length-1;y>-1;y--){
						if(ballots2D[x][y]==minIndex && y+1<ballots2D[0].length){
							ballots2D[x][y]=ballots2D[x][y+1];
						}else if(ballots2D[x][y]==minIndex){
							ballots2D[x][y]=0;
						}
					}
				}
				
				sumVotes = new int[max];
				
				for(int[] iteratingBallot : ballots2D){
					if(iteratingBallot[0]-1>-1){
						sumVotes[iteratingBallot[0]-1]++;
					}
				}
			}
		}
	}

	private boolean isSomeoneOver50Percent(int totalSumOfVotes) {
		for(int i=0;i<sumVotes.length;i++){
			if(sumVotes[i]>totalSumOfVotes/2){
				//System.out.println(sumVotes[i] + " " + totalSumOfVotes/2);
				firstPlace = i+1;
				return true;
			}
		}
		return false;
	}

}
