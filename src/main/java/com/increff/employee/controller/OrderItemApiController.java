package com.increff.employee.controller;

import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class OrderItemApiController {

	@Autowired
	private OrderItemService service;

	@ApiOperation(value = "Adds an orderItem")
	@RequestMapping(path = "/api/createOrder", method = RequestMethod.POST)
	public void add(@RequestBody OrderItemForm form) throws ApiException {
		OrderItemPojo p = convert(form);
		service.add(p);
	}

	
	@ApiOperation(value = "Deletes and orderItem")
	@RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.DELETE)
	// /api/1
	public void delete(@PathVariable int id) {
		service.delete(id);
	}

	@ApiOperation(value = "Gets an orderItem by ID")
	@RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
	public OrderItemData get(@PathVariable int id) throws ApiException {
		OrderItemPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all orderItems")
	@RequestMapping(path = "/api/orderItem", method = RequestMethod.GET)
	public List<OrderItemData> getAll() {
		List<OrderItemPojo> list = service.getAll();
		List<OrderItemData> list2 = new ArrayList<OrderItemData>();
		for (OrderItemPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	@ApiOperation(value = "Updates an orderItem")
	@RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody OrderItemForm f) throws ApiException {
		OrderItemPojo p = convert(f);
		service.update(id, p);
	}
	

	private static OrderItemData convert(OrderItemPojo p) {
		OrderItemData d = new OrderItemData();
		d.setProductId(p.getProductId());
		d.setQuantity(p.getQuantity());
		d.setSellingPrice(p.getSellingPrice());
		d.setOrderId(p.getOrderId());
		d.setId(p.getId());
		return d;
	}

	private static OrderItemPojo convert(OrderItemForm f) {
		OrderItemPojo p = new OrderItemPojo();
		p.setProductId(f.getProductId());
		p.setQuantity(f.getQuantity());
		p.setSellingPrice(f.getSellingPrice());
		return p;
	}

}
