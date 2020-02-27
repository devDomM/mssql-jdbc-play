import com.google.inject.AbstractModule;
import modules.CustomEbeanConfigParser;
import modules.CustomEbeanDynamicEvolutions;
import modules.CustomEvolutions;
import modules.InjectorFactory;
import play.db.ebean.DefaultEbeanConfig;
import play.db.ebean.EbeanDynamicEvolutions;

public class Module extends AbstractModule {

    @Override
    protected void configure() {
        requestStaticInjection(InjectorFactory.class);

        // custom ebean bindings
        bind(DefaultEbeanConfig.EbeanConfigParser.class).to(CustomEbeanConfigParser.class);
        bind(EbeanDynamicEvolutions.class).to(CustomEbeanDynamicEvolutions.class);

        // modules
        bind(CustomEvolutions.class).asEagerSingleton();

        // for testing purposes to create some data
        bind(TestDataManager.class).asEagerSingleton();
    }
}
