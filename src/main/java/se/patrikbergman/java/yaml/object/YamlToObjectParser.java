package se.patrikbergman.java.yaml.object;

import org.yaml.snakeyaml.Yaml;
import se.patrikbergman.java.utility.resource.ResourceInputStream;

import java.io.IOException;
import java.io.InputStream;

final class YamlToObjectParser {

    public static Object parse(String resourceOnClassPath) throws IOException {
        Yaml yaml = new Yaml();
        InputStream in = new ResourceInputStream(resourceOnClassPath);
        return yaml.loadAs(in, Object.class);
    }
}
