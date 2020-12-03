package wildlifetracker;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
    @Rule
    public DatabaseRule database = new DatabaseRule();
    @Test
    public void endangered_instantiatesCorrectly_true() {
        Endangered testendered = new Endangered("Cat", "canvore","okay","young");
        assertEquals(true, testendered instanceof Endangered);
    }
    @Test
    public void getName_endangeredInstantiate() {
        Endangered testendangered = new Endangered("cat", "canvore","okay","young");
        assertEquals("cat", testendangered.getName());
    }
    @Test
    public void getType_endInstantiatesWithType_String() {
        Endangered testndegered = new Endangered("cat", "canvore","okay","young");
        assertEquals("canvore", testndegered.getType());
    }
    @Test
    public void equals_returnsTrueIfNameAndType_true() {
        Endangered firstendegered = new Endangered("cat", "canvore","okay","young");
        Endangered anotherendered = new Endangered("cat", "canvore","okay","young");
        assertTrue(firstendegered.equals(anotherendered));
    }


    @Test
    public void save_assignsIdToObject() {
        Endangered testend = new Endangered("cat", "canvore","okay","young");
        testend.save();
        AnimalType savedanimal = Endangered.all().get(0);
        assertEquals(testend.getId(), savedanimal.getId());
    }


}