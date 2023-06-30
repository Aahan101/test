package com.increff.employee.controller;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class OrderApiController {

	@Autowired
	private OrderService service;

	@Autowired
	private ProductDao productDao;


	@ApiOperation(value = "Adds an order")
	@RequestMapping(path = "/api/order", method = RequestMethod.POST)
	public int add() throws ApiException {
	OrderPojo p = new OrderPojo();
	service.add(p);
	return p.getId();
	}

	@ApiOperation(value = "Gets list of all orders")
	@RequestMapping(path = "/api/order", method = RequestMethod.GET)
	public List<OrderData> getAll() {
		List<OrderPojo> list = service.getAll();
		List<OrderData> list2 = new ArrayList<OrderData>();
		for (OrderPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	private static OrderData convert(OrderPojo p) {
		OrderData d = new OrderData();
		d.setTime(p.getTime());
		d.setId(p.getId());
		return d;
	}
	private static OrderPojo convert(OrderForm f) {
		OrderPojo p = new OrderPojo();
		p.setTime(f.getTime());
		return p;
	}

}
