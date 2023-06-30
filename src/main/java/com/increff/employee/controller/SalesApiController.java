package com.increff.employee.controller;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.model.SalesData;
import com.increff.employee.model.SalesForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class SalesApiController {

	@Autowired
	private OrderItemService service;

	@Autowired
	private OrderDao orderDao;

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private BrandService brandService;

	@Autowired
	private InventoryDao inventoryDao;


	@Autowired
	private ProductService productService;
//
//	@ApiOperation(value = "Adds an orderItem")
//	@RequestMapping(path = "/api/orderItem", method = RequestMethod.POST)
//	public void add(@RequestBody SalesForm form) throws ApiException {
//		OrderItemPojo p = convert(form);
//		service.add(p);
//	}
//
//
//	@ApiOperation(value = "Deletes and orderItem")
//	@RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.DELETE)
//	// /api/1
//	public void delete(@PathVariable int id) {
//		service.delete(id);
//	}

//	@ApiOperation(value = "Gets an orderItem by ID")
//	@RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.GET)
//	public SalesData get(@PathVariable int id) throws ApiException {
//		OrderItemPojo p = service.get(id);
//		return convert(p);
//	}

	@ApiOperation(value = "Gets list of all orderItems")
	@RequestMapping(path = "/api/report/salesReport", method = RequestMethod.GET)
	public List<SalesData> getAll() throws ApiException {
		List<OrderItemPojo> list = service.getAll();
		List<SalesData> list2 = new ArrayList<SalesData>();
		for (OrderItemPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

//	@ApiOperation(value = "Updates an orderItem")
//	@RequestMapping(path = "/api/orderItem/{id}", method = RequestMethod.PUT)
//	public void update(@PathVariable int id, @RequestBody SalesForm f) throws ApiException {
//		OrderItemPojo p = convert(f);
//		service.update(id, p);
//	}
	

	private SalesData convert(OrderItemPojo p) throws ApiException {
		SalesData d = new SalesData();
		d.setDate(orderDao.select(p.getOrderId()).getTime().toLocalDate());
		int brand_category=productService.get(p.getProductId()).getBrand_category();
		d.setBrand(brandService.getCheck(brand_category).getBrand());
		d.setCategory(brandService.getCheck(brand_category).getCategory());
		d.setQuantity(inventoryDao.select(p.getProductId()).getQuantity());
		d.setId(p.getId());
		return d;
	}


}
