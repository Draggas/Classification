package fr.univlille.iut.sae302;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TestData {
    private Data<Iris> data;

    @BeforeEach
    public void init(){
        data = new Data<Iris>(new ArrayList<Iris>());
    }

    @Test
    public void testIsEmpty(){
        assertTrue(data.isEmpty());
    }

    @Test
    public void testIsNotEmpty(){
        data.getEData().add(new Iris(1.0, 1.0, 1.0, 1.0, "Setosa"));
        assertFalse(data.isEmpty());
    }

    @Test
    public void testGetMaxData(){
        data.getEData().add(new Iris(2.0, 1.3, 1.5, 1.0, "Setosa"));
        data.getEData().add(new Iris(1.0, 3.0, 1.4, 1.0, "Setosa"));

        assertEquals(2.0, data.getMaxData("Sepal Length"));
        assertEquals(3.0, data.getMaxData("Sepal Width"));
        assertEquals(1.5, data.getMaxData("Petal Length"));
        assertEquals(1.0, data.getMaxData("Petal Width"));
        assertEquals(0, data.getMaxData(""));

    }

    @Test
    public void testGetMinData(){
        data.getEData().add(new Iris(2.0, 1.3, 1.5, 1.0, "Setosa"));
        data.getEData().add(new Iris(1.0, 3.0, 1.4, 1.0, "Setosa"));

        assertEquals(1.0, data.getMinData("Sepal Length"));
        assertEquals(1.3, data.getMinData("Sepal Width"));
        assertEquals(1.4, data.getMinData("Petal Length"));
        assertEquals(1.0, data.getMinData("Petal Width"));
        assertEquals(0, data.getMinData(""));
    }
}
