import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BlankTest {

    @ParameterizedTest
    @DisplayName("isBlank: should return True for null or blank strings")
    @ValueSource(strings = {"  ", "\t", "\n"})
    @NullAndEmptySource
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input) {

        assertTrue(Blank.isBlank(input));
    }

    // to pass complex objects, use a method as an argument source
    @ParameterizedTest
    @MethodSource("provideStringsForIsBlank")  // name supplied should match an existing method
    void isBlank_ShouldReturnTrueForNullOrBlankStrings(String input, boolean expected){
        assertEquals(expected, Blank.isBlank(input));
    }

    private static Stream<Arguments> provideStringsForIsBlank(){
        return Stream.of(
            Arguments.of(null, true),
            Arguments.of("", true),
            Arguments.of("  ", true),
            Arguments.of("not blank", false)
        );
    }

}















