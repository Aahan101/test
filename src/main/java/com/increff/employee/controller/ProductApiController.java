package com.increff.employee.controller;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class ProductApiController {

	@Autowired
	private ProductService service;
	@Autowired
	private BrandDao brandDao;

	@ApiOperation(value = "Adds a product")
	@RequestMapping(path = "/api/product", method = RequestMethod.POST)
	public void add(@RequestBody ProductForm  form) throws ApiException {
		ProductPojo p = convert(form);
		System.out.println(p);
		service.addProd(p,form);
	}


	@ApiOperation(value = "Gets a product by ID")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.GET)
	public ProductData get(@PathVariable int id) throws ApiException {
		ProductPojo p = service.get(id);
		return convert(p);
	}

	@ApiOperation(value = "Gets list of all products")
	@RequestMapping(path = "/api/product", method = RequestMethod.GET)
	public List<ProductData> getAll() throws ApiException {
		List<ProductPojo> list = service.getAll();
		List<ProductData> list2 = new ArrayList<ProductData>();
		for (ProductPojo p : list) {
			list2.add(convert(p));
		}
		return list2;
	}

	@ApiOperation(value = "Updates an product")
	@RequestMapping(path = "/api/product/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
		ProductPojo p = convert(f);
		service.updateProd(id, p,f);
	}
	

	private ProductData convert(ProductPojo p) {
		ProductData d = new ProductData();
		d.setBarcode(p.getBarcode());
		d.setBrand_category(p.getBrand_category());
//		if(service.getValueBrandCategory(p.getBrand_category()).getBrand()==null){
//			throw new ApiException("Brand cannot be empty");
//		}
//		else{
//			d.setBrand(service.getValueBrandCategory(p.getBrand_category()).getBrand());
//		}
//		if(service.getValueBrandCategory(p.getBrand_category()).getCategory()==null){
//			throw new ApiException("Category cannot be empty");
//		}
//		else{
//			d.setCategory(service.getValueBrandCategory(p.getBrand_category()).getCategory());
//		}
		d.setBrand(service.getValueBrandCategory(p.getBrand_category()).getBrand());
		d.setCategory(service.getValueBrandCategory(p.getBrand_category()).getCategory());
		d.setMrp(p.getMrp());
		d.setName(p.getName());
		d.setId(p.getId());
		return d;
	}

	private ProductPojo convert(ProductForm f) throws ApiException {
		ProductPojo p = new ProductPojo();
		p.setBarcode(f.getBarcode());
		p.setName(f.getName());
		p.setMrp(f.getMrp());
		if(brandDao.checkCombination(f.getBrand(),f.getCategory())!=null) {
			p.setBrand_category(brandDao.checkCombination(f.getBrand(),f.getCategory()).getId());
		}else{
			p.setBrand_category(0);
		}
		return p;
	}
}
