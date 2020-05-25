package lazy;

import lazy.database.connectors.HumanRepository;
import lazy.database.entities.Human;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DataInitializer {
    public static void initializeData(ApplicationContext context) {
        HumanRepository humanRepository = context.getBean(HumanRepository.class);
        humanRepository.saveAll(getHumanList());
    }

    private static List<Human> getHumanList() {
        return new ArrayList<>(Arrays.asList(
                new Human("First", "Foo"),
                new Human("Second", "Bar"),
                new Human("Third", "Baz")
        ));
    }
}
