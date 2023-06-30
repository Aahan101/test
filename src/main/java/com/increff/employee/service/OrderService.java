package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OrderPojo p) throws ApiException {
		//normalize(p);
		dao.insert(p);
	}


	@Transactional(rollbackOn = ApiException.class)
	public OrderPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional
	public OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Order with given ID does not exit, id: " + id);
		}
		return p;
	}
	@Transactional
	public List<OrderPojo> getOrderIdByDateTime(LocalDateTime startDate,LocalDateTime endDate) {
		return dao.selectDateTime(startDate, endDate);
	}

	protected static void normalize(OrderPojo p) {

	}
}
