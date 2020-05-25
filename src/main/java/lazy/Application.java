package lazy;

import lazy.configurations.MyConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

import static lazy.DataInitializer.initializeData;

@SpringBootApplication
@Import(MyConfiguration.class)
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        initializeData(context);
    }
}
