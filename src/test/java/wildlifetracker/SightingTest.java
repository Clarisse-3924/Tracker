package wildlifetracker;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class SightingTest {

    @Rule
    public DatabaseRule database = new DatabaseRule();
    @Test
    public void Sighting_instantiatesCorrectly_true() {
        Sighting testsight = new Sighting(1,1,3);
        assertEquals(true, testsight instanceof Sighting);
    }

    @Test
    public void getanimalid_animalInstantiatesWithName_Henry() {
        Sighting testsight= new Sighting(1,1, 1);
        assertEquals("cat", testsight.getAnimal_id());
    }
    @Test
    public void getType_animalInstantiatesWithType_String() {
        AnimalType testanimal = new AnimalType("cat", "Canivore");
        assertEquals("Canivore", testanimal.getType());
    }
    @Test
    public void equals_returnsTrueIfNameAndType_true() {
        AnimalType firstanimal = new AnimalType("cat", "carnovre");
        AnimalType anotheranimal = new AnimalType("cat", "endegered");
        assertTrue(firstanimal.equals(anotheranimal));
    }
    @Test
    public void save_insertsObjectIntoDatabase_animal() {
        AnimalType testanimal = new AnimalType("cat", "endegered");
        testanimal.save();
        assertTrue(AnimalType.all().get(0).equals(testanimal));
    }
    @Test
    public void find_returnsanimalWithSameId_secondanimal() {
        AnimalType firstanimal = new AnimalType("cat", "canvore");
        firstanimal.save();
        AnimalType secondanimal = new AnimalType("cow", "hebvore");
        secondanimal.save();
        assertEquals(AnimalType.find(secondanimal.getId()), secondanimal);
    }
    @Test
    public void all_returnsAllInstancesOfAnimal_true() {
        AnimalType firstanimal = new AnimalType("cow", "endegered");
        firstanimal.save();
        AnimalType secondanimal = new AnimalType("lion", "canvore");
        secondanimal.save();
        assertEquals(true, AnimalType.all().get(0).equals(firstanimal));
        assertEquals(true, AnimalType.all().get(1).equals(secondanimal));
    }
    @Test
    public void save_assignsIdToObject() {
        AnimalType testanimal = new AnimalType("hen", "heb");
        testanimal.save();
        AnimalType savedanimal = AnimalType.all().get(0);
        assertEquals(testanimal.getId(), savedanimal.getId());
    }}