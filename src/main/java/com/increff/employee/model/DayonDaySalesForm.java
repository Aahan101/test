package com.increff.employee.model;

import java.time.LocalDate;

public class DayonDaySalesForm {


	private int invoice_orders_count;

	private int invoice_items_count;

	private double total_revenue;

	private LocalDate Date;

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
