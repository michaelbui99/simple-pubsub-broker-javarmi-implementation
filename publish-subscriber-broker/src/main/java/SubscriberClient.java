import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.google.gson.Gson;

import io.github.michaelbui99.publishsubscriberbroker.broker.Broker;
import io.github.michaelbui99.publishsubscriberbroker.shared.Callbackable;
import io.github.michaelbui99.publishsubscriberbroker.shared.Message;

public class SubscriberClient implements Callbackable {

    private Gson gson = new Gson(); 
    private String topic; 
    private Broker broker; 

    public SubscriberClient(String topic) {
        try {
            UnicastRemoteObject.exportObject(this, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        } 
        this.topic = topic; 
    }

    public void start(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            broker = (Broker) registry.lookup("broker");
            broker.subscribeToTopic(this, topic);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        } 
    }


    @Override
    public void broadCastMessage(Message message) {
        // TODO Auto-generated method stub
        System.out.println(gson.toJson(message));
    }
    
}
