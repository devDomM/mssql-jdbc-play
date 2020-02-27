package modules;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import play.api.inject.Injector;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * this class can be used to recieve injected members in play.
 */
public class InjectorFactory {

    @Inject
    private static Provider<Injector> injectorProvider;

    /**
     * Returns the registered ebean server. Currently uses the {@link Ebean} singleton to receive the injected default EbeanServer.
     * @return the default ebean server
     */
    public static EbeanServer db() {
        return Ebean.getDefaultServer();
    }

    public static EbeanServer keyunitdb() {
        return Ebean.getServer("keyunit");
    }

    private static Injector getInstance() {
        return injectorProvider.get();
    }

    @SuppressWarnings("unused")
    private static <T>T instanceOf(Class<T> clazz) {
        return getInstance().instanceOf(clazz);
    }
}
