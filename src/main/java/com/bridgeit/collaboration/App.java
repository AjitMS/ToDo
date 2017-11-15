package com.bridgeit.collaboration;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class App {

	static SessionFactoryBuilder builder = new SessionFactoryBuilder();
	static SessionFactory factory = builder.getSessionFactory();
	Logger logger = Logger.getLogger(App.class);

	public static void main(String args[]) {
		Book b1 = new Book("1", "book1");
		Collection<Book> bookList = new ArrayList<Book>();
		bookList.add(b1);
		Student s1 = new Student("1", "user1", bookList);
		Student s2 = new Student("2", "user1", bookList);
		Student s3 = new Student("3", "user1", bookList);
		Collection<Student> studentList = new ArrayList<Student>();
		studentList.add(s1);
		studentList.add(s2);
		studentList.add(s3);
		b1.setStudentList(studentList);
		Session session = factory.openSession();
		Transaction tx = session.beginTransaction();
		session.save(b1);
		session.save(s1);
		tx.commit();
		

	}
}
