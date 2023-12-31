package com.increff.employee.service;

import com.increff.employee.controller.ProductApiController;
import com.increff.employee.dao.BrandDao;
import com.increff.employee.dao.InventoryDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProductService {

	@Autowired
	private ProductDao dao;
	@Autowired
	private BrandDao brandDao;
	@Autowired
	private InventoryDao inventoryDao;


	@Transactional(rollbackOn = ApiException.class)
	public void addProd(ProductPojo p, ProductForm pf) throws ApiException {
		normalize(p);
//		String check_Mrp=String.valueOf(p.getMrp());
//		System.out.println("Hey................"+check_Mrp);
		if(StringUtil.isEmpty(p.getBarcode())) {
			throw new ApiException("Barcode cannot be empty");
		}
		if(dao.checkBarcode(p.getBarcode())!=null){
			throw new ApiException("Barcode already exists");
		}
		if(pf.getBrand()==""){
			throw new ApiException("Brand cannot be empty");
		}
		if(pf.getCategory()==""){
			throw new ApiException("Category cannot be empty");
		}
		if(p.getBrand_category()==0) {
			throw new ApiException("Invalid Brand Category");
		}
		if(StringUtil.isEmpty(p.getName())) {
			throw new ApiException("Name cannot be empty");
		}
//		if(StringUtil.isEmpty(check_Mrp)) {
//			throw new ApiException("Mrp cannot be empty");
//		}
		if(p.getMrp()<0){
			throw new ApiException("Mrp cannot be negative");
		}
		dao.insert(p);
		InventoryPojo pi  = new InventoryPojo();
		pi.setId(p.getId());
		pi.setQuantity(0);
		inventoryDao.insert(pi);

	}
//
//	@Transactional(rollbackOn = ApiException.class)
//	public void add(ProductPojo p) throws ApiException {
//		normalize(p);
////		String check_Mrp=String.valueOf(p.getMrp());
////		System.out.println("Hey................"+check_Mrp);
//		if(StringUtil.isEmpty(p.getBarcode())) {
//			throw new ApiException("Barcode cannot be empty");
//		}
//		if(dao.checkBarcode(p.getBarcode())!=null){
//			throw new ApiException("Barcode already exists");
//		}
//		if(StringUtil.isEmpty(p.getName())) {
//			throw new ApiException("Name cannot be empty");
//		}
////		if(StringUtil.isEmpty(check_Mrp)) {
////			throw new ApiException("Mrp cannot be empty");
////		}
//		if(p.getMrp()<0){
//			throw new ApiException("Mrp cannot be negative");
//		}
//		if(p.getBrand_category()==0) {
//			throw new ApiException("Invalid Brand Category");
//		}
//		dao.insert(p);
//		InventoryPojo pi  = new InventoryPojo();
//		pi.setId(p.getId());
//		pi.setQuantity(0);
//		inventoryDao.insert(pi);
//
//	}

	@Transactional(rollbackOn = ApiException.class)
	public ProductPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<ProductPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional
	public BrandPojo getValueBrandCategory(int id) {
		return brandDao.select(id);
	}

//	@Transactional(rollbackOn  = ApiException.class)
//	public void update(int id, ProductPojo p) throws ApiException {
//		normalize(p);
//		ProductPojo ex = getCheck(id);
//		if(StringUtil.isEmpty(p.getBarcode())) {
//			throw new ApiException("Barcode cannot be empty");
//		}
//		if((dao.checkBarcode(p.getBarcode())!=null ) ){
//			if(id != dao.checkBarcode(p.getBarcode()).getId()){
//				throw new ApiException("Barcode already exists");}
//		}
//
//		if(StringUtil.isEmpty(p.getName())) {
//			throw new ApiException("Name cannot be empty");
//		}
//		if(p.getMrp()<0){
//			throw new ApiException("Mrp cannot be negative");
//		}
//		if(brandDao.checkCategory(p.getBrand_category())==null) {
//			throw new ApiException("Invalid Brand Category");
//		}
//
//		ex.setBarcode(p.getBarcode());
//		ex.setName(p.getName());
//		ex.setMrp(p.getMrp());
//		dao.update(ex);
//	}


	@Transactional(rollbackOn  = ApiException.class)
	public void updateProd(int id, ProductPojo p,ProductForm pf) throws ApiException {
		normalize(p);
		ProductPojo ex = getCheck(id);
		if(StringUtil.isEmpty(p.getBarcode())) {
			throw new ApiException("Barcode cannot be empty");
		}
		if((dao.checkBarcode(p.getBarcode())!=null ) ){
			if(id != dao.checkBarcode(p.getBarcode()).getId()){
				throw new ApiException("Barcode already exists");}
		}
		if(pf.getBrand()==""){
			throw new ApiException("Brand cannot be empty");
		}
		if(pf.getCategory()==""){
			throw new ApiException("Category cannot be empty");
		}
		if(brandDao.checkCategory(p.getBrand_category())==null) {
			throw new ApiException("Invalid Brand Category");
		}
		if(StringUtil.isEmpty(p.getName())) {
			throw new ApiException("Name cannot be empty");
		}
		if(p.getMrp()<0){
			throw new ApiException("Mrp cannot be negative");
		}

		ex.setBarcode(p.getBarcode());
		ex.setName(p.getName());
		ex.setMrp(p.getMrp());
		dao.update(ex);
	}

	@Transactional
	public ProductPojo getCheck(int id) throws ApiException {
		ProductPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Product with given ID does not exit, id: " + id);
		}
		return p;
	}

	protected static void normalize(ProductPojo p) {
		p.setName(StringUtil.toLowerCase(p.getName()));
	}
}
