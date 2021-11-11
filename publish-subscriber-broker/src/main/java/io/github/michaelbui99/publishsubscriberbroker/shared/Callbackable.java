package io.github.michaelbui99.publishsubscriberbroker.shared;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Callbackable extends Remote{
    void broadCastMessage(Message message) throws RemoteException; 
}
