package se.patrikbergman.java.yaml.dockercompose;

import lombok.Data;

import java.util.Map;

@Data
public final class DockerCompose {
    private String version;
    private Map<String, Service> services;
    private Map<String, Network> networks;
}
