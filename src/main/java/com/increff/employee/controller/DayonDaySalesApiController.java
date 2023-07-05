package com.increff.employee.controller;

import com.increff.employee.model.DayonDaySalesData;
import com.increff.employee.model.DayonDaySalesForm;
import com.increff.employee.pojo.DayonDaySalesPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.DayonDaySalesService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class DayonDaySalesApiController {

	@Autowired
	private DayonDaySalesService service;


	@ApiOperation(value = "Gets list of all dayonDaySaless")
	@RequestMapping(path = "/api/report/dayonDaySales", method = RequestMethod.GET)
	public List<DayonDaySalesData> getAll() {
		List<DayonDaySalesPojo> list = service.getAll();
		List<DayonDaySalesData> list2 = new ArrayList<DayonDaySalesData>();
		for (DayonDaySalesPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}


	private static DayonDaySalesData convert(DayonDaySalesPojo p) {
		DayonDaySalesData d = new DayonDaySalesData();
		d.setInvoice_orders_count(p.getInvoice_orders_count());
		d.setInvoice_items_count(p.getInvoice_items_count());
		d.setTotal_revenue(p.getTotal_revenue());
		d.setDate(p.getDate());
		d.setId(p.getId());
		return d;
	}

}
