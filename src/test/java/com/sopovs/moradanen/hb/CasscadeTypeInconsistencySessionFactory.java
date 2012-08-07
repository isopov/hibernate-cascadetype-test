package com.sopovs.moradanen.hb;

import java.util.Arrays;

import junit.framework.Assert;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CasscadeTypeInconsistencySessionFactory {
	private SessionFactory sessionFactory;

	@Before
	public void setUp() throws Exception {
		// A SessionFactory is set up once for an application
		sessionFactory = new Configuration()
				.configure() // configures settings from hibernate.cfg.xml
				.buildSessionFactory();
	}

	@After
	public void tearDown() throws Exception {
		if (sessionFactory != null) {
			sessionFactory.close();
		}
	}

	@Test
	public void testCascadeTypeAll() {
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Parent1 parent = new Parent1();
		Child1 child = new Child1();
		child.setParent(parent);
		parent.setChildren(Arrays.asList(child));

		session.save(parent);

		Assert.assertNotNull(parent.getId());
		Assert.assertNotNull(child.getId());

		session.getTransaction().commit();
		session.close();
	}

	@Test
	public void testCascadeTypeOthers() {
		// create a couple of events...
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Parent2 parent = new Parent2();
		Child2 child = new Child2();
		child.setParent(parent);
		parent.setChildren(Arrays.asList(child));

		session.save(parent);

		Assert.assertNotNull(parent.getId());
		//here we fail
		Assert.assertNotNull(child.getId());

		session.getTransaction().commit();
		session.close();
	}

}