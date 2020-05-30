package lazy.services;

import lazy.database.connectors.HumanRepository;
import lazy.database.entities.Human;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
public class SecondService {
    @Autowired
    private HumanRepository humanRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public String changeName() {
        System.out.println(TransactionSynchronizationManager.getCurrentTransactionIsolationLevel() + TransactionSynchronizationManager.getCurrentTransactionName());
        Human one = humanRepository.getOne(1L);
        one.setFirstName("chen");
        humanRepository.save(one);
        System.out.println("name was chaned");
        return "name changed!";
    }
}
