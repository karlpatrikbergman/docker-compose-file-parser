package se.patrikbergman.java.yaml.object;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * A docker-compose.yml file can be loaded as object and pretty printed
 * as json by jackson.
 */
public class ObjectYamlTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test2() throws IOException {
        Object object = YamlToObjectParser.parse("object/docker-compose.yml");
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object));
    }
}
