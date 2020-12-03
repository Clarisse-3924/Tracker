package wildlifetracker;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;


public class AnimalTypeTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();
    @Test
    public void animal_instantiatesCorrectly_true() {
        AnimalType testanimal = new AnimalType("Cat", "canvore");
        assertEquals(true, testanimal instanceof AnimalType);
    }



}