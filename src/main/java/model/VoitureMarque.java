package model;


public class VoitureMarque {
	private int voitureId;
	private int marqueId;

	public int getVoitureId() {
		return voitureId;
	}

	public void setVoitureId(int voitureId) {
		this.voitureId = voitureId;
	}

	public int getMarqueId() {
		return marqueId;
	}

	public void setMarqueId(int marqueId) {
		this.marqueId = marqueId;
	}

	@Override
	public String toString() {
		return "VoitureMarque [voitureId=" + voitureId + ", marqueId=" + marqueId + "]";
	}

	

	

}