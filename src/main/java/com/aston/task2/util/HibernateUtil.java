package com.aston.task2.util;

import com.aston.task2.exception.HibernateUtilException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.util.Properties;

@Log4j2
public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            InputStream input = HibernateUtil.class.getClassLoader().getResourceAsStream("hibernate.properties");

            Properties props = new Properties();
            props.load(input);

            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().applySettings(props).build();

            Metadata metadata = new MetadataSources(standardRegistry).addAnnotatedClass(com.aston.task2.entity.User.class).getMetadataBuilder().build();

            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            log.error("Initial SessionFactory creation failed: " + e);
            throw new HibernateUtilException(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
