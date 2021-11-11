package io.github.michaelbui99.publishsubscriberbroker.broker;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import io.github.michaelbui99.publishsubscriberbroker.shared.Callbackable;
import io.github.michaelbui99.publishsubscriberbroker.shared.Message;
import javafx.util.Callback;
import lombok.Data;
import lombok.Synchronized;

@Data
public class BrokerImpl implements Broker, RMIServer{
    /** 
     * 
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
    public void start() throws RemoteException {
        try {
            System.out.println("Starting server....");
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("broker", this);
            System.out.println("Server started...");
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }       
    }

    @Override
    @Synchronized
    public void subscribeToTopic(Callbackable subscriber, String topic) throws IllegalStateException {
        if (publishers.get(topic) == null){
            throw new IllegalStateException("No publishers of such topic exists");
        }
        System.out.println("New subscriber to topic: " + topic );
        subscribers.get(topic).add(subscriber);     
    }

    @Override
    @Synchronized
    public void unscribeFromTopic(Callbackable subscriber, String topic) {
        boolean subscriberIsSubscribedToTopic = subscribers.get(topic).contains(subscriber); 
        if (!subscriberIsSubscribedToTopic){
            throw new IllegalArgumentException("Subscriber is not subscribed to the topic"); 
        }
        
        
    }

    @Override
    @Synchronized
    public synchronized void joinAsPublisher(String topic) {
        System.out.println("New published joined");
        if (publishers.get(topic) == null){
            int publisherCount = 1; 
            publishers.put(topic, publisherCount);
            subscribers.put(topic, new ArrayList<Callbackable>()); 
        }else{
            int newPublisherCount = publishers.get(topic).intValue() + 1  ; 
            publishers.replace(topic, newPublisherCount);
        }
    }

    @Override
    @Synchronized
    public void leaveAsPublisher(String topic) {
        if (publishers.get(topic) == null){
          return;   
        }
        int currentPublisherCount = publishers.get(topic); 
        publishers.replace(topic, currentPublisherCount, currentPublisherCount++); 
    }

 




    @Override
    @Synchronized
    public List<String> fetchTopics() {
       List<String> topics = new ArrayList<>(publishers.keySet()); 
       return topics;
    }


    @Override
    public void broadcastMessage(Message message) throws RemoteException {
        // TODO Auto-generated method stub
        for (Callbackable subscriber : subscribers.get(message.getTopic())){
            subscriber.broadCastMessage(message);
        }
        
    }

    
}
