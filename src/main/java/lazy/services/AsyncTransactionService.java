package lazy.services;

import lazy.database.connectors.DogRepository;
import lazy.database.connectors.HumanRepository;
import lazy.database.entities.Dog;
import lazy.database.entities.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class AsyncTransactionService {

    @Autowired
    private HumanRepository humanRepository;

    @Autowired
    private DogRepository dogRepository;

    @Async
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void addDefaultDogToAllHumans() throws InterruptedException {
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel() + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println("waiting...");
        Thread.sleep(10000);
        System.out.println("finished waiting");
        humanRepository.findAll().forEach(human -> {
            Dog dog = new Dog("Bolt", "White", human);
            human.getDogs().add(dog);
            dogRepository.save(dog);
        });

        humanRepository.findAll().forEach(human -> {
            System.out.println(human.getFirstName() + " " + human.getLastName());
        });
    }
}
