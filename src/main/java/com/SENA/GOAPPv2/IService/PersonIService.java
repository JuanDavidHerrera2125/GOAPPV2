package com.SENA.GOAPPv2.IService;

import com.SENA.GOAPPv2.Entity.Person;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface PersonIService {

    // Métodos CRUD básicos

    /**
     * Obtener todas las personas.
     * @return Lista de todas las personas.
     */
    List<Person> getAllPersons();

    /**
     * Obtener una persona por su ID.
     * @param id ID de la persona.
     * @return Persona encontrada o un Optional vacío si no existe.
     */
    Optional<Person> getPersonById(Long id);

    /**
     * Crear una nueva persona.
     * @param person Objeto Person con los datos a guardar.
     * @return La persona creada.
     */
    Person createPerson(Person person);

    /**
     * Actualizar una persona existente.
     * @param id ID de la persona a actualizar.
     * @param person Objeto Person con los datos actualizados.
     * @return La persona actualizada.
     */
    Person updatePerson(Long id, Person person);

    /**
     * Eliminar una persona por su ID.
     * @param id ID de la persona a eliminar.
     */
    void deletePerson(Long id);

    // Métodos relacionados con persistencia

    /**
     * Guardar una persona en la base de datos.
     * @param person Objeto Person a guardar.
     */
    void save(Person person);

    /**
     * Buscar una persona por su ID.
     * @param id ID de la persona.
     * @return Persona encontrada o un Optional vacío si no existe.
     */
    Optional<Person> findById(Long id);

    /**
     * Eliminar una persona por su ID.
     * @param id ID de la persona.
     */
    void deleteById(Long id);

    // Métodos de búsqueda avanzada

    /**
     * Buscar personas por su nombre.
     * @param name Nombre de la persona.
     * @return Lista de personas que coincidan con el nombre.
     */
    List<Person> findPersonsByName(String name);

    /**
     * Buscar una persona por su email.
     * @param email Email de la persona.
     * @return Persona encontrada o un Optional vacío si no existe.
     */
    Optional<Person> findPersonByEmail(String email);

    /**
     * Buscar personas por su fecha de nacimiento.
     * @param birthDate Fecha de nacimiento en formato yyyy-MM-dd.
     * @return Lista de personas que coincidan con la fecha de nacimiento.
     */
    List<Person> findPersonsByBirthDate(String birthDate);

    List<Person> findPersonsByBirthDate(Date birthDate);

    /**
     * Buscar personas por su número de teléfono.
     * @param phone Número de teléfono de la persona.
     * @return Lista de personas que coincidan con el número de teléfono.
     */
    List<Person> findPersonsByPhone(String phone);

    /**
     * Buscar personas por su dirección.
     * @param address Dirección de la persona.
     * @return Lista de personas que coincidan con la dirección.
     */
    List<Person> findPersonsByAddress(String address);

    /**
     * Buscar una persona por su documento.
     * @param document Documento de la persona.
     * @return Persona encontrada o un Optional vacío si no existe.
     */
    Optional<Person> findPersonByDocument(String document);


}
