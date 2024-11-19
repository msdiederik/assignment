package nl.ing.mortgages.mortgagecheck.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class BodyParser<T> {
    private final ObjectMapper objectMapper;
    private final Class<T> typeParameterClass;

    public BodyParser(Class<T> typeParameterClass) {
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        this.typeParameterClass = typeParameterClass;
    }

    public T parseBodyToObject(String body) throws JsonProcessingException {
        return objectMapper.readValue(body, typeParameterClass);
    }

    public String parseObjectToBody(T object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }
}
