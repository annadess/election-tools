package model;

public class PostitionalResult extends Results {

	public static final enums.ResultType resultType = enums.ResultType.POSITION_ONLY;
	
	public PostitionalResult(int[] places) {
		super(places);
	}
	
}
