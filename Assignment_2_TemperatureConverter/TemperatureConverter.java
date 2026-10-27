// APAN 5175 - Assignment 2: Method Mastery (Design & Parameter Passing)
//Author: Hanqing Wang (hw3146)

import java.util.Arrays;
import java.util.Scanner;

public class TemperatureConverter {

    // Conversion between Celsius and Kelvin (K = C + 273.15)
    private static final double KELVIN_OFFSET = 273.15;

    // Absolute zero in each scale: no temperature can be lower than this
    private static final double ABSOLUTE_ZERO_C = -273.15;
    private static final double ABSOLUTE_ZERO_F = -459.67;
    private static final double ABSOLUTE_ZERO_K = 0.0;

    // Core conversion methods (return type: double, see [4])
    /** 
    * Converts a Celsius temperature to the target scale.
    *
    * @param celsius     the temperature in Celsius
    * @param targetScale "F" for Fahrenheit, "K" for Kelvin, or "C" 
    * @return the converted temperature
    */
    public static double convertCelsius(double celsius, String targetScale) {

        if (!isValidTemperature(celsius, "C")) {
            throw new IllegalArgumentException(
                "Invalid temperature: " + celsius + "°C is below absolute zero (-273.15°C).");
        }

        switch (targetScale.toUpperCase()) {
            case "F":
                return celsius * 9.0 / 5 + 32;
            case "K":
                return celsius + KELVIN_OFFSET;
            case "C":
                return celsius;
            default:
                throw new IllegalArgumentException("Unsupported target scale: " + targetScale);
        }
    }

    /**
     * Converts a Fahrenheit temperature to the target scale.
     * Converts to Celsius first, then reuses convertCelsius() for Kelvin.
     *
     * @param fahrenheit  the temperature in Fahrenheit
     * @param targetScale "C" for Celsius, "K" for Kelvin, or "F" (no change)
     * @return the converted temperature
     */
    public static double convertFahrenheit(double fahrenheit, String targetScale) {
        if (!isValidTemperature(fahrenheit, "F")) {
            throw new IllegalArgumentException(
                "Invalid temperature: " + fahrenheit + "°F is below absolute zero (-459.67°F).");
        }
        
        double celsius = (fahrenheit - 32) * 5.0 / 9;
        
        switch (targetScale.toUpperCase()) {
            case "C":
                return celsius;
            case "K":
                return convertCelsius(celsius, "K");
            case "F":
                return fahrenheit;
            default:
                throw new IllegalArgumentException("Unsupported target scale: " + targetScale);
        }
    }

    /**
     * Converts a Kelvin temperature to the target scale.
     * Converts to Celsius first, then reuses convertCelsius() for Fahrenheit.
     *
     * @param kelvin      the temperature in Kelvin
     * @param targetScale "C" for Celsius, "F" for Fahrenheit, or "K" (no change)
     * @return the converted temperature
     */
    public static double convertKelvin(double kelvin, String targetScale) {
        if (!isValidTemperature(kelvin, "K")) {
            throw new IllegalArgumentException(
                "Invalid temperature: " + kelvin + "K is below absolute zero (0K).");
        }
        
        double celsius = kelvin - KELVIN_OFFSET;
       
        switch (targetScale.toUpperCase()) {
            case "C":
                return celsius;
            case "F":
                return convertCelsius(celsius, "F");
            case "K":
                return kelvin;
            default:
                throw new IllegalArgumentException("Unsupported target scale: " + targetScale);
        }
    }

    /**
     * General-purpose converter: calls the correct conversion method
     * based on the source scale.
     *
     * @param value       the temperature to convert
     * @param sourceScale the scale of the input ("C", "F", or "K")
     * @param targetScale the scale to convert to ("C", "F", or "K")
     * @return the converted temperature
     */
    public static double convertTemperature(double value, String sourceScale, String targetScale) {
        switch (sourceScale.toUpperCase()) {
            case "C":
                return convertCelsius(value, targetScale);
            case "F":
                return convertFahrenheit(value, targetScale);
            case "K":
                return convertKelvin(value, targetScale);
            default:
                throw new IllegalArgumentException("Unsupported source scale: " + sourceScale);
        }
    }
    
    // Pass-by-value demonstration

    /**
     * Helper for the pass-by-value demo: tries to change its parameter.
     * Java passes a COPY of the value, so only the local copy changes;
     * the caller's variable is not affected.
     *
     * @param temperature a copy of the caller's value
     */
    public static void modifyTemperature(double temperature) {
        temperature = temperature + 100;   // changes the local copy only
        System.out.println("  Inside modifyTemperature: temperature = "
            + formatTemperature(temperature));
    }

