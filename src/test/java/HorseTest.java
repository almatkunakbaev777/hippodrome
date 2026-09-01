import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class HorseTest {


    @Test
    void constructorShouldThrowIAEWithCorrectMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(null, 2.0, 10.0);
        });
        String message = exception.getMessage();
        assertEquals("Name cannot be null.", message);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "\t", "\n"})
    void nameNotConsistSpace(String name) {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            new Horse(name, 2.0, 10.0);
        });
        String message = exception.getMessage();
        assertEquals("Name cannot be blank.", message);
    }

    @Test
    void speedNotNegative() {

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Horse("sad", -1.0, 10.0);});

        String message = exception.getMessage();
        assertEquals("Speed cannot be negative.", message);
    }

    @Test
    void distanceNotNegative() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Horse("sad", 1.0, -10.0);});
        String message = exception.getMessage();
        assertEquals("Distance cannot be negative.", message);
    }
    @Test
    void getName() {
        Horse horse = new Horse("Ben", 1.0, 10.0);
        assertEquals("Ben", horse.getName());
    }

    @Test
    void getSpeed() {
        Horse horse = new Horse("Ben", 1.0, 10.0);
        assertEquals(1.0, horse.getSpeed(), 0.0000001);

    }

    @Test
    void getDistance() {
        Horse horse = new Horse("Ben", 1.0, 10.0);
        assertEquals(10.0, horse.getDistance(), 0.0000001);

    }
    @Test
    void distanceZeroIfTwoParams() {
        Horse horse = new Horse("Ben", 1.0);
        assertEquals(0, horse.getDistance(), 0.0000001);
    }


    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4})
    void move(double arg) {
        try(MockedStatic<Horse> mockedHorse = Mockito.mockStatic(Horse.class)) {
            mockedHorse.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(arg);
            Horse horse = new Horse("Ben", 1.0, 10.0);
            horse.move();
            assertEquals(10.0 + 1.0 *  arg, horse.getDistance(), 0.0000001);
            mockedHorse.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }
}