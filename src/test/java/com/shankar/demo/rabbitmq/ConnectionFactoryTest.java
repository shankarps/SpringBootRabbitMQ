package com.shankar.demo.rabbitmq;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@Configuration
public class ConnectionFactoryTest {

    private static Connection connection;

    @BeforeClass
    public static void setUpConnection(){
        ConnectionFactory connectionFactory = connectionFactory();
        connection = createConnection(connectionFactory);
    }



    public static ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("localhost");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    public static Connection createConnection(ConnectionFactory connectionFactory){
        Connection connection = connectionFactory.createConnection();
        return connection;
    }

    @Test
    public void testConnection() throws Exception {
        //verify the connection properties.
        assert(connection.getDelegate().getAddress().toString().contains("127.0.0.1"));
        assertEquals(connection.getDelegate().getPort(), 5672);
    }

    @AfterClass
    public static void closeConnection(){
        connection.close();
    }

}
