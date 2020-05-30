package lazy.configurations.controllers;

import lazy.database.connectors.DogRepository;
import lazy.database.connectors.HumanRepository;
import lazy.database.entities.Human;
import lazy.services.AsyncTransactionService;
import lazy.services.SecondService;
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
    private SecondService service2;

    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private DogRepository dogRepository;

    @GetMapping("/")
    public String logic() {
            try {
                service.addDefaultDogToAllHumans();
            } catch (Exception e) {
            }
        return "Request received!";
    }

    @GetMapping("/update")
    public String update() {
        return service2.changeName();
    }
}
