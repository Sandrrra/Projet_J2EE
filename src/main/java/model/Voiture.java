package model;

public class Voiture {
	private int voitureId;
	private String voitureNom;
	private String voitureDescription;
	private double prix;
	private java.sql.Date miseAuMarche;
	private String pays;
	private String carburant;
	private int portes;
	private int modeleId;
	private String modeleNom;
	private int marqueId;
	private String marqueNom;
	
	public int getVoitureId() {
		return voitureId;
	}

	public void setVoitureId(int voitureId) {
		this.voitureId = voitureId;
	}

	public String getVoitureNom() {
		return voitureNom;
	}

	public void setVoitureNom(String voitureNom) {
		this.voitureNom = voitureNom;
	}

	public String getVoitureDescription() {
		return voitureDescription;
	}

	public void setVoitureDescription(String voitureDescription) {
		this.voitureDescription = voitureDescription;
	}

	public double getPrix() {
		return prix;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public java.sql.Date getMiseAuMarche() {
		return miseAuMarche;
	}

	public void setMiseAuMarche(java.sql.Date miseAuMarche) {
		this.miseAuMarche = miseAuMarche;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getCarburant() {
		return carburant;
	}

	public void setCarburant(String carburant) {
		this.carburant = carburant;
	}

	public int getPortes() {
		return portes;
	}

	public void setPortes(int portes) {
		this.portes = portes;
	}

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

	public String getMarqueNom() {
		return marqueNom;
	}

	public void setMarqueNom(String marqueNom) {
		this.marqueNom = marqueNom;
	}

	public int getMarqueId() {
		return marqueId;
	}

	public void setMarqueId(int marqueId) {
		this.marqueId = marqueId;
	}

	@Override
	public String toString() {
		return "Voiture [voitureId=" + voitureId + ", voitureNom=" + voitureNom + ", voitureDescription="
				+ voitureDescription + ", prix=" + prix + ", miseAuMarche=" + miseAuMarche + ", pays=" + pays
				+ ", carburant=" + carburant + ", portes=" + portes + ", modeleId=" + modeleId + ", modeleNom="
				+ modeleNom + ", marqueId=" + marqueId + ", marqueNom=" + marqueNom + "]";
	}


	
	
}