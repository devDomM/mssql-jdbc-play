package modules;

import play.Application;
import play.db.Database;
import play.db.evolutions.Evolutions;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This Plugin shall simply create a MSSQL compatible "play_evolutions" table.
 * This is necessary because of the different meaning of the column-type "timestamp" in MSSQL
 * see also: https://github.com/playframework/Play20/issues/827
 */
public class CustomEvolutions {

    @Inject
    private CustomEvolutions(Application application, Database db) {
        exectueCustomEvolutionsCreation(application, db);
    }

    @SuppressWarnings("unused")
    private void exectueCustomEvolutionsCreation(Application app, Database db) {
        try (Connection connection = db.getConnection()) {
            ResultSet result = connection.createStatement().executeQuery("SELECT [id] FROM [play_evolutions]");

            // recreate the evolution table only if it doesn't contain anything
            if (!result.next()) {
                connection.createStatement().execute("DROP TABLE [play_evolutions]");
                connection.createStatement().execute(
                        "create table [play_evolutions] (\n" +
                        "id int not null primary key, hash varchar(255) not null, \n" +
                        "applied_at datetime not null, \n" +
                        "apply_script text, \n" +
                        "revert_script text, \n" +
                        "state varchar(255), \n" +
                        "last_problem text \n"+
                        ")"
                );

                // execute evolutions manually. see TREX-7050 for details.
                Evolutions.applyEvolutions(db);
            }
        } catch (SQLException ignored) {
        }
    }
}
