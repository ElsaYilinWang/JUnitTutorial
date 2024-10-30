import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class BlankTest {

    @ParameterizedTest
    @DisplayName("isBlank: should return True for null or blank strings")
    @ValueSource(strings = {"  ", "\t", "\n"})
    @NullAndEmptySource
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {
        assertTrue(Blank.isBlank(input));
    }


}