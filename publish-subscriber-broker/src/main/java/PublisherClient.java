import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import io.github.michaelbui99.publishsubscriberbroker.broker.Broker;
import io.github.michaelbui99.publishsubscriberbroker.shared.Message;

public class PublisherClient {
    
    private Broker broker; 
    private String topic; 

    public PublisherClient(String topic) {        
        this.topic = topic; 
    }

    public void start(){
        try {
            Registry registry = LocateRegistry.getRegistry(1099);
            broker = (Broker) registry.lookup("broker");
            broker.joinAsPublisher(topic); 
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        } 
    }


    public void publishMessage(Message message){
        try {
            broker.broadcastMessage(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
