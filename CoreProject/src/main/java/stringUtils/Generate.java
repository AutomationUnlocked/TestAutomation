package stringUtils;

import java.util.Random;

public class Generate {


    // Method to generate a random email address
    public static String generateRandomEmail() {
        String[] domains = {"mail.com", "qrst.com", "abcd.com", "example.com"};
        String username = generateRandomString(8, true);
        String domain = domains[new Random().nextInt(domains.length)];
        return username + "@" + domain;
    }

    // Method to generate a random 8-character password
    public static String generateRandomPassword(int length) {
        String upperCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String specialCharacters = "!@#$%^&*()-_=+<>?";
        String allCharacters = upperCaseLetters + lowerCaseLetters + numbers + specialCharacters;

        StringBuilder password = new StringBuilder();

        Random random = new Random();

        // Ensure at least one character of each type is included
        password.append(upperCaseLetters.charAt(random.nextInt(upperCaseLetters.length())));
        password.append(lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length())));
        password.append(numbers.charAt(random.nextInt(numbers.length())));
        password.append(specialCharacters.charAt(random.nextInt(specialCharacters.length())));

        // Fill the remaining characters randomly
        for (int i = 4; i < length; i++) {
            password.append(allCharacters.charAt(random.nextInt(allCharacters.length())));
        }

        // Shuffle the password for randomness
        return shuffleString(password.toString());
    }

    // Helper method to generate a random string
    public static String generateRandomString(int length, boolean isAlphanumeric) {
        String characters = isAlphanumeric ? "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789" : "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    // Helper method to generate a random string
    public static String generateRandomInteger(int length) {
        String characters = "0123456789";
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }
        return result.toString();
    }

    // Helper method to shuffle a string
    private static String shuffleString(String input) {
        char[] characters = input.toCharArray();
        Random random = new Random();
        for (int i = 0; i < characters.length; i++) {
            int j = random.nextInt(characters.length);
            char temp = characters[i];
            characters[i] = characters[j];
            characters[j] = temp;
        }
        return new String(characters);
    }

    public static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder number = new StringBuilder();
        // Ensure the first digit is non-zero
        number.append(random.nextInt(9) + 1);
        // Generate the remaining digits
        for (int i = 1; i < length; i++) {
            number.append(random.nextInt(10));
        }
        return number.toString();
    }
}

