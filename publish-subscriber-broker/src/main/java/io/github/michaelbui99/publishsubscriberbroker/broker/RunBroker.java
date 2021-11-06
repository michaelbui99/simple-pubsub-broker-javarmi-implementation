package io.github.michaelbui99.publishsubscriberbroker.broker;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RunBroker {
    public static void main(String[] args) throws AccessException, RemoteException, AlreadyBoundException {
        RMIServer brokerServer = new BrokerImpl(); 
        Registry registry = LocateRegistry.createRegistry(1099); 
        registry.bind("Broker", brokerServer);
        System.out.println("Starting server....");
    }
}
