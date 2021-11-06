package io.github.michaelbui99.publishsubscriberbroker.shared;

import java.rmi.Remote;

public interface Callbackable extends Remote{
    void broadCastMessage(Message message); 
}
