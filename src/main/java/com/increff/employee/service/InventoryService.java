package com.increff.employee.service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.InventoryData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class InventoryService {

    @Autowired
    private InventoryDao dao;

	@Autowired
	private ProductDao productdao;

    @Transactional(rollbackOn = ApiException.class)
    public void add(InventoryPojo p) throws ApiException {
//		normalize(p);
//		if(StringUtil.isEmpty(p.getInventory())) {
//			throw new ApiException("Inventory cannot be empty");
//		}
        if (productdao.checkId(p.getId())==null) {
            throw new ApiException("Inventory with given id does not exist");
        }
        if(p.getQuantity()<0){
            throw new ApiException("Quantity can not be negative");
        }
        dao.insert(p);
    }

    @Transactional(rollbackOn = ApiException.class)
    public InventoryPojo get(int id) throws ApiException {
        return getCheck(id);
    }

    @Transactional
    public List<InventoryPojo> getAll() {
        return dao.selectAll();
    }
    @Transactional
    public String getBarcode(int id) {
        return productdao.selectBarcode(id);
    }

    @Transactional(rollbackOn = ApiException.class)
    public void update(int id, InventoryPojo p) throws ApiException {
        //normalize(p);
        InventoryPojo ex = getCheck(id);
        if(p.getQuantity()<0){
            throw new ApiException("Quantity can not be negative");
        }
        ex.setQuantity(p.getQuantity());
        dao.update(ex);
    }

    @Transactional
    public InventoryPojo getCheck(int id) throws ApiException {
        InventoryPojo p= dao.select(id);
        if (p == null) {
            throw new ApiException("Inventory with given ID does not exit, id: " + id);
        }
        return p;
    }

    protected static void normalize(InventoryPojo p) {
        //p.setQuantity(StringUtil.toLowerCase(p.getQuantity()));
    }
}
