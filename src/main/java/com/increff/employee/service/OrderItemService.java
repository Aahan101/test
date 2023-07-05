package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.CreateOrderData;
import com.increff.employee.model.CreateOrderForm;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private InventoryDao inventoryDao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OrderItemPojo p) throws ApiException {
//		normalize(p);
//		if(StringUtil.isEmpty(p.getName())) {
//			throw new ApiException("name cannot be empty");
//		}
		dao.insert(p);
	}
//	List<OrderItemPojo> temp = new ArrayList<OrderItemPojo>();
//	@Transactional(rollbackOn = ApiException.class)
//	public void add2(OrderItemPojo p) throws ApiException{
//		temp.add(p);
//	}
//	@Transactional(rollbackOn = ApiException.class)
//	public void add3() throws ApiException{
//		for(OrderItemPojo p:temp){
//			dao.insert(p);
//		}
//		temp=null;
//	}


	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<OrderItemPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, OrderItemPojo p) throws ApiException {
//		normalize(p);
		OrderItemPojo ex = getCheck(id);
		ex.setOrderId(p.getOrderId());
		ex.setProductId(p.getProductId());
		ex.setQuantity(p.getQuantity());
		ex.setSellingPrice(p.getSellingPrice());
		dao.update(ex);
	}


	@Transactional
	public OrderItemPojo getCheck(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("OrderItem with given ID does not exit, id: " + id);
		}
		return p;
	}

	@Transactional
	public int checkBarcode(String barcode) throws ApiException {
		ProductPojo p = productDao.checkBarcode(barcode);
		if (p == null) {
			throw new ApiException("Barcode does not exist ");
		}
		return p.getId();
	}

	@Transactional
	public int checkQuantity(int id,int quantity) throws ApiException {
		InventoryPojo p=inventoryDao.select(id);
		if (p.getQuantity()<quantity) {
			throw new ApiException("Quantity of product does not exist in Inventory");
		}
		return quantity;
	}

	@Transactional
	public double checkMrp(double mrp) throws ApiException{
		if(mrp<0){
			throw new ApiException("Selling Price can not be negative");
		}
		return mrp;
	}

	@Transactional
	public double totalRevenue(int id){
		List<OrderItemPojo> list = dao.selectAllOrderId(id);
		double amount = 0;
		for (OrderItemPojo p:list){
			amount += (p.getQuantity()*p.getSellingPrice());
		}
		return amount;
	}

	@Transactional
	public int totalItemCount(int id){
		List<OrderItemPojo> list = dao.selectAllOrderId(id);
		return list.size();
	}


//	protected static void normalize(OrderItemPojo p) {
//		p.setName(StringUtil.toLowerCase(p.getName()));
//	}
}