    /**
     * Demonstrates Java's pass-by-value behavior with a primitive (double)
     * and with an array.
     */
    public static void demonstratePassByValue() {
        System.out.println("\n--- Pass-by-value demonstration ---");

        // Case 1: primitive value. The method receives a copy of 25.
        double original = 25;
        System.out.println("Before call: original = " + formatTemperature(original));
        modifyTemperature(original);
        System.out.println("After call:  original = " + formatTemperature(original)
            + " (unchanged: the method only received a copy)");

        // Case 2: array. Java copies the array REFERENCE, so a method could change
        // the elements. convertMultiple avoids this by writing results to a NEW array.
        double[] readings = { 0, 100};
        double[] kelvinReadings = convertMultiple(readings, "C", "K");
        System.out.println("Original readings after convertMultiple: " + Arrays.toString(readings));
        System.out.println("Converted readings (new array):          " + Arrays.toString(kelvinReadings));
    }

    // Method overloading: convertMultiple
    /**
     * Converts multiple temperatures to one target scale in a single call.
     * Returns a NEW array, so the caller's original array is left unchanged.
     *
     * @param temperatures the temperatures to convert
     * @param sourceScale  the scale of the input values ("C", "F", or "K")
     * @param targetScale  the scale to convert to ("C", "F", or "K")
     * @return a new array containing the converted temperatures, in the same order
     */
    public static double[] convertMultiple(double[] temperatures, String sourceScale, String targetScale) {
        double[] results = new double[temperatures.length];

        for (int i = 0; i < temperatures.length; i++) {
            results[i] = convertTemperature(temperatures[i], sourceScale, targetScale);
        }
        return results;
    }

    /**
     * Overloaded version: converts each temperature to BOTH other scales
     * and prints the results in the required format.
     *
     * @param temperatures the temperatures to convert
     * @param sourceScale  the scale of the input values ("C", "F", or "K")
     */
    public static void convertMultiple(double[] temperatures, String sourceScale) {
        for (double temperature : temperatures) {
            printConversion(temperature, sourceScale);
        }
    }

    // Input validation

    /**
    * Checks whether the given scale is supported (C, F, or K, case-insensitive).
    *
    * @param scale the scale entered by the user
    * @return true if the scale is C, F, or K; false otherwise
    */
    public static boolean isValidScale(String scale) {
        if (scale == null) {
            return false;
        }
        String upper = scale.toUpperCase();
        return upper.equals("C") || upper.equals("F") || upper.equals("K");
    }

    /**
     * Checks that a temperature is not below absolute zero for its scale.
     * For example, negative Kelvin values are invalid.
     *
     * @param value the temperature to check
     * @param scale the scale of the temperature ("C", "F", or "K")
     * @return true if the temperature is physically possible; false otherwise
     */
    public static boolean isValidTemperature(double value, String scale) {
        switch (scale.toUpperCase()) {
            case "C":
                return value >= ABSOLUTE_ZERO_C;
            case "F":
                return value >= ABSOLUTE_ZERO_F;
            case "K":
                return value >= ABSOLUTE_ZERO_K;
            default:
                return false;
        }
    }

    /**
     * Prompts until the user enters a valid scale (C, F, or K).
     *
     * @param scanner the Scanner reading user input
     * @return the scale in uppercase
     */
    public static String readScale(Scanner scanner) {
        String scale;
        do {
            System.out.print("Enter the scale of your temperature (C, F, or K): ");
            scale = scanner.next();
            if (!isValidScale(scale)) {
                System.out.println("Invalid scale. Please enter C, F, or K.");
            }
        } while (!isValidScale(scale));

        return scale.toUpperCase();
    }

    /**
     * Prompts until the user enters a number that is not below absolute zero
     * for the given scale (e.g., rejects negative Kelvin values).
     *
     * @param scanner the Scanner reading user input
     * @param scale   the scale of the temperature being entered
     * @return a valid temperature value
     */
    public static double readTemperature(Scanner scanner, String scale) {
        double value = 0;
        boolean valid = false;

        do {
            System.out.print("Enter the temperature value: ");
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (isValidTemperature(value, scale)) {
                    valid = true;
                } else {
                    System.out.println("Invalid temperature: " + formatTemperature(value) + unitSymbol(scale)
                        + " is below absolute zero. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next();   // discard the invalid input so the loop can ask again
            }
        } while (!valid);

        return value;
    }
    
    // Structured output formatting
    /**
     * Formats a temperature for display: rounds to 2 decimal places and
     * drops the decimal part for whole numbers (77.0 -> "77", 26.850000000000023 -> "26.85").
     *
     * @param value the temperature to format
     * @return the formatted temperature as text
     */
    public static String formatTemperature(double value) {
        double rounded = Math.round(value * 100) / 100.0;

        if (rounded == (long) rounded) {
            return String.valueOf((long) rounded);
        }
        return String.valueOf(rounded);
    }

    /**
     * Returns the display symbol for a scale: °C, °F, or K
     * (Kelvin is written without a degree sign).
     *
     * @param scale "C", "F", or "K"
     * @return the unit symbol
     */
    public static String unitSymbol(String scale) {
        switch (scale.toUpperCase()) {
            case "C":
                return "°C";
            case "F":
                return "°F";
            default:
                return "K";
        }
    }

    /**
     * Converts one temperature to the other two scales and prints the result
     * in the required format, for example:
     * "25°C is equivalent to 77°F and 298.15K"
     *
     * @param value       the temperature to convert
     * @param sourceScale the scale of the input ("C", "F", or "K")
     */
    public static void printConversion(double value, String sourceScale) {
        String source = sourceScale.toUpperCase();
        String firstTarget;
        String secondTarget;

        // Pick the two other scales, in the order used by the sample output
        switch (source) {
            case "C":
                firstTarget = "F";
                secondTarget = "K";
                break;
            case "F":
                firstTarget = "C";
                secondTarget = "K";
                break;
            case "K":
                firstTarget = "C";
                secondTarget = "F";
                break;
            default:
                throw new IllegalArgumentException("Unsupported source scale: " + sourceScale);
        }

        double firstResult = convertTemperature(value, source, firstTarget);
        double secondResult = convertTemperature(value, source, secondTarget);

        System.out.println(formatTemperature(value) + unitSymbol(source)
            + " is equivalent to "
            + formatTemperature(firstResult) + unitSymbol(firstTarget)
            + " and "
            + formatTemperature(secondResult) + unitSymbol(secondTarget));
    }

    // Program entry point and test cases

    /**
     * Test: both versions of the overloaded convertMultiple method.
     */
    public static void runBatchConversionTests() {
        System.out.println("\n--- Batch conversions (method overloading) ---");

        // Version 1: three arguments -> converts to one target scale, returns an array
        double[] celsiusReadings = { 0, 25, 100};
        double[] fahrenheitResults = convertMultiple(celsiusReadings, "C", "F");
        System.out.println("convertMultiple(temperatures, \"C\", \"F\"):");
        for (int i = 0; i < celsiusReadings.length; i++) {
            System.out.println("  " + formatTemperature(celsiusReadings[i]) + unitSymbol("C")
                + " -> " + formatTemperature(fahrenheitResults[i]) + unitSymbol("F"));
        }

        // Version 2: two arguments -> converts to both other scales and prints
        double[] fahrenheitReadings = { 32, 68, 212};
        System.out.println("convertMultiple(temperatures, \"F\"):");
        convertMultiple(fahrenheitReadings, "F");
    }

    /**
     * Test: invalid values are rejected with a clear message instead of a wrong result.
     * try/catch lets the program report the error and keep running.
     */
    public static void runValidationTests() {
        System.out.println("\n--- Validation tests ---");

        try {
            convertKelvin(-5, "C");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            convertFahrenheit(-500, "C");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            convertCelsius(25, "X");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }

    /**
     * Lets the user convert temperatures until they choose to stop.
     *
     * @param scanner the Scanner reading user input
     */
    public static void runInteractiveConverter(Scanner scanner) {
        System.out.println("\n--- Interactive converter ---");
        String answer;

        do {
            String scale = readScale(scanner);
            double value = readTemperature(scanner, scale);
            printConversion(value, scale);

            System.out.print("Convert another temperature? (Y/N): ");
            answer = scanner.next();
        } while (answer.equalsIgnoreCase("Y"));

        System.out.println("Goodbye!");
    }

    /**
     * Program entry point. Runs the test cases, then the interactive converter.
     */
    public static void main(String[] args) {
        System.out.println("=== Temperature Converter ===");

        // Test 1: required sample output
        System.out.println("\n--- Sample conversions ---");
        printConversion(25, "C");
        printConversion(68, "F");
        printConversion(300, "K");

        // Test 2: method overloading
        runBatchConversionTests();

        // Test 3: pass-by-value
        demonstratePassByValue();

        // Test 4: input validation
        runValidationTests();

        // User input
        Scanner scanner = new Scanner(System.in);
        runInteractiveConverter(scanner);
        scanner.close();
    }
}