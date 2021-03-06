package models;

import database.DB;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.sql2o.Connection;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MonsterTest {

    @AfterEach
    public void afterEach(){
        try(Connection con = DB.sql2o.open()) {
            String deletePersonsQuery = "DELETE FROM persons *;";
            String deleteMonstersQuery = "DELETE FROM monsters *;";
            con.createQuery(deletePersonsQuery).executeUpdate();
            con.createQuery(deleteMonstersQuery).executeUpdate();
        }
    }

    @Test
    @DisplayName("Monster instantiates correctly")
    public void monster_instantiatesCorrectly_true(){
        Monster monster = new Monster("Bubbles",1);
        assertTrue(monster instanceof Monster);
    }

    @Test
    public void Monster_instantiatesWithName_String() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals("Bubbles", testMonster.getName());
    }

    @Test
    public void Monster_instantiatesWithPersonId_int() {
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(1, testMonster.getPersonId());
    }

    @Test
    public void equals_returnsTrueIfNameAndPersonIdAreSame_true() {
        Monster testMonster = new Monster("Bubbles", 1);
        Monster anotherMonster = new Monster("Bubbles", 1);
        assertTrue(testMonster.equals(anotherMonster));
    }

    @Test
    public void save_returnsTrueIfDescriptionsAretheSame() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        assertTrue(Monster.all().get(0).equals(testMonster));
    }
    @Test
    public void save_assignsIdToMonster() {
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.save();
        Monster savedMonster = Monster.all().get(0);
        assertEquals(savedMonster.getId(), testMonster.getId());
    }
    @Test
    public void find_returnsMonsterWithSameId_secondMonster() {
        Monster firstMonster = new Monster("Bubbles", 1);
        firstMonster.save();
        Monster secondMonster = new Monster("Spud", 3);
        secondMonster.save();
        assertEquals(Monster.find(secondMonster.getId()), secondMonster);
    }

    @Test
    public void save_savesPersonIdIntoDB_true() {
        Person testPerson = new Person("Henry", "henry@henry.com");
        testPerson.save();
        Monster testMonster = new Monster("Bubbles", testPerson.getId());
        testMonster.save();
        Monster savedMonster = Monster.find(testMonster.getId());
        assertEquals(savedMonster.getPersonId(), testPerson.getId());
    }

    @Test
    public void getMonsters_retrievesAllMonstersFromDatabase_monstersList() {
        Person testPerson = new Person("Henry", "henry@henry.com");
        testPerson.save();
        Monster firstMonster = new Monster("Bubbles", testPerson.getId());
        firstMonster.save();
        Monster secondMonster = new Monster("Spud", testPerson.getId());
        secondMonster.save();
        Monster[] monsters = new Monster[] { firstMonster, secondMonster };
        assertTrue(testPerson.getMonsters().containsAll(Arrays.asList(monsters)));
    }

    @Test
    public void monster_instantiatesWithHalfFullPlayLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2));
    }
    @Test
    public void monster_instantiatesWithHalfFullSleepLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2));
    }
    @Test
    public void monster_instantiatesWithHalfFullFoodLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2));
    }

    @Test
    public void isAlive_confirmsMonsterIsAliveIfAllLevelsAboveMinimum_true(){
        Monster testMonster = new Monster("Bubbles", 1);
        assertEquals(testMonster.isAlive(), true);
    }
    @Test
    public void depleteLevels_reducesAllLevels(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.depleteLevels();
        assertEquals(testMonster.getFoodLevel(), (Monster.MAX_FOOD_LEVEL / 2) - 1);
        assertEquals(testMonster.getSleepLevel(), (Monster.MAX_SLEEP_LEVEL / 2) - 1);
        assertEquals(testMonster.getPlayLevel(), (Monster.MAX_PLAY_LEVEL / 2) - 1);
    }
    @Test
    public void isAlive_recognizesMonsterIsDeadWhenLevelsReachMinimum_false(){
        Monster testMonster = new Monster("Bubbles", 1);
        for(int i = Monster.MIN_ALL_LEVELS; i <= Monster.MAX_FOOD_LEVEL; i++){
            testMonster.depleteLevels();
        }
        assertEquals(testMonster.isAlive(), false);
    }
    @Test
    public void play_increasesMonsterPlayLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.play();
        assertTrue(testMonster.getPlayLevel() > (Monster.MAX_PLAY_LEVEL / 2));
    }
    @Test
    public void sleep_increasesMonsterSleepLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.sleep();
        assertTrue(testMonster.getSleepLevel() > (Monster.MAX_SLEEP_LEVEL / 2));
    }
    @Test
    public void feed_increasesMonsterFoodLevel(){
        Monster testMonster = new Monster("Bubbles", 1);
        testMonster.feed();
        assertTrue(testMonster.getFoodLevel() > (Monster.MAX_FOOD_LEVEL / 2));
    }
}