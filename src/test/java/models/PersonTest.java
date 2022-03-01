package models;

import database.DB;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PersonTest {

    @Rule
    public  DatabaseRule database = new DatabaseRule();

    @BeforeEach
     void beforeEach() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/virtual_pets_test", "moringa", "access");

    }


    @AfterEach
    void aferEach(){
        try(Connection con = DB.sql2o.open()) {
            String deletePersonsQuery = "DELETE FROM persons *;";
            con.createQuery(deletePersonsQuery).executeUpdate();
        }
    }
    @Test
    @DisplayName("Person instantiates correctly")
    public void person_instantiatesCorrectly_true(){
        Person person = new Person("Nicholas","barkotenicholas@gmail.com");
        assertTrue(person instanceof Person);
    }

    @Test
    @DisplayName("Person instantiates with Name")
    public void getName_personInstantiatesWithName_String(){
        Person testPerson = new Person("Nicholas","barkotenicholas@gmail.com");
        assertEquals("Nicholas",testPerson.getName());
    }

    @Test
    @DisplayName("Person instantiates with Email")
    public void getEmail_personInstantiatesWithEmail_String(){
        Person testPerson = new Person("Nicholas","barkotenicholas@gmail.com");
        assertEquals("barkotenicholas@gmail.com",testPerson.getEmail());
    }

    @Test
    @DisplayName("Eqauls returns true if Person objects are same")
    public void equals_returnsTrueIfNameAndEmailSReSame_true(){
        Person firstPerson = new Person("Nicholas","barkotenicholas@gmail.com");
        Person secondPerson = new Person("Nicholas","barkotenicholas@gmail.com");

        assertTrue(firstPerson.equals(secondPerson));
    }

    @Test
    @DisplayName("All returns all instance of person")
    public void all_returnsAllInstanceOfPerson_true(){
        Person testPerson = new Person("Nicholas","barkotenicholas@gmail.com");
        testPerson.save();
        Person secondPerson = new Person("Barkote","barkote@gmail.com");
        secondPerson.save();
        assertTrue(Person.all().get(0).equals(testPerson));
        assertTrue(Person.all().get(1).equals(secondPerson));
    }

    @Test
    @DisplayName("Saves assigns if to object")
    public void saves_AssignsIdToObject(){
        Person testPerson = new Person("Henry", "henry@henry.com");
        testPerson.save();
        Person savedPerson = Person.all().get(0);
        assertEquals(testPerson.getId(), savedPerson.getId());
    }

    @Test
    @DisplayName("Search for Person an with an id")
    public void find_returnsWithSameId_Person(){
        Person firstPerson = new Person("Henry", "henry@henry.com");
        firstPerson.save();
        Person secondPerson = new Person("Harriet", "harriet@harriet.com");
        secondPerson.save();
        assertEquals(Person.find(secondPerson.getId()),secondPerson);
    }

}