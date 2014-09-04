package chiphead.model;

import java.math.BigInteger;

public class WeekNumSet {
	private BigInteger id;
	private Integer year;
	private Integer month;
	private Integer weekNum;
	
	@Override 
	public String toString() {
	return this.id + "#" + this.year + "#" +
		   this.month + "#" + this.weekNum; 
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getWeekNum() {
		return weekNum;
	}

	public void setWeekNum(Integer weekNum) {
		this.weekNum = weekNum;
	}
}
