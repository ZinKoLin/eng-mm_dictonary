package com.expresstemplate.dictionary;
import java.io.Serializable;

@SuppressWarnings("serial")
public class Word implements Serializable {
    String word;
    String meaning;
    String id;
    String is_fav;

	public String getIs_add() {
		return is_add;
	}

	public void setIs_add(String is_add) {
		this.is_add = is_add;
	}

	String is_add;

	public String getGypsy() {
		return gypsy;
	}

	public void setGypsy(String gypsy) {
		this.gypsy = gypsy;
	}

	String gypsy;

    public String getIs_fav() {
		return is_fav;
	}
	public void setIs_fav(String is_fav) {
		this.is_fav = is_fav;
	}
	public String getMeaning() {
		return meaning;
	}
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWord() {
        return this.word;
    }
    public void setWord(String word) {
        this.word = word;
    }
    
}
