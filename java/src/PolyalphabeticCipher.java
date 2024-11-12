import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class PolyalphabeticCipher {

    private int _alphabetLength = 0; // Length of the alphabet used in cipher
    private List<String> mostCommonWords; // Common words for matching during brute-force
    private char[] _alphabet; // Alphabet array (English or Turkish)
    private HashMap<Character, Integer> alphabetIndexMap; // Map for quick character-to-index lookup

    private static final char[] ENGLISH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] TURKISH = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ".toCharArray();

    /**
     * Constructor for initializing the PolyalphabeticCipher with the desired alphabet length.
     * Sets up most common words, alphabet, and creates a mapping from each character to its index.
     *
     * @param lengthOfAlphabet Length of the alphabet (26 for English, 29 for Turkish)
     */
    public PolyalphabeticCipher(int lengthOfAlphabet) {
        this._alphabetLength = lengthOfAlphabet;

        // Set common words based on alphabet type
        this.mostCommonWords = (lengthOfAlphabet == 29) 
            ? Arrays.asList("PEK", "BIR", "BU", "ZAMAN", "DA", "ILE", "DE", "ÇOK", "DEN", "KI", "NE", "BEN", "AMA", "ONA", "GIBI", "HER", "AMA", "DAHA", "ÇOK", "ŞEY")
            : Arrays.asList("THE", "BE", "TO", "OF", "AND", "A", "IN", "THAT", "HAVE", "I", "IT", "FOR", "NOT", "ON", "WITH", "HE", "AS", "YOU", "DO", "AT");

        // Set the appropriate alphabet based on the alphabet length
        this._alphabet = (_alphabetLength == 29) ? TURKISH : ENGLISH;

        // Create a character-to-index map for fast lookup
        this.alphabetIndexMap = new HashMap<>();
        for (int i = 0; i < _alphabet.length; i++) {
            alphabetIndexMap.put(_alphabet[i], i);
        }
    }

    /**
     * Helper function to get the index of a character in the alphabet.
     * 
     * @param c Character to get the index of
     * @return  Index of character in alphabet, or -1 if not found
     */
    private int getIndex(char c) {
        return alphabetIndexMap.getOrDefault(c, -1);
    }

    /**
     * Encrypts a message using the Vigenère cipher with the provided key.
     * 
     * @param message The plaintext message to be encrypted
     * @param key     The key used for encryption
     * @return        The encrypted message
     */
    public String encryptMessage(String message, String key) {
        StringBuilder newMessage = new StringBuilder();
        message = _alphabetLength == 29 ? message.toUpperCase(Locale.forLanguageTag("tr")) : message.toUpperCase();
        int count = 0;

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);

            if (Character.isLetter(currentChar)) {
                int index = getIndex(currentChar);
                int keyIndex = getIndex(key.charAt(count % key.length()));
                if (index == -1 || keyIndex == -1) {
                    newMessage.append(currentChar); // Append non-alphabet character as-is
                } else {
                    int encryptedIndex = (index + keyIndex) % _alphabetLength;
                    newMessage.append(_alphabet[encryptedIndex]);
                }

                count++;
            } else {
                newMessage.append(currentChar); // Append non-alphabet character as-is
            }
        }

        return newMessage.toString();
    }

    /**
     * Decrypts a message encrypted with the Vigenère cipher using the provided key.
     * 
     * @param message The encrypted message to be decrypted
     * @param key     The key used for decryption
     * @return        The decrypted message
     */
    public String decryptMessage(String message, String key) {
        StringBuilder newMessage = new StringBuilder();
        message = _alphabetLength == 29 ? message.toUpperCase(Locale.forLanguageTag("tr")) : message.toUpperCase();
        int count = 0;

        for (int i = 0; i < message.length(); i++) {
            char currentChar = message.charAt(i);

            if (Character.isLetter(currentChar)) {
                int index = getIndex(currentChar);
                int keyIndex = getIndex(key.charAt(count % key.length()));

                if (index == -1 || keyIndex == -1) {
                    newMessage.append(currentChar); // Append non-alphabet character as-is
                } else {
                    int decryptedIndex = (index - keyIndex + _alphabetLength) % _alphabetLength;
                    newMessage.append(_alphabet[decryptedIndex]);
                }

                count++;
            } else {
                newMessage.append(currentChar); // Append non-alphabet character as-is
            }
        }

        return newMessage.toString();
    }

    /**
     * Generates keys of length 1 to 3 using characters from the alphabet.
     * 
     * @return A list of generated keys for brute-force attempts
     */
    private List<String> generateKeys() {
        List<String> combinations = new ArrayList<>();

        // Single character keys
        for (char c : this._alphabet) {
            combinations.add(String.valueOf(c));
        }

        // Two character keys
        for (char c1 : this._alphabet) {
            for (char c2 : this._alphabet) {
                combinations.add(String.valueOf(c1) + c2);
            }
        }

        // Three character keys
        for (char c1 : this._alphabet) {
            for (char c2 : this._alphabet) {
                for (char c3 : this._alphabet) {
                    combinations.add(String.valueOf(c1) + c2 + c3);
                }
            }
        }

        return combinations;
    }

    /**
     * Attempts to brute-force decrypt a message by testing common word matches with generated keys.
     * 
     * @param message The encrypted message to brute-force decrypt
     */
    public void bruteForcePolyalphabet(String message) {
        List<String> combinations = generateKeys();
        int maxMatchCount = 0;
        String potentialKey = "";
        String potentialDecryptedMessage = "";

        for (String combination : combinations) {
            int matchCount = 0;
            String decryptedMessage = decryptMessage(message, combination);
            String[] words = decryptedMessage.split("\\s+");

            for (String word : words) {
                if (mostCommonWords.contains(word)) {
                    matchCount++;
                }
            }

            // Keep track of the combination with the highest word match count
            if (matchCount >= maxMatchCount) {
                maxMatchCount = matchCount;
                potentialKey = combination;
                potentialDecryptedMessage = decryptedMessage;
            }
        }

        System.out.println("Potential match found! Key = " + potentialKey + ", => " + potentialDecryptedMessage);
    }
}