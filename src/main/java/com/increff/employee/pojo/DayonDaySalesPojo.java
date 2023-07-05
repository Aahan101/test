package com.increff.employee.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class DayonDaySalesPojo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int invoice_orders_count;

	private int invoice_items_count;

	private double total_revenue;

	private LocalDate Date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getInvoice_orders_count() {
		return invoice_orders_count;
	}

	public void setInvoice_orders_count(int invoice_orders_count) {
		this.invoice_orders_count = invoice_orders_count;
	}

	public int getInvoice_items_count() {
		return invoice_items_count;
	}

	public void setInvoice_items_count(int invoice_items_count) {
		this.invoice_items_count = invoice_items_count;
	}

	public double getTotal_revenue() {
		return total_revenue;
	}

	public void setTotal_revenue(double total_revenue) {
		this.total_revenue = total_revenue;
	}

	public LocalDate getDate() {
		return Date;
	}

	public void setDate(LocalDate date) {
		Date = date;
	}



}
