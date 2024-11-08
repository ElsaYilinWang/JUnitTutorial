import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

import java.lang.annotation.*;
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

    // Custom Argument Provider: custom implementation of an interface ArgumentsProvider
    @ParameterizedTest
    @ArgumentsSource(BlankStringsArgumentsProvider.class)
    void isBlank_ShouldReturnTrueForNullOrBlankStringsArgProvider(String input) {
        assertTrue(Blank.isBlank(input));
    }

    // Custom Annotation
    static Stream<Arguments> arguments = Stream.of(
        Arguments.of(null, true), // null strings should be considered blank
            Arguments.of("", true),
            Arguments.of("  ", true),
            Arguments.of("not blank", false)
    );

    @ParameterizedTest
    @VariableSource("arguments") // JUnit 5 not provide, needs customization, see below
    void isBlank_ShouldReturnTrueForNullOrBlankStringsVariableSource(
            String input, boolean expected) {
        assertEquals(expected, Blank.isBlank(input));
    }

    @Documented
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)
    @ArgumentsSource(VariableArgumentProvider.class)
    public @interface VariableSource {
        /**
         * The name of the static variable
         */

        String value();
    }
}

class BlankStringsArgumentsProvider implements ArgumentsProvider {
    @Override
    public Stream<? extends Arguments> provideArguments(ExtensionContext context){
        return Stream.of(
            Arguments.of((String) null),
                Arguments.of(""),
                Arguments.of("   ")
        );
    }
}

class VariableArgumentsProvider implements ArgumentsProvider, AnnotationConsumer<VariableSource> {
    private String variableName;

    @Override
    public Stream<? extends Arguments> providerArguments(ExtensionContext context) {
        return context.getTestClass()
                .map(this::getField)
                .map(this::getValue)
                .orElseThrow( () ->
                        new IllegalArgumentException("Failed to load test arguments"));
    }

    @Override
    public void accept(VariableSource variableSource){
        variableName = variableSource.value();
    }

    private Field getField(Class<?> clazz) {
        try {
            return clazz.getDeclaredField(variableName);
        } catch (Exception e){
            return null;
        }
    }

    @SupportWarnings("unchecked")
    private Stream<Arguments> getValue(Field field){
        Object value = null;
        try {
            value = field.get(null);
        } catch (Exception ignored){
        return value == null ? null : (Stream<Arguments>) value;
        }
    }
}














