package model;

public class Marque {
	private int marqueId;
	private String marqueNom;
	private String marqueDescription;
	private boolean checked;

	public int getMarqueId() {
		return marqueId;
	}

	public void setMarqueId(int marqueId) {
		this.marqueId = marqueId;
	}

	public String getMarqueNom() {
		return marqueNom;
	}

	public void setMarqueNom(String nom) {
		this.marqueNom = nom;
	}

	public String getMarqueDescription() {
		return marqueDescription;
	}

	public void setMarqueDescription(String marqueDescription) {
		this.marqueDescription = marqueDescription;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "Marque [marqueId=" + marqueId + ", marqueNom=" + marqueNom + ", marqueDescription=" + marqueDescription
				+ ", checked=" + checked + "]";
	}

	
	
	
}