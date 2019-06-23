package ar.edu.itba.cep.playground_service.domain;

import ar.edu.itba.cep.playground_service.models.ExecutionRequest;
import ar.edu.itba.cep.playground_service.models.ExecutionResult;
import ar.edu.itba.cep.playground_service.models.Language;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for testing.
 */
class TestHelper {

    /**
     * Indicates how many elements must have the created {@link String} {@link List}s used for testing
     * (i.e to be used as inputs and expected outputs).
     */
    private static final int STRING_LISTS_SIZE = 10;


    // ================================================================================================================
    // Valid values
    // ================================================================================================================

    /**
     * @return A valid {@link ExecutionRequest} id.
     */
    /* package */
    static long validExecutionRequestId() {
        return Faker.instance().number().numberBetween(1L, Long.MAX_VALUE);
    }

    /**
     * @return A valid code {@link String}.
     */
    /* package */
    static String validCode() {
        return Faker.instance()
                .lorem()
                .paragraph();
    }

    /**
     * @return A valid timeout.
     */
    /* package */
    static Long validTimeout() {
        final List<Long> validValues = new LinkedList<>();
        validValues.add(null);
        validValues.add(Faker.instance().number().numberBetween(1, Long.MAX_VALUE));
        final var index = (int) Faker.instance().number().numberBetween(0L, validValues.size());
        return validValues.get(index);
    }

    /**
     * @return a valid {@link Language}.
     */
    /* package */
    static Language validLanguage() {
        final var languages = Language.values();
        final var index = (int) Faker.instance().number().numberBetween(0L, languages.length);
        return languages[index];
    }

    /**
     * @return A valid {@link ExecutionResult} id.
     */
    /* package */
    static long validExecutionResultId() {
        return Faker.instance().number().numberBetween(1L, Long.MAX_VALUE);
    }

    /**
     * @return A valid exit code.
     */
    /* package */
    static int validExitCode() {
        return Faker.instance()
                .number()
                .numberBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * Creates a valid {@link List} of {@link String} to be used as inputs or outputs (stdout/stderr).
     *
     * @return A valid {@link List} of inputs/outputs.
     */
    /* package */
    static List<String> validInputOutputList() {
        return Faker.instance()
                .lorem()
                .words(STRING_LISTS_SIZE);
    }

    /**
     * @return A random compiled {@link Language}.
     */
    /* package */
    static Language compiledLanguage() {
        final var compiledLanguages = Arrays.stream(Language.values())
                .filter(Language::isCompiled)
                .collect(Collectors.toList());
        final var index = (int) Faker.instance().number().numberBetween(0L, compiledLanguages.size());
        return compiledLanguages.get(index);
    }

    /**
     * @return A random non-compiled {@link Language}.
     */
    /* package */
    static Language nonCompiledLanguage() {
        final var nonCompiledLanguages = Arrays.stream(Language.values())
                .filter(Predicate.not(Language::isCompiled))
                .collect(Collectors.toList());
        final var index = (int) Faker.instance().number().numberBetween(0L, nonCompiledLanguages.size());
        return nonCompiledLanguages.get(index);
    }


    // ================================================================================================================
    // Invalid values
    // ================================================================================================================

    /**
     * @return An invalid code.
     */
    /* package */
    static String invalidCode() {
        return null;
    }

    /**
     * @return An invalid {@link List} of {@link String} for to be used as inputs or outputs (stdout/stderr).
     */
    /* package */
    static List<String> invalidInputOutputList() {
        final var possibleValues = new LinkedList<List<String>>();
        possibleValues.add(null);
        final var listWithNulls = Stream.concat(
                Faker.instance().lorem().words(STRING_LISTS_SIZE - 1).stream(),
                Stream.of((String) null)
        ).collect(Collectors.toList());
        Collections.shuffle(listWithNulls); // Perform shuffling to be sure that check is performed in all the list
        possibleValues.add(listWithNulls);

        final var index = Faker.instance()
                .number()
                .numberBetween(0, possibleValues.size());
        return possibleValues.get(index);
    }

    /**
     * @return A random non positive long that represents a timeout.
     */
    /* package */
    static long nonPositiveTimeout() {
        return Faker.instance().number().numberBetween(Long.MIN_VALUE, 1);
    }

    /**
     * @return An invalid {@link Language}.
     */
    /* package */
    static Language invalidLanguage() {
        return null;
    }

}
