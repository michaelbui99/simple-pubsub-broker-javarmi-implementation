package io.github.michaelbui99.publishsubscriberbroker.broker;

import java.rmi.RemoteException;
import java.util.List;

import io.github.michaelbui99.publishsubscriberbroker.shared.Callbackable;

public interface Broker {
    void subscribeToTopic(Callbackable subscriber, String topic) throws IllegalStateException, RemoteException; 
    void unscribeFromTopic(Callbackable subscriber, String topic) throws RemoteException;
    void joinAsPublisher(String topic) throws RemoteException;
    void leaveAsPublisher(String topic) throws RemoteException; 
    void broadcastMessage(String topic, Object messageContent) throws RemoteException;
    List<String> fetchTopics() throws RemoteException;  
}
