//A. Stewart-
//G. Watson-
//Chevis Hutchinson -1601446


package main;

public class Data { 
	private String word;
	private String definition;
	private String partOfSpeech;
	private int foundIndex; 
	
	public Data() { 
		this.foundIndex = 0; 
		this.word = this.definition = this.partOfSpeech = ""; 
	}
	
	public Data(String word, String definition, String partOfSpeech) { 
		this.word = word;
		this.definition = definition;
		this.partOfSpeech = partOfSpeech;
	}
	
	// Accessors
	public final int getFoundIndex() {
		return this.foundIndex;
	}
	
	public final String getWord() {
		return this.word;
	}
	
	public final String getDefinition() {
		return this.definition;
	}
	
	public final String getPartOfSpeech() {
		return this.partOfSpeech;
	}
	
	//  Mutators
	public void setFoundIndex(int foundIndex) {
		this.foundIndex = foundIndex;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
}