package com.increff.employee.dao;

import com.increff.employee.pojo.DayonDaySalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class DayonDaySalesDao extends AbstractDao {

	private static String delete_id = "delete from DayonDaySalesPojo p where id=:id";
	private static String select_id = "select p from DayonDaySalesPojo p where id=:id";
	private static String select_all = "select p from DayonDaySalesPojo p";

	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(DayonDaySalesPojo p) {
		em.persist(p);
	}

	public int delete(int id) {
		Query query = em.createQuery(delete_id);
		query.setParameter("id", id);
		return query.executeUpdate();
	}

	public DayonDaySalesPojo select(int id) {
		TypedQuery<DayonDaySalesPojo> query = getQuery(select_id, DayonDaySalesPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}



	public List<DayonDaySalesPojo> selectAll() {
		TypedQuery<DayonDaySalesPojo> query = getQuery(select_all, DayonDaySalesPojo.class);
		return query.getResultList();
	}

	public void update(DayonDaySalesPojo p) {
	}



}
