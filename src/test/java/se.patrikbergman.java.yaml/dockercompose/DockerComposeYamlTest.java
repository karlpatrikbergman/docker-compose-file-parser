package se.patrikbergman.java.yaml.dockercompose;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class DockerComposeYamlTest {

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void test() throws IOException {
        DockerCompose dockerCompose = YamlToJavaParser.parse("dockercompose/docker-compose.yml");

        Service alarmService = dockerCompose.getServices().get("alarmservice");
        assertNotNull(alarmService);
        assertEquals(alarmService.getImage(), "com.infinera.metro.service.alarm/alarmservice:1.0-SNAPSHOT");
        assertEquals(alarmService.getEnvironment().get("SPRING_PROFILES_ACTIVE"), "docker-compose");
        assertEquals(alarmService.getPorts().size(), 1);
        assertTrue(alarmService.getNetworks().containsKey("alarmservice_network"));
        assertTrue(alarmService.getDepends_on().contains("alarmservice_db"));
        assertTrue(alarmService.getLinks().contains("alarmservice_db"));

        Service node1 = dockerCompose.getServices().get("node1");
        assertNotNull(node1);
        assertEquals("172.25.0.101", node1.getNetworks().get("alarmservice_network").get("ipv4_address"));

        Service nodesSetup = dockerCompose.getServices().get("nodes_setup");
        assertNotNull(nodesSetup);
        assertEquals("nodes_setup", nodesSetup.getBuild());
        assertEquals(2, nodesSetup.getDepends_on().size());

        Network network = dockerCompose.getNetworks().get("alarmservice_network");
        assertNotNull(network);
        assertEquals("bridge", network.getDriver());
        Map<String, List<Map>> ipam = network.getIpam();
        assertNotNull(ipam);
        List<Map> config = ipam.get("config");
        assertNotNull(config);
        assertEquals("172.25.0.0/24", config.get(0).get("subnet"));

        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dockerCompose));

    }
}
