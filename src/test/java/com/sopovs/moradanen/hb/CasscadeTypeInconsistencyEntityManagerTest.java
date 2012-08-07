package com.sopovs.moradanen.hb;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CasscadeTypeInconsistencyEntityManagerTest {
	private EntityManagerFactory entityManagerFactory;

	@Before
	public void setUp() throws Exception {
		entityManagerFactory = Persistence.createEntityManagerFactory("hibernate");
	}

	@After
	public void tearDown() throws Exception {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

	@Test
	public void testCascadeTypeAll() {
		// create a couple of events...
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Parent1 parent = new Parent1();
		Child1 child = new Child1();
		child.setParent(parent);
		parent.setChildren(Arrays.asList(child));

		em.persist(parent);

		Assert.assertNotNull(parent.getId());
		Assert.assertNotNull(child.getId());

		em.getTransaction().commit();
		em.close();
	}

	@Test
	public void testCascadeTypeOthers() {
		// create a couple of events...
		EntityManager em = entityManagerFactory.createEntityManager();
		em.getTransaction().begin();
		Parent2 parent = new Parent2();
		Child2 child = new Child2();
		child.setParent(parent);
		parent.setChildren(Arrays.asList(child));

		em.persist(parent);

		Assert.assertNotNull(parent.getId());
		//here we fail
		Assert.assertNotNull(child.getId());

		em.getTransaction().commit();
		em.close();
	}

}
