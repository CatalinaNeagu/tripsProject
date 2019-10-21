package ro.siit.trips.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.siit.trips.models.Person;
import ro.siit.trips.repositiories.PersonRepository;

@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;


public void updateUser(Long id, Person person){
    if (person.getId().equals(id)){
        personRepository.save(person);
    }
}

}
