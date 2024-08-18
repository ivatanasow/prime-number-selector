package com.offerista.consumer.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrimeNumberService {

    public List<Integer> extractPrimeNumbers(List<Integer> numbers) {
        return numbers.stream().filter(this::isPrime).toList();
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        if (number % 2 == 0) {
            return false;
        }

        int sqrt = (int) Math.sqrt(number);
        for (int i = 3; i <= sqrt; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

}
