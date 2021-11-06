package io.github.michaelbui99.publishsubscriberbroker.broker;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import io.github.michaelbui99.publishsubscriberbroker.shared.Callbackable;
import io.github.michaelbui99.publishsubscriberbroker.shared.Message;
import lombok.Data;
import lombok.NonNull;
import lombok.Synchronized;

@Data
public class BrokerImpl implements Broker, RMIServer{
    /** 
     * Topic acts as key for the HashMap. 
     * List contains all subscribers of a specific topic. 
     * */ 
    private HashMap<String, List<Callbackable>> subscribers = new HashMap<>();
    
    /**
     * Topic acts as key for the HashMap. 
     * The value keeps track of how many publishers of a given topic exists. 
     * When all publishers of topic has left, then the topic will be removed. 
     */
    private HashMap<String, Integer> publishers = new HashMap<>(); 
    private Gson gson = new Gson();

    public BrokerImpl() {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    @Synchronized
    public void subscribeToTopic(Callbackable subscriber, String topic) throws IllegalStateException {
        if (publishers.get(topic) == null){
            throw new IllegalStateException("No publishers of such topic exists");
        }
        subscribers.get(topic).add(subscriber);     
    }

    @Override
    @Synchronized
    public synchronized void joinAsPublisher(String topic) {
        if (publishers.get(topic) == null){
            int publisherCount = 1; 
            publishers.put(topic, publisherCount);
        }else{
            int newPublisherCount = publishers.get(topic).intValue() + 1  ; 
            publishers.replace(topic, newPublisherCount);
        }
    }

    @Override
    @Synchronized
    public void leaveAsPublisher(String topic) {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Synchronized
    public void unscribeFromTopic(Callbackable subscriber, String topic) {
        // TODO Auto-generated method stub
        
    }

    @Override
    @Synchronized
    public void broadcastMessage(String topic, Object messageContent) {
        // TODO Auto-generated method stub
        String messageContentAsJson = gson.toJson(messageContent); 
        Message message = new Message(topic, messageContentAsJson);
        
    }


    @Override
    @Synchronized
    public List<String> fetchTopics() {
        // TODO Auto-generated method stub
        return null;
    }

    
}
