package model;

public class Modele {
	private int modeleId;
	private String modeleNom;
	private String modeleDescription;

	public int getModeleId() {
		return modeleId;
	}

	public void setModeleId(int modeleId) {
		this.modeleId = modeleId;
	}

	public String getModeleNom() {
		return modeleNom;
	}

	public void setModeleNom(String modeleNom) {
		this.modeleNom = modeleNom;
	}

	public String getModeleDescription() {
		return modeleDescription;
	}

	public void setModeleDescription(String modeleDescription) {
		this.modeleDescription = modeleDescription;
	}

	@Override
	public String toString() {
		return "Modele [modeleId=" + modeleId + ", modeleNom=" + modeleNom + ", modeleDescription=" + modeleDescription
				+ "]";
	}


	

}