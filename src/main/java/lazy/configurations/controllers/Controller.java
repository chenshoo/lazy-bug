package lazy.configurations.controllers;

import lazy.database.connectors.DogRepository;
import lazy.database.connectors.HumanRepository;
import lazy.database.entities.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class Controller {

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private DogRepository dogRepository;

    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @GetMapping("/")
    public String logic() {
        threadPoolTaskExecutor.execute(() -> {
            try {
                Thread.sleep(5000);
                addDefaultDogToAllHumans();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return "Request received!";
    }

    @GetMapping("/humans")
    public List<String> humans() {
        return humanRepository.findAll().stream()
                .map(human -> human.getFirstName() + " " + human.getLastName()).collect(Collectors.toList());
    }

    private void addDefaultDogToAllHumans() {
        humanRepository.findAll().forEach(human -> {
            Dog dog = new Dog("Bolt", "White", human);
            human.getDogs().add(dog);
            dogRepository.save(dog);
            humanRepository.save(human);
        });
    }
}
