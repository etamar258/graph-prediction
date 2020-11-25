package core.utility;

import core.MainProperties;

import org.neo4j.ogm.config.ClasspathConfigurationSource;
import org.neo4j.ogm.config.Configuration;
import org.neo4j.ogm.config.Configuration.Builder;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;

import java.io.InputStream;
import java.util.Properties;

public class Neo4jSessionFactory {

    private static SessionFactory sessionFactory = null;
    private static Neo4jSessionFactory factory = new Neo4jSessionFactory();

    private Neo4jSessionFactory() {
    }

    public static Neo4jSessionFactory getInstance() {
        return factory;
    }

    public Session getSession() {
        if (sessionFactory == null) {
            Builder builder = new Builder(new ClasspathConfigurationSource("ogm.properties"));
            Properties prop = new Properties();
            InputStream input = MainProperties.class.getClassLoader().getResourceAsStream("ogm.properties");
            Configuration configuration = null;
            try {
                prop.load(input);
                configuration = builder.build();
//                configuration.
//                configuration.driverConfiguration()
//                        .setDriverClassName(prop.getProperty("driver"))
//                        .setURI(prop.getProperty("URI"))
//                        .setConnectionPoolSize(Integer.parseInt(prop.getProperty("connection.pool.size")))
//                        .setEncryptionLevel(prop.getProperty("encryption.level"))
//                        .setTrustStrategy(prop.getProperty("trust.strategy"));
            } catch (Exception e) {
//                configuration.driverConfiguration()
//                        .setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
//                        .setURI("http://neo4j:123456@localhost:7474")
//                        .setEncryptionLevel("NONE")
//                        .setTrustStrategy("TRUST_ON_FIRST_USE");
            }

            sessionFactory = new SessionFactory(configuration, "core.entities");
        }

        return sessionFactory.openSession();
    }
}
