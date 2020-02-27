package controllers;

import models.Customer;
import modules.InjectorFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.*;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * Manage a database of computers
 */
public class HomeController  extends Controller {

    @Inject
    private HttpExecutionContext httpExecutionContext;

    public Result index() {
        return ok(views.html.home.render());
    }

    public CompletableFuture<Result> testSelect () {
        return CompletableFuture.supplyAsync(() -> {
            DateTime start = DateTime.now();

            // the actual select
            Map<Long, Customer> customers = InjectorFactory.db().find(Customer.class).fetch("customerGroups").setMapKey("id").findMap();

            DateTime end = DateTime.now();
            Duration duration = new Duration(start, end);

            return ok(duration.toString());
        }, httpExecutionContext.current());
    }
}
