package and.db.motus;

public class Mot {
	int _id;
	String _lettre;
	int _alphabetid;
	
	/**
	 * 
	 */
	public Mot() {
		
	}

	/**
	 * @param _id
	 * @param _lettre
	 * @param _alphabetid
	 */
	public Mot(int _id, String _lettre, int _alphabetid) {
		super();
		this._id = _id;
		this._lettre = _lettre;
		this._alphabetid = _alphabetid;
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the _lettre
	 */
	public String get_lettre() {
		return _lettre;
	}

	/**
	 * @param _lettre the _lettre to set
	 */
	public void set_lettre(String _lettre) {
		this._lettre = _lettre;
	}

	/**
	 * @return the _alphabetid
	 */
	public int get_alphabetid() {
		return _alphabetid;
	}

	/**
	 * @param _alphabetid the _alphabetid to set
	 */
	public void set_alphabetid(int _alphabetid) {
		this._alphabetid = _alphabetid;
	}
	
	
}
