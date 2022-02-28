package models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

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
}