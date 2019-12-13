package algo;

/**
 * Classe qui stocke les etats vu et dispo
 */
public class Paire {
	
	private boolean dispo;
	private boolean vu;
	
	/**
	 * Constructeur
	 * @param dispo - boolean
	 * @param vu - boolean
	 */
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
