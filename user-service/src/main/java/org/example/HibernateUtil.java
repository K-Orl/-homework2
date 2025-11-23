package org.example;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.addAnnotatedClass(User.class);

                // Берём настройки из System.properties, если есть (для тестов)
                String url = System.getProperty("hibernate.connection.url");
                String user = System.getProperty("hibernate.connection.username");
                String pass = System.getProperty("hibernate.connection.password");

                if (url != null) {
                    configuration.setProperty("hibernate.connection.url", url);
                    configuration.setProperty("hibernate.connection.username", user);
                    configuration.setProperty("hibernate.connection.password", pass);
                } else {
                    // Настройки локальной PostgreSQL
                    configuration.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
                    configuration.setProperty("hibernate.connection.url", "jdbc:postgresql://localhost:8888/Base");
                    configuration.setProperty("hibernate.connection.username", "admin1");
                    configuration.setProperty("hibernate.connection.password", "admin");
                }

                configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
                configuration.setProperty("hibernate.hbm2ddl.auto", "update");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.setProperty("hibernate.format_sql", "true");
                configuration.setProperty("hibernate.bytecode.use_reflection_optimizer", "false");

                sessionFactory = configuration.buildSessionFactory();
            } catch (Throwable ex) {
                throw new ExceptionInInitializerError(ex);
            }
        }
        return sessionFactory;
    }

    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
