package io.github.michaelbui99.publishsubscriberbroker.broker;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIServer extends Remote {
    void start() throws RemoteException; 
}
