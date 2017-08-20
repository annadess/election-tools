package model;

public abstract class Results {

	final private int[] placements;

	public Results(int[] places) {
		super();
		this.placements = places;
	}

	public int[] getPlaces() {
		return placements;
	}
	
}
