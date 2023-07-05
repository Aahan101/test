package com.increff.employee.service;

import com.increff.employee.dao.DayonDaySalesDao;
import com.increff.employee.dao.OrderDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.DayonDaySalesData;
import com.increff.employee.model.SalesData;
import com.increff.employee.pojo.*;
import com.increff.employee.pojo.DayonDaySalesPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DayonDaySalesService {


	@Autowired
	private DayonDaySalesDao dao;
	@Autowired
	private OrderItemService service;
	@Autowired
	private DayonDaySalesService dayonDaySalesService;
	@Autowired
	private DayonDaySalesDao dayonDaySalesDao;
	@Autowired
	private OrderDao orderDao;

	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private ProductDao productDao;

	@Autowired
	private OrderService orderService;


	@Transactional
	public List<DayonDaySalesPojo> getAll() {
		return dao.selectAll();
	}



	@Transactional
	public void getRelevantAll() throws ApiException{
		LocalDate localDate=LocalDate.now();
		DayonDaySalesPojo d = new DayonDaySalesPojo();
		LocalDateTime startDate = localDate.atStartOfDay();
		LocalDateTime endDate = localDate.atTime(LocalTime.MAX);
		List<OrderPojo> list1 = orderService.getRelevantData(startDate,endDate);
		List<OrderItemPojo> orderList = new ArrayList<>();
		for(OrderPojo o:list1){
			OrderItemPojo p= orderItemDao.select_orderID(o.getId());
			orderList.add(p);
		}
		int quantity=0;
		double revenue=0.0;
		for (OrderItemPojo p:orderList){
			quantity+=(p.getQuantity());
			revenue+=(p.getQuantity()*p.getSellingPrice());
		}
		d.setInvoice_orders_count(orderList.size());
		d.setInvoice_items_count(quantity);
		d.setTotal_revenue(revenue);
		d.setDate(localDate);
		dao.insert(d);
	}
}
