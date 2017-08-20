package functional;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import model.Ballot;
import model.SimpleBallot;

public class BallotGenerator {
	
	private static Random randGen;
	private static int[][] tempBallotArray;
	
	public static Ballot[] generate(int numOfBallots, byte maxRanks, int numOfChoices){
		checkForIllegalArguments(numOfBallots,maxRanks, numOfChoices);
		tempBallotArray = new int[numOfBallots][maxRanks];
		generateTempBallotArray(numOfBallots,maxRanks,numOfChoices);
		return returnBallotArray(numOfBallots);
	}

	private static void generateTempBallotArray(int numOfBallots, int maxRanks, int numOfChoices) {
		for(int y=0;y<maxRanks;y++){
			for(int x=0;x<numOfBallots;x++){
				randomizeCurrentCell(x,y,numOfChoices);
			}
		}
	}

	private static void randomizeCurrentCell(int x, int y, int numOfChoices) {
		randGen = ThreadLocalRandom.current();
		if(y<1){	
			tempBallotArray[x][y] = randGen.nextInt(numOfChoices)+1;
		}
		else{
			randomizeEmptyOrDeepCell(x,y,numOfChoices);
		}
	}

	private static void randomizeEmptyOrDeepCell(int x, int y, int numOfChoices) {
		if(tempBallotArray[x][y-1]==0){
			tempBallotArray[x][y] = 0;
		}
		else{
			tempBallotArray[x][y] = randGen.nextInt(numOfChoices+1);
			while(checkForDuplicates(x,y)){
				tempBallotArray[x][y] = randGen.nextInt(numOfChoices+1);
			}	
		}
	}
	
	private static Ballot[] returnBallotArray(int numOfBallots) {
		Ballot[] returnBallots = new Ballot[numOfBallots];
		for(int i=0;i<numOfBallots;i++){
			returnBallots[i]= new SimpleBallot(tempBallotArray[i]);
		}
		return returnBallots;
	}

	private static boolean checkForDuplicates(int x,int y) {
		for(int i=0; i<y; i++){
			if(tempBallotArray[x][y] == tempBallotArray[x][i]){
				return true;
			}
		}
		return false;
	}
	
	private static void checkForIllegalArguments(int numOfBallots, byte maxRanks, int numOfChoices) {
		if(numOfBallots <1 || maxRanks<1 || numOfChoices<1){
			throw new IllegalArgumentException();
		}
	}
	
	public static void main(String[] args){
		Ballot[] testBallots = generate(100000,(byte)3,30);
		for(Ballot ballot : testBallots){
			for(int box : ballot.getEntryBoxes()){
				System.out.print(box+" ");
			}
			System.out.println();
		}
		System.out.println("Positioning, Alternative Vote");
		for(int pos: new AlternativeVote().calculate(testBallots).getPlaces()){
			System.out.println(pos);
		}
		System.out.println("Positioning, First Past the Post");
		for(int pos: new FirstPastThePost().calculate(testBallots).getPlaces()){
			System.out.println(pos);
		}
	}
}
