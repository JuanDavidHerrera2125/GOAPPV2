package com.SENA.GOAPPv2.Service;

import com.SENA.GOAPPv2.Entity.Person;
import com.SENA.GOAPPv2.IService.PersonIService;
import com.SENA.GOAPPv2.Repository.PersonRepository;
import com.SENA.GOAPPv2.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService implements PersonIService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private UserRepository userRepository; // Asegúrate de tener esta línea

    @Override
    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    @Override
    public Optional<Person> getPersonById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public Person createPerson(Person person) {
        // Convertir la fecha de nacimiento (si es necesario)
        if (person.getBirthDateAsString() != null) {
            try {
                Date birthDate = convertStringToDate(person.getBirthDateAsString());
                person.setBirthDate(birthDate);  // Establecer la fecha en la entidad
            } catch (Exception e) {
                // Manejar el error de conversión, por ejemplo, lanzando una excepción personalizada
                throw new RuntimeException("Invalid birth date format", e);
            }
        }
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(Long id, Person person) {
        return personRepository.findById(id)
                .map(existingPerson -> {
                    // Actualiza la persona solo si existe
                    existingPerson.setFirstName(person.getFirstName());
                    existingPerson.setLastName(person.getLastName());
                    existingPerson.setEmail(person.getEmail());
                    existingPerson.setPhone(person.getPhone());
                    existingPerson.setAddress(person.getAddress());
                    if (person.getBirthDateAsString() != null) {
                        try {
                            Date birthDate = convertStringToDate(person.getBirthDateAsString());
                            existingPerson.setBirthDate(birthDate);  // Establecer la fecha convertida
                        } catch (Exception e) {
                            throw new RuntimeException("Invalid birth date format", e);
                        }
                    }
                    return personRepository.save(existingPerson);
                })
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @Override
    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    @Override
    public void save(Person person) {
        personRepository.save(person);
    }

    @Override
    public Optional<Person> findById(Long id) {
        return personRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (personOptional.isPresent()) {
            Person person = personOptional.get();

            // Eliminar el usuario asociado antes de eliminar la persona
            UserRepository.deleteByPerson(person);

            // Ahora sí, eliminar la persona
            personRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Persona con ID " + id + " no encontrada");
        }
    }


    @Override
    public List<Person> findPersonsByName(String name) {
        return personRepository.findByFirstNameContainingOrLastNameContaining(name, name);  // Buscar por nombre o apellido
    }

    @Override
    public Optional<Person> findPersonByEmail(String email) {
        return personRepository.findByEmail(email);
    }

    @Override
    public List<Person> findPersonsByBirthDate(String birthDate) {
        try {
            Date parsedDate = convertStringToDate(birthDate);
            return findPersonsByBirthDate(parsedDate);
        } catch (Exception e) {
            throw new RuntimeException("Invalid birth date format", e);
        }
    }

    @Override
    public List<Person> findPersonsByBirthDate(Date birthDate) {
        return personRepository.findByBirthDate(birthDate);  // Suponiendo que el formato sea 'yyyy-MM-dd'
    }

    @Override
    public List<Person> findPersonsByPhone(String phone) {
        return personRepository.findByPhone(phone);
    }

    @Override
    public List<Person> findPersonsByAddress(String address) {
        return personRepository.findByAddressContaining(address);  // Buscar por dirección
    }

    @Override
    public Optional<Person> findPersonByDocument(String document) {
        return personRepository.findByDocument(document);
    }

    // Método para convertir String a Date
    public Date convertStringToDate(String dateString) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  // Ajusta el formato si es necesario
        return formatter.parse(dateString);  // Devuelve la fecha como Date
    }
}
