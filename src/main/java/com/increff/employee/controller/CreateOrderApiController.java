package com.increff.employee.controller;

import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.CreateOrderData;
import com.increff.employee.model.CreateOrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class CreateOrderApiController {

	@Autowired
	private OrderItemService service;

	@Autowired
	private ProductDao productDao;


	@ApiOperation(value = "Adds an createOrder")
	@RequestMapping(path = "/api/createOrder", method = RequestMethod.POST)
	public void add(@RequestBody CreateOrderForm form) throws ApiException {
	OrderItemPojo p = convert2(form);
	//service.add(p);
	}

//	@ApiOperation(value = "Adds an createOrder")
//	@RequestMapping(path = "/api/orderItem", method = RequestMethod.POST)
//	public void add4(@RequestBody CreateOrderForm form) throws ApiException {
//		service.add3();
//	}


//	@ApiOperation(value = "Deletes and createOrder")
//	@RequestMapping(path = "/api/createOrder/{id}", method = RequestMethod.DELETE)
//	// /api/1
//	public void delete(@PathVariable int id) {
//		service.delete(id);
//	}

//	@ApiOperation(value = "Gets an createOrder by ID")
//	@RequestMapping(path = "/api/createOrder/{id}", method = RequestMethod.GET)
//	public OrderItemData get(@PathVariable int id) throws ApiException {
//		OrderItemPojo p = service.get(id);
//		return convert(p);
//	}

	@ApiOperation(value = "Gets list of all createOrders")
	@RequestMapping(path = "/api/createOrder", method = RequestMethod.GET)
	public List<CreateOrderData> getAll() {
//		List<OrderItemPojo> list = service.getAll();
		List<CreateOrderData> list2 = new ArrayList<CreateOrderData>();
//		for (OrderItemPojo p : list) {
//			list2.add(convert(p));
//		}
		return list2;
	}

	@ApiOperation(value = "Updates an createOrder")
	@RequestMapping(path = "/api/createOrder", method = RequestMethod.PUT)
	public void update( @RequestBody CreateOrderForm f) throws ApiException {
		OrderItemPojo p = convert2(f);
		//service.update(p);
	}
//	@ApiOperation(value = "Updates an createOrder")
//	@RequestMapping(path = "/api/createOrder/{id}", method = RequestMethod.PUT)
//	public void update(@PathVariable int id,@RequestBody CreateOrderForm form) throws ApiException {
//		OrderItemPojo p = convert2(form);
//		service.update2(id, p,form);
//	}


//	private static CreateOrderData convert(OrderItemPojo p) {
//		CreateOrderData d = new CreateOrderData();
//		d.setQuantity(p.getQuantity());
//		d.setSellingPrice(p.getSellingPrice());
//		d.setId(p.getId());
//		return d;
//	}

	private OrderItemPojo convert2(CreateOrderForm f) throws ApiException {
		OrderItemPojo p = new OrderItemPojo();
		p.setProductId(service.checkBarcode(f.getBarcode()));
		p.setQuantity(service.checkQuantity(p.getProductId(),f.getQuantity()));
		p.setSellingPrice(service.checkMrp(f.getSellingPrice()));
		return p;
	}


	private OrderItemPojo convert(CreateOrderForm f) {
		OrderItemPojo p = new OrderItemPojo();
		p.setQuantity(f.getQuantity());
		p.setSellingPrice(f.getSellingPrice());
		p.setProductId(productDao.checkBarcode(f.getBarcode()).getId());
		return p;
	}

}
