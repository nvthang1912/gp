package com.linkin.model;

import java.io.Serializable;
import java.util.List;

public class FamilyTreeDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private PersonDTO text;
	private String image;
	private boolean stackChildren = false;
	private List<FamilyTreeDTO> children;

	public PersonDTO getText() {
		return text;
	}

	public boolean isStackChildren() {
		return stackChildren;
	}

	public void setStackChildren(boolean stackChildren) {
		this.stackChildren = stackChildren;
	}

	public void setText(PersonDTO text) {
		this.text = text;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<FamilyTreeDTO> getChildren() {
		return children;
	}

	public void setChildren(List<FamilyTreeDTO> children) {
		this.children = children;
	}

}
