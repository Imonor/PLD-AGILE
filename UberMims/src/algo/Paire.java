package algo;

public class Paire {
	
	private boolean dispo;
	private boolean vu;
	
	public Paire(boolean dispo, boolean vu) {
		this.dispo = dispo;
		this.vu = vu;
	}

	public boolean getDispo() {
		return dispo;
	}

	public void setDispo(boolean dispo) {
		this.dispo = dispo;
	}

	public boolean getVu() {
		return vu;
	}

	public void setVu(boolean vu) {
		this.vu = vu;
	}
	
}
