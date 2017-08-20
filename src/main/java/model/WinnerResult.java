package model;

public class WinnerResult extends Results {

	public static final enums.ResultType resultType = enums.ResultType.WINNER_ONLY;
	
	public WinnerResult(int[] places) {
		super(places);
	}
	
}
