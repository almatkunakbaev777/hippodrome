import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HippodromeTest {

    @Test
    void horsesIsNotNull() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Hippodrome(null);});
        String message = exception.getMessage();
        assertEquals("Horses cannot be null.", message);
    }

    @Test
    void whenListOfHorsesIsEmptyThenThrowIAEWithCorrectMessage() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {new Hippodrome(new ArrayList<>());});
        String message = exception.getMessage();
        assertEquals("Horses cannot be empty.", message);
    }

    @Test
    void getHorses() {
        List<Horse> horses = IntStream.rangeClosed(1, 30).mapToObj(i ->
             new Horse("Horse " + i, 0, i )).collect(Collectors.toList());

        Hippodrome test = new Hippodrome(horses);
        assertEquals(horses, test.getHorses());
    }



    @Test
    void move() {
        List<Horse> horses = IntStream.rangeClosed(1, 50)
                .mapToObj(i -> mock(Horse.class))
                .collect(Collectors.toList());
        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();
        for (Horse horse : horses) {
            verify(horse).move();
        }
    }

    @Test
    void getWinner() {
        Horse horse1 = mock(Horse.class);
        Horse horse2 =  mock(Horse.class);
        Horse horse3 = mock(Horse.class);

        when(horse1.getDistance()).thenReturn(100.0);
        when(horse2.getDistance()).thenReturn(250.0);
        when(horse3.getDistance()).thenReturn(150.0);

        List<Horse> horses = List.of(horse1, horse2, horse3);
        Hippodrome hippodrome = new Hippodrome(horses);

        Horse winner = hippodrome.getWinner();

        assertEquals(horse2, winner);
    }
}