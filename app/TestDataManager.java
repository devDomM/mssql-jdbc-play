import models.*;
import modules.InjectorFactory;
import org.apache.commons.lang3.RandomStringUtils;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class TestDataManager {

    @Inject
    private TestDataManager() {
        if (InjectorFactory.db().find(Country.class).findCount() == 0) {
            Country country = new Country();
            country.isoCode = "US";
            country.save();
        }

        if (InjectorFactory.db().find(CustomerGroup.class).findCount() == 0) {
            Country customerGroupCountry = InjectorFactory.db().find(Country.class).findList().get(0);

            List<CustomerGroup> customerGroupList = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                CustomerGroup customerGroup = new CustomerGroup();
                customerGroup.name = generateRandomString(16) + i;
                customerGroup.country = customerGroupCountry;
                customerGroupList.add(customerGroup);
            }
            InjectorFactory.db().saveAll(customerGroupList);
        }

        if (InjectorFactory.db().find(Customer.class).findCount() == 0) {
            Country customerCountry = InjectorFactory.db().find(Country.class).findList().get(0);
            List<CustomerGroup> customerGroupList = InjectorFactory.db().find(CustomerGroup.class).findList();

            List<Customer> customerList = new ArrayList<>();
            List<PointBalance> pointBalanceList = new ArrayList<>();
            for (int i = 0; i < 25000; i++) {
                Customer customer = new Customer();
                customer.country = customerCountry;
                customer.email = generateRandomString(10) + i;
                customer.name = generateRandomString(18) + i;
                customer.language = "en";

                List<CustomerGroupRelation> customerGroupRelationList = new ArrayList<>();
                for (int y = 0; y < 3; y++) {
                    CustomerGroupRelation customerGroupRelation = new CustomerGroupRelation();
                    Long randomId = generateRandomNumber();
                    //noinspection OptionalGetWithoutIsPresent
                    customerGroupRelation.customerGroup = customerGroupList.stream().filter(cg -> cg.id.equals(randomId)).findFirst().get();
                    customerGroupRelation.customer = customer;
                    customerGroupRelation.customerNumber = generateRandomString(7) + i + "-" + y;

                    customerGroupRelationList.add(customerGroupRelation);
                }
                customer.customerGroups = customerGroupRelationList;

                customerList.add(customer);


                PointBalance pointBalanceActive = new PointBalance();
                pointBalanceActive.customer = customer;
                pointBalanceActive.remainingPoints = new BigDecimal(generateRandomNumber());
                pointBalanceActive.isExpired = false;
                pointBalanceList.add(pointBalanceActive);

                PointBalance pointBalanceExpired = new PointBalance();
                pointBalanceExpired.customer = customer;
                pointBalanceExpired.remainingPoints = new BigDecimal(generateRandomNumber());
                pointBalanceExpired.isExpired = true;
                pointBalanceList.add(pointBalanceExpired);
            }
            InjectorFactory.db().saveAll(customerList);

            InjectorFactory.db().saveAll(pointBalanceList);
        }

        // scan unsubmitted stays empty for testing. table still exists, so the @Formula on Customer works.
    }

    private String generateRandomString(int length) {
        return RandomStringUtils.random(length, true, false);
    }

    private Long generateRandomNumber() {
        Random r = new Random();
        int rnd = r.nextInt(100-1) + 1;
        return (long) rnd;
    }
}
