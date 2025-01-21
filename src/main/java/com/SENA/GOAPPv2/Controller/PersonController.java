package com.SENA.GOAPPv2.Controller;

import com.SENA.GOAPPv2.Entity.Person;
import com.SENA.GOAPPv2.Service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/person")
public class PersonController {

    @Autowired
    private PersonService personService;

    /**
     * Crear una nueva persona.
     * @param person Objeto Person recibido en el cuerpo de la solicitud.
     * @return La persona creada.
     */
    @PostMapping("")
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        return ResponseEntity.status(201).body(personService.createPerson(person));
    }

    /**
     * Actualizar una persona existente por su ID.
     * @param person Objeto Person con los datos actualizados.
     * @param id ID de la persona a actualizar.
     * @return La persona actualizada o un error 404 si no se encuentra.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person, @PathVariable Long id) {
        Optional<Person> existingPerson = personService.getPersonById(id);
        if (existingPerson.isPresent()) {
            return ResponseEntity.ok(personService.updatePerson(id, person));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Obtener todas las personas.
     * @return Lista de todas las personas.
     */
    @GetMapping("")
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> persons = personService.getAllPersons();
        if (persons.isEmpty()) {
            return ResponseEntity.noContent().build(); // Si no hay personas, devuelve código 204
        }
        return ResponseEntity.ok(persons); // Si hay personas, las devuelve con código 200
    }

    /**
     * Obtener una persona por su ID.
     * @param id ID de la persona.
     * @return La persona encontrada o un error 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        Optional<Person> person = personService.getPersonById(id);
        return person.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Eliminar una persona por su ID.
     * @param id ID de la persona a eliminar.
     * @return Respuesta vacía con código 204 si se elimina correctamente, o un error 404 si no existe.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        Optional<Person> existingPerson = personService.getPersonById(id);
        if (existingPerson.isPresent()) {
            personService.deletePerson(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
