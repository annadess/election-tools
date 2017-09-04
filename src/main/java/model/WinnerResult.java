package model;

public class WinnerResult extends Results {

	public static final enums.ResultType resultType = enums.ResultType.WINNER_ONLY;
	
	public WinnerResult(int[] places) {
		super(places);
	}
	
	public static final WinnerResult getResultFromFirstPlace(int firstPlace){
		int[] places = new int[1];
		places[0] = firstPlace;
		return new WinnerResult(places);
	}
}
