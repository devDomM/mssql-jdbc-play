package modules;

import io.ebean.config.ServerConfig;
import play.Configuration;
import play.Environment;
import play.db.DBApi;
import play.db.ebean.DefaultEbeanConfig;
import play.db.ebean.EbeanConfig;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.util.Map;

/**
 * Custom implementation of {@link DefaultEbeanConfig.EbeanConfigParser}. When starting multiple {@link io.ebean.EbeanServer},
 * a default datasource has to be selected. This should usually be done in the application.conf with by setting <b>ebeanconfig.datasource.default=DATASOURCENAME</b>
 * But since this key is ignored we have to set the default datasource manually.
 */
@Singleton
public class CustomEbeanConfigParser extends DefaultEbeanConfig.EbeanConfigParser implements Provider<EbeanConfig> {

    @Inject
    public CustomEbeanConfigParser(Configuration configuration, Environment environment, DBApi dbApi) {
        super(configuration, environment, dbApi);
    }

    @Override
    public EbeanConfig parse() {
        DefaultEbeanConfig ebeanConfig = (DefaultEbeanConfig) super.parse();

        Map<String, ServerConfig> serverConfigMap = ebeanConfig.serverConfigs();
        for (Map.Entry<String, ServerConfig> entry : serverConfigMap.entrySet()) {
            entry.getValue().setDefaultServer(entry.getKey().equals(ebeanConfig.defaultServer()));
        }

        return ebeanConfig;
    }
}
