package ehu;

public final class PersonID implements Comparable<PersonID> {
	
	private final String firstName;
	private final String lastName;
	
	public PersonID(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getFirstName() { return firstName; }

	public String getLastName() { return lastName; }

	@Override
	public String toString() {
		return "ID [" + firstName + " , " + lastName+ "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass())return false;
		PersonID other = (PersonID) obj;
		if (firstName == null) {
			if (other.firstName != null) return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null) return false;
		} else if (!lastName.equals(other.lastName)) return false;
		return true;
	}

	@Override
	public int compareTo(PersonID other) {
		return PersonID.Comparators.NATURAL.compare(this, other);
	}

	public static enum Comparators implements java.util.Comparator<PersonID>{
		NATURAL(true), INVERSE(false);

		private boolean isNat;

		private Comparators(boolean isNatural){this.isNat = isNatural;}

		@Override
		public int compare(PersonID a, PersonID b ) {
			if (!isNat){
				PersonID tmp = a;
				a = b;
				b = tmp;
			}
			int c = a.lastName.compareTo(b.lastName);
			if (c == 0) c = a.firstName.compareTo(b.firstName);
			return c;

		}
	}
	
}




