package io.github.michaelbui99.publishsubscriberbroker.broker;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RunBroker {
    public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {
        RMIServer brokerServer = new BrokerImpl(); 
        brokerServer.start();
    }
}
