package com.increff.employee.controller;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.model.ProductData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class InventoryApiController {

	@Autowired
	private InventoryService service;

	@ApiOperation(value = "Gets an inventory by ID")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) throws ApiException {
		InventoryPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all inventory")
	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		List<InventoryPojo> list = service.getAll();
		List<InventoryData> list2 = new ArrayList<InventoryData>();
		for (InventoryPojo p : list) {
			list2.add(convert(p));
		}

		return list2;
	}
//	@ApiOperation(value = "Gets list of all inventory")
//	@RequestMapping(path = "/api/inventory", method = RequestMethod.GET)
//	public List<ProductData> getBarcode() {
//		List<ProductPojo> list = service.getBarcode();
//		List<ProductData> list2 = new ArrayList<ProductData>();
//		for (ProductPojo p : list) {
//			list2.add(convert(p));
//		}
//		return list2;
//	}
//
//	private ProductData convert(ProductPojo p) {
//		ProductData d = new ProductData();
//		d.setBarcode(p.getBarcode());
//		d.setBrand_category(p.getBrand_category());
//		d.setMrp(p.getMrp());
//		d.setName(p.getName());
//		d.setId(p.getId());
//		return d;
//	}

	@ApiOperation(value = "Updates an inventory")
	@RequestMapping(path = "/api/inventory/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
		InventoryPojo p = convert(f);
		service.update(id, p);
	}
	

	private InventoryData convert(InventoryPojo p) {
		InventoryData d = new InventoryData();
		d.setBarcode(service.getBarcode(p.getId()));
		d.setQuantity(p.getQuantity());
		d.setId(p.getId());
		return d;
	}
	private static InventoryData convertForm(InventoryPojo p,ProductPojo pp) {
		InventoryData d = new InventoryData();
		d.setQuantity(p.getQuantity());
		d.setId(p.getId());
		d.setBarcode(pp.getBarcode());
		return d;
	}

	private static InventoryPojo convert(InventoryForm f) {
		InventoryPojo p = new InventoryPojo();
		p.setQuantity(f.getQuantity());
		return p;
	}

}
