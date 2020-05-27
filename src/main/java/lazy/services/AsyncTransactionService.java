package lazy.services;

import lazy.database.connectors.DogRepository;
import lazy.database.connectors.HumanRepository;
import lazy.database.entities.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsyncTransactionService {

//    @Autowired
//    private HumanRepository humanRepository;
//
//    @Autowired
//    private DogRepository dogRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addDefaultDogToAllHumans(HumanRepository humanRepository, DogRepository dogRepository) {
        humanRepository.findAll().forEach(human -> {
            Dog dog = new Dog("Bolt", "White", human);
            human.getDogs().add(dog);
            dogRepository.save(dog);
//            humanRepository.save(human);
        });
    }
}
