package org.apache.pulsar.ecosystem.io.azure.function;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.pulsar.functions.api.Record;
import org.apache.pulsar.io.core.Sink;
import org.apache.pulsar.io.core.SinkContext;
import org.testcontainers.shaded.org.apache.http.HttpResponse;
import org.testcontainers.shaded.org.apache.http.client.methods.HttpPost;
import org.testcontainers.shaded.org.apache.http.entity.StringEntity;
import org.testcontainers.shaded.org.apache.http.impl.client.CloseableHttpClient;
import org.testcontainers.shaded.org.apache.http.impl.client.HttpClientBuilder;

import java.util.Map;

public class AzureFunctionSink<T> implements Sink<T> {

    private AzureFunctionConnectorConfig config;

    @Override
    public void open(Map<String, Object> map, SinkContext sinkContext) throws Exception {
        this.config = AzureFunctionConnectorConfig.load(map);
    }

    @Override
    public void write(Record<T> record) throws Exception {
        String payload = convertToJson(record);

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(config.getFunctionUrl() + "?code=" + config.getFunctionKey());
        request.setHeader("content-type", "application/json");
        request.setEntity(new StringEntity(convertToJson(record)));

        HttpResponse response = httpClient.execute(request);
    }

    private String convertToJson(Record<T> record) {
        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        try {
            return mapper.writeValueAsString(record);
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }

    @Override
    public void close() throws Exception {
        // bye bye
    }
}
