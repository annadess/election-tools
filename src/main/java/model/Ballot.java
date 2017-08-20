package model;

public abstract class Ballot {

	final private int[] entryBoxes;

	public Ballot(int[] entryBoxes){
		this.entryBoxes = entryBoxes;
	}
	
	public int[] getEntryBoxes() {
		return entryBoxes;
	}
	
}
