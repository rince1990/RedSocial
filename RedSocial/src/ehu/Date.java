package ehu;

public final class Date implements Comparable<Date> {
	
	
// farfetch	
	
	public static final int MIN_YEAR = 1900;
	public static final int MAX_YEAR = 2100;

	private final int day;
	private final int month;
	private final int year;
	private final int hashCode;
	
	public Date(int day, int month, int year) {
		checkRange(day, 1, 31);
		checkRange(month, 1, 12);
		checkRange(year, MIN_YEAR, MAX_YEAR);
		this.day = day;
		this.month = month;
		this.year = year;
		this.hashCode = (year - MIN_YEAR + 1) * 10000 + month * 100 + day;
	}

	private void checkRange (int v, int min, int max) {
		if (v < min||v > max)
			throw new IllegalArgumentException (v + " not in " + min + " .. " + max);
	}

	public int getDay() { return day; }

	public int getMonth() { return month; }

	public int getYear() { return year; }

	@Override
	public String toString() {
		return "Date [" + day + "/" + month + "/" + year + "]";
	}

	@Override
	public int hashCode() { return hashCode; }

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Date other = (Date) obj;
		if (day != other.day) return false;
		if (month != other.month) return false;
		if (year != other.year)	return false;
		return true;
	}

	@Override
	public int compareTo(Date other) {
		return Date.Comparators.NATURAL.compare(this, other);
	}

	public static enum Comparators implements java.util.Comparator<Date> {
		NATURAL(true), INVERSE(false);

		private boolean isNat;

		private Comparators (boolean isNatural) { this.isNat = isNatural; }

		@Override
		public int compare(Date a, Date b){
			return (isNat? a:b).hashCode - (isNat? b: a).hashCode;

		}
	}

}
