package ehu;

public final class DatosPersona {

	private final String alias;
	private final PersonID id;
	private final Date bornDate;
	private final String city;
	
	public DatosPersona(String alias, PersonID id, Date bornDate, String city) {
		if (alias == null || id == null)
			throw new NullPointerException(alias + " " + id + " " + bornDate + " " + city);
		this.alias = alias;
		this.id = id;
		this.bornDate = bornDate;
		this.city = city;
	}

	public String getAlias() { return alias; }

	public PersonID getId() { return id; }

	public Date getBornDate() { return bornDate; }

	public String getCity() { return city; }
	
	@Override
	public String toString() {
		return "Person[" + alias + " , " + id.getFirstName() + " " + id.getLastName() + " , " + bornDate + " , " + city + "]";
	}
	
	
}
