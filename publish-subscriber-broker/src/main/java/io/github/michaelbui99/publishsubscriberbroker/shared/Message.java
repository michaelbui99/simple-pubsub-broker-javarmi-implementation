package io.github.michaelbui99.publishsubscriberbroker.shared;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Message implements Serializable {
    @NonNull private String topic;
    @NonNull private String  messagePayload;   

}
