import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DummyTest {
    @Test
    void assertionWorked() {
        int actual = 1 + 1;
        int expected = 2;
        assertEquals(expected,actual);
    }
}
