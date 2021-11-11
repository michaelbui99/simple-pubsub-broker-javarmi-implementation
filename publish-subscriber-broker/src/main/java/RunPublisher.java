import com.google.gson.Gson;

import io.github.michaelbui99.publishsubscriberbroker.shared.Message;

public class RunPublisher {
    public static void main(String[] args) throws InterruptedException {
        PublisherClient pub = new PublisherClient("test"); 
        pub.start();
        Gson gson = new Gson(); 
        Thread.sleep(20000);
        for (int i = 0; i<100; i++){
            Message message = new Message("test", "Message " + i); 
            System.out.println(gson.toJson(message)); 
            pub.publishMessage(message);
        }
    }
}
