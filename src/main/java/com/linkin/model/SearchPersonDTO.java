package com.linkin.model;

public class SearchPersonDTO extends SearchDTO {
	private static final long serialVersionUID = 1L;
	private boolean isFounder = false;

	public boolean isFounder() {
		return isFounder;
	}

	public void setFounder(boolean isFounder) {
		this.isFounder = isFounder;
	}

}
