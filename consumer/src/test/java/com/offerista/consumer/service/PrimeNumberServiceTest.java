package com.offerista.consumer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrimeNumberServiceTest {

    private PrimeNumberService primeNumberService;

    @BeforeEach
    void setUp() {
        primeNumberService = new PrimeNumberService();
    }

    @Test
    void testExtractPrimeNumbers_withPrimes() {
        List<Integer> numbers = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        List<Integer> expectedPrimes = List.of(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);

        List<Integer> result = primeNumberService.extractPrimeNumbers(numbers);

        assertEquals(expectedPrimes, result);
    }

    @Test
    void testExtractPrimeNumbers_withNonPrimes() {
        List<Integer> numbers = List.of(4, 6, 8, 9, 10, 12, 14, 15, 16, 18);
        List<Integer> expectedPrimes = Collections.emptyList();

        List<Integer> result = primeNumberService.extractPrimeNumbers(numbers);

        assertEquals(expectedPrimes, result);
    }

    @Test
    void testExtractPrimeNumbers_withMixedNumbers() {
        List<Integer> numbers = List.of(2, 4, 6, 7, 8, 9, 10, 11, 13, 14);
        List<Integer> expectedPrimes = List.of(2, 7, 11, 13);

        List<Integer> result = primeNumberService.extractPrimeNumbers(numbers);

        assertEquals(expectedPrimes, result);
    }

    @Test
    void testExtractPrimeNumbers_withNegativeAndZero() {
        List<Integer> numbers = List.of(-10, -1, 0, 1, 2, 3, 5);
        List<Integer> expectedPrimes = List.of(2, 3, 5);

        List<Integer> result = primeNumberService.extractPrimeNumbers(numbers);

        assertEquals(expectedPrimes, result);
    }

    @Test
    void testExtractPrimeNumbers_withEmptyList() {
        List<Integer> numbers = Collections.emptyList();
        List<Integer> expectedPrimes = Collections.emptyList();

        List<Integer> result = primeNumberService.extractPrimeNumbers(numbers);

        assertEquals(expectedPrimes, result);
    }
}
