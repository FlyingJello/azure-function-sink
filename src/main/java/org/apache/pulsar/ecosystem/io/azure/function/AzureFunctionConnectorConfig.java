package org.apache.pulsar.ecosystem.io.azure.function;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
public class AzureFunctionConnectorConfig implements Serializable {
    private static final long serialVersionUID = 1L;

    private String functionUrl;
    private String functionKey;

    public static AzureFunctionConnectorConfig load(Map<String, Object> config) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new ObjectMapper().writeValueAsString(config), AzureFunctionConnectorConfig.class);
    }
}


