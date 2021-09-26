package com.es.phoneshop.model.historyRecord;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Objects;

public class HistoryRecord implements Serializable {

	private LocalDate date;
	private BigDecimal price;
	private Currency curr;

	public HistoryRecord() {
	}
	
	public HistoryRecord(LocalDate date, BigDecimal price, Currency curr) {
		this.curr = curr;
		this.date = date;
		this.price = price;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Currency getCurr() {
		return curr;
	}

	public void setCurr(Currency curr) {
		this.curr = curr;
	}
	
	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}
		if (o == this) {
			return true;
		}
		if (o.getClass() != this.getClass()) {
			return false;
		}
		HistoryRecord p = (HistoryRecord) o;
		if (p.getCurr().equals(this.getCurr()) && p.getDate().equals(this.getDate())
				&& p.getPrice().equals(this.getPrice())) {
			return true;
		}
		return false;
	}

	public int hashCode() {

		return Objects.hash(this.curr, this.date, this.price);

	}

	public String toString() {

		return this.getClass().toString() + "{ " + "curr=" + this.curr + " | date=" + this.date + " | price="
				+ this.price + " }";

	}

	

}
