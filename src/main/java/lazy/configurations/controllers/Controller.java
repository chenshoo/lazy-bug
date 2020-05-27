package lazy.configurations.controllers;

import lazy.database.connectors.DogRepository;
import lazy.database.connectors.HumanRepository;
import lazy.services.AsyncTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/")
public class Controller {

    @Autowired
    private AsyncTransactionService service;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private DogRepository dogRepository;

    @GetMapping("/")
    public String logic() {
        taskExecutor.execute(() -> {
            try {
                Thread.sleep(5000);
                service.addDefaultDogToAllHumans(humanRepository, dogRepository);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return "Request received!";
    }
}
