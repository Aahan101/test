package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {

	private static String delete_id = "delete from OrderItemPojo p where id=:id";
	private static String select_id = "select p from OrderItemPojo p where id=:id";
	private static String select_all = "select p from OrderItemPojo p";

	private static String select_all_orderId="select p from OrderItemPojo p where orderId=:orderId";

	private static String select_all_productId="select p from OrderItemPojo p where productId=:productId";


	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(OrderItemPojo p) {
		em.persist(p);
	}

	public int delete(int id) {
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(select_id, OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public List<OrderItemPojo> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(select_all, OrderItemPojo.class);
		return query.getResultList();
	}

	public List<OrderItemPojo> selectAllOrderId(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(select_all_orderId, OrderItemPojo.class);
		query.setParameter("id", id);
		return query.getResultList();
	}
	public List<OrderItemPojo> selectAllProductId(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(select_all_productId, OrderItemPojo.class);
		query.setParameter("id", id);
		return query.getResultList();
	}


	public void update(OrderItemPojo p) {
	}



}
