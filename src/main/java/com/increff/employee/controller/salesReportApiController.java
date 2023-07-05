package com.increff.employee.controller;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.model.SalesData;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class salesReportApiController {

	@Autowired
	private OrderItemService service;
	@Autowired
	private BrandService brandService;
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private ProductDao productDao;

	@Autowired
	private OrderService orderService;

	int findBrandCategory(List<SalesData> list,SalesData s){
		for(SalesData e: list){
			System.out.println("List Item:");
			String a,b,c,d;
			a=e.getBrand();
			b=s.getBrand();
			c=e.getCategory();
			d=s.getCategory();
			System.out.println(e.getId() + " " + a + " " + c);
			System.out.println(s.getId() + " " + b + " " + d);
			if(a.equals(b) && c.equals(d)){// && (e.getCategory()==s.getCategory())) {
				System.out.println("found");
				System.out.println(e.getId());
				return list.indexOf(e);
			}
		}return -1;
	}
	@ApiOperation(value = "Gets list of all orders")
	@RequestMapping(path = "/api/report/salesReport/new", method = RequestMethod.GET)
	public List<SalesData> getRelevantAll(LocalDate start_Date, LocalDate end_Date)  {
		List<SalesData> list = new ArrayList<>();
		LocalDateTime startDate = start_Date.atStartOfDay();
		LocalDateTime endDate = end_Date.atTime(LocalTime.MAX);
		List<OrderPojo> list1 = orderService.getRelevantData(startDate,endDate);
		List<OrderItemPojo> orderList=new ArrayList<>();
		for(OrderPojo o:list1){
			OrderItemPojo p= orderItemDao.select_orderID(o.getId());
			orderList.add(p);
		}
		for (OrderItemPojo p : orderList) {
			SalesData s= convert(p);
			int i=findBrandCategory(list,s);
			if(i==-1) {
				list.add(convert(p));
			}
			else{
				list.set(i,convert3(list.get(i),p));
			}
		}
		return list;
	}
	@ApiOperation(value = "Gets list of all orders")
	@RequestMapping(path = "/api/report/salesReport", method = RequestMethod.GET)
	public List<SalesData> getAll() {
		List<OrderItemPojo> orderList = service.getAll();
		List<SalesData> list = new ArrayList<SalesData>();
//		for (BrandPojo p : brandList) {
//			list.add(convert2(p));
//		}
		for (OrderItemPojo p : orderList) {
			SalesData s= convert(p);
			int i=findBrandCategory(list,s);
			if(i==-1) {
				list.add(convert(p));
			}
			else{
				list.set(i,convert3(list.get(i),p));
			}
		}
		return list;
	}

	@ApiOperation(value = "Add date time")
	@RequestMapping(path = "/api/report/salesReport", method = RequestMethod.POST)
	public List<SalesData> add(@RequestBody SalesData form) throws ApiException {
		LocalDate startDate= form.getStartDate();
		LocalDate endDate= form.getEndDate();
		System.out.printf("startDate "+ startDate);
		System.out.printf("endDate "+ endDate);
		List<SalesData> d = getRelevantAll(startDate,endDate);
		return d;
	}

	private SalesData convert(OrderItemPojo p) {
		SalesData d = new SalesData();
		int brand_category=productDao.select(p.getProductId()).getBrand_category();
		d.setId(p.getId());
		d.setDate((orderDao.select(p.getOrderId()).getTime()).toLocalDate());
		d.setBrand(brandDao.select(brand_category).getBrand());
		d.setCategory(brandDao.select(brand_category).getCategory());
		d.setQuantity(p.getQuantity());
		d.setRevenue(p.getSellingPrice()*p.getQuantity());
		return d;
	}
	private SalesData convert3(SalesData d,OrderItemPojo p) {
		d.setQuantity(d.getQuantity()+p.getQuantity());
		d.setRevenue(d.getRevenue()+(p.getSellingPrice()*p.getQuantity()));
		return d;
	}


}
