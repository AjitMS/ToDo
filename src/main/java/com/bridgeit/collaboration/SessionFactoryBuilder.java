package com.bridgeit.collaboration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryBuilder {

	private static SessionFactory sessionFactory;

	private SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration();
		sessionFactory = configuration.configure().addAnnotatedClass(Book.class).addAnnotatedClass(Student.class).buildSessionFactory();
		System.out.println("Session Factory build Successful");
		return sessionFactory;
	}

	public SessionFactory getSessionFactory() {
		if (sessionFactory == null)
			sessionFactory = buildSessionFactory();
		return sessionFactory;
	}

}
