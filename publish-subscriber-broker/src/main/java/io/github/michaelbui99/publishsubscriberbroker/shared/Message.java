package io.github.michaelbui99.publishsubscriberbroker.shared;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Message {
    @NonNull private String topic;
    @NonNull private String  jsonMessagePayload;   
}
