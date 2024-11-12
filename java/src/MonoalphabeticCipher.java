import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonoalphabeticCipher {
    
    private String _alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"; // Default alphabet (English)
    private final int _topK = 3; // The top K n-grams to analyze
    private final int _maxGram = 20; // The max n-grams to display in analysis

    /**
     * Default constructor that initializes the cipher with the English alphabet.
     */
    public MonoalphabeticCipher() {
        this(true);
    }
    
    /**
     * Constructor that allows setting the alphabet based on language preference.
     * 
     * @param isEnglish True for English alphabet; False for Turkish alphabet.
     */
    public MonoalphabeticCipher(boolean isEnglish) {
        if (!isEnglish) {
            _alphabet = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ"; // Turkish alphabet
        }
    }   

    /**
     * Encrypts the given message using a monoalphabetic substitution cipher and a specified key.
     *
     * @param message The plaintext message to be encrypted.
     * @param key     The substitution key that maps each letter in the alphabet to another.
     * @return        The encrypted message.
     */
    public String encryptMessage(String message, String key) {
        message = message.toUpperCase();
        StringBuilder newMessage = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = _alphabet.indexOf(c);
                if (index != -1) {
                    newMessage.append(key.charAt(index)); // Substitute character from key
                } else {
                    newMessage.append(c); // Non-alphabetic characters are appended unchanged
                }
            } else {
                newMessage.append(c); // Non-letter characters are appended unchanged
            }
        }

        return newMessage.toString();
    }

    /**
     * Decrypts the given message using a monoalphabetic substitution cipher and a specified key.
     *
     * @param message The encrypted message to be decrypted.
     * @param key     The substitution key used during encryption.
     * @return        The decrypted message.
     */
    public String decryptMessage(String message, String key) {
        message = message.toUpperCase();
        StringBuilder newMessage = new StringBuilder();

        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                int index = key.indexOf(c);
                if (index != -1) {
                    newMessage.append(_alphabet.charAt(index)); // Map character back to original
                } else {
                    newMessage.append(c); // Non-alphabetic characters are appended unchanged
                }
            } else {
                newMessage.append(c); // Non-letter characters are appended unchanged
            }
        }

        return newMessage.toString();
    }

    /**
     * Analyzes n-grams in the given text for n values from 1 up to the specified topK.
     * Prints the top n-grams based on frequency for each n.
     *
     * @param text The text to analyze for n-grams.
     */
    public void analyzeNGrams(String text) {
        // Remove non-alphabetic characters and convert to uppercase
        String filteredText = text.replaceAll("[^a-zA-Z]", "").toUpperCase();
    
        for (int n = 1; n <= _topK; n++) {
            System.out.println("\n" + n + "-gram (top " + _maxGram + "):");
            Map<String, Integer> nGramFreq = calculateNGramFrequency(filteredText, n);
            printTopNGrams(nGramFreq);
        }
    }
    
    /**
     * Calculates the frequency of each n-gram in the provided text.
     *
     * @param text The text to analyze.
     * @param n    The length of the n-grams to find.
     * @return     A map of n-grams to their frequency counts.
     */
    private Map<String, Integer> calculateNGramFrequency(String text, int n) {
        Map<String, Integer> nGramFreq = new HashMap<>();
    
        for (int i = 0; i <= text.length() - n; i++) {
            String nGram = text.substring(i, i + n); // text is already uppercase and cleaned
            nGramFreq.put(nGram, nGramFreq.getOrDefault(nGram, 0) + 1);
        }
    
        return nGramFreq;
    }
    
    /**
     * Prints the top n-grams by frequency up to the specified maximum.
     *
     * @param nGramFreq A map of n-grams to their frequency counts.
     */
    private void printTopNGrams(Map<String, Integer> nGramFreq) {
        List<Map.Entry<String, Integer>> sortedNGrams = new ArrayList<>(nGramFreq.entrySet());
        sortedNGrams.sort((a, b) -> b.getValue().compareTo(a.getValue())); // Sort by frequency descending
    
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedNGrams) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
            count++;
            if (count >= _maxGram) break; // Limit output to maxGram entries
        }
    }
}