package modules;

import io.ebean.EbeanServer;
import io.ebean.EbeanServerFactory;
import play.Environment;
import play.db.ebean.EbeanConfig;
import play.db.ebean.EbeanDynamicEvolutions;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/**
 * Custom implementation of {@link EbeanDynamicEvolutions}. Evoultion SQL Scripts will not be created if
 * <b>play.evolutions.db.DATASOURCE.enabled</b> is set to "false" for a specific {@link EbeanServer} (datasource).
 */
@Singleton
public class CustomEbeanDynamicEvolutions extends EbeanDynamicEvolutions {

    private final EbeanConfig config;
    private final Environment environment;

    private final Map<String, EbeanServer> servers = new HashMap<>();

    @Inject
    public CustomEbeanDynamicEvolutions(EbeanConfig config, Environment environment, ApplicationLifecycle lifecycle) {
        super(config, environment, lifecycle);
        this.config = config;
        this.environment = environment;
        config.serverConfigs().forEach((key, serverConfig) -> servers.put(key, EbeanServerFactory.create(serverConfig)));
    }

    @Override
    public void create() {
        if (!environment.isProd()) {
            config.serverConfigs().forEach((key, serverConfig) -> {
                // check config
                if (serverConfig.isDdlGenerate()) {
                    String evolutionScript = generateEvolutionScript(servers.get(key));
                    if (evolutionScript != null) {
                        File evolutions = environment.getFile("conf/evolutions/" + key + "/1.sql");
                        try {
                            String content = "";
                            if (evolutions.exists()) {
                                content = new String(Files.readAllBytes(evolutions.toPath()), StandardCharsets.UTF_8);
                            }

                            if (content.isEmpty() || content.startsWith("# --- Created by Ebean DDL")) {
                                //noinspection ResultOfMethodCallIgnored
                                environment.getFile("conf/evolutions/" + key).mkdirs();
                                if (!content.equals(evolutionScript)) {
                                    Files.write(evolutions.toPath(), evolutionScript.getBytes(StandardCharsets.UTF_8));
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
    }
}
