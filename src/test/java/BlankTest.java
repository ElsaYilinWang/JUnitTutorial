import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.util.Arrays;
import java.util.List;
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

    // Field Source: starting with JUnit 5.11
    // update pom.xml to 5.11.x and add relevant dependency

    static List<String> cities = Arrays.asList("Madrid", "Rome", "Paris", "London");

    @ParameterizedTest
    @FieldSource("cities")
    void isBlank_ShouldReturnFalseWhenTheArgHasAtLeastOneCharacter(String arg){
        assertFalse(Blank.isBlank(arg));
    }

    // A different way to write Field Source
    static String[] isEmpty_ShouldReturnFalseWhenTheArgHasAtLeastOneCharacter = {
        "Spain", "Italy", "France", "Engliand"
    };

    @ParameterizedTest
    @FieldSource
    void setIsEmpty_ShouldReturnFalseWhenTheArgHasAtLeastOneCharacter(String arg){
        assertFalse(arg.isEmpty());
    }



}















