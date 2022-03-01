package models;

import database.DB;
import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Monster {

    private String name;
    private int personId;
    private int id;
    private int foodLevel;
    private int sleepLevel;
    private int playLevel;

    public static final int MAX_FOOD_LEVEL = 3;
    public static final int MAX_SLEEP_LEVEL = 8;
    public static final int MAX_PLAY_LEVEL = 12;
    public static final int MIN_ALL_LEVELS = 0;

    public Monster(String name, int personId) {
        this.name = name;
        this.personId = personId;
        playLevel = MAX_PLAY_LEVEL / 2;
        sleepLevel = MAX_SLEEP_LEVEL / 2;
        foodLevel = MAX_FOOD_LEVEL / 2;

    }

    public String getName() {
        return name;
    }

    public int getPersonId() {
        return personId;
    }

    public int getId() {
        return id;
    }

    public int getFoodLevel() {
        return foodLevel;
    }

    public int getSleepLevel() {
        return sleepLevel;
    }

    public int getPlayLevel() {
        return playLevel;
    }

    public boolean isAlive() {
        if (foodLevel <= MIN_ALL_LEVELS ||
                playLevel <= MIN_ALL_LEVELS ||
                sleepLevel <= MIN_ALL_LEVELS) {
            return false;
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monster monster = (Monster) o;
        return personId == monster.personId && Objects.equals(name, monster.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, personId);
    }
    public static List<Monster> all() {
        String sql = "SELECT * FROM monsters";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Monster.class);
        }
    }
    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO monsters (name, personid) VALUES (:name, :personId)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("personId", this.personId)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static Monster find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM monsters where id=:id";
            return con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Monster.class);
        }
    }

    public void depleteLevels(){
        playLevel--;
        foodLevel--;
        sleepLevel--;
    }

    public void play(){
        playLevel++;
    }
    public void sleep(){
        sleepLevel++;
    }
    public void feed(){
        foodLevel++;
    }
}
