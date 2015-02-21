package and.db.motus;

public class Alphabet {
	int _id;
	String _lettre;
	
	/**
	 * 
	 */
	public Alphabet() {

	}

	/**
	 * @param _id
	 * @param _lettre
	 */
	public Alphabet(int _id, String _lettre) {
		super();
		this._id = _id;
		this._lettre = _lettre;
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
	
	
}
