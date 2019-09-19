package com.shankar.demo.rabbitmq;

import com.google.common.io.Files;
import org.apache.qpid.server.Broker;
import org.apache.qpid.server.BrokerOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ConnectionFactoryTest.class, //test case 1
})
public class RabbitmqApplicationTestSuite {

    public static final int BROKER_PORT = 5672;
    public static final String BROKER_HOST = "localhost";
    public static final String ConfigFileName = "test-qpid-config.json";

    private static Broker broker;

    @BeforeClass
    public static void startAMQPServers(){
        broker = new Broker();
        // prepare options
        BrokerOptions brokerOptions = new BrokerOptions();
        brokerOptions.setConfigProperty("broker.name", "embedded-broker");
        brokerOptions.setConfigProperty("qpid.amqp_port", String.valueOf(BROKER_PORT));
        brokerOptions.setConfigProperty("qpid.work_dir", Files.createTempDir().getAbsolutePath());
        // set options
        brokerOptions.setInitialConfigurationLocation(
                RabbitmqApplicationTestSuite.class.getClassLoader().getResource(ConfigFileName).toString());
        // start broker
        try {
            broker.startup(brokerOptions);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    @AfterClass
    public static void shutdownServer() {
        broker.shutdown();
    }


}
