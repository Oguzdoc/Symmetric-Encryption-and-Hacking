import java.util.Arrays;
import java.util.List;

public class AffineCipher 
{
    private int _alphabet = 0;

    private List<String> mostCommonWords;
    private char[] upperAlphabet;
    private int[] possibleAValues;
    
    private static final char[] ENGLISH = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] TURKISH = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ".toCharArray();
    
    /**
     * Initializes the AffineCipher with a specified alphabet length,
     * setting up most common words, alphabet, and valid A values.
     * 
     * @param lengthOfAlphabet  the length of the alphabet, either 26 for English or 29 for Turkish
     */
    public AffineCipher(int lengthOfAlphabet) 
    {
        this._alphabet = lengthOfAlphabet;

        this.mostCommonWords = (lengthOfAlphabet == 29) 
            ? Arrays.asList("PEK", "BIR", "BU", "ZAMAN", "DA", "ILE", "DE", "ÇOK", "DEN", "KI", "NE", "BEN", "AMA", "ONA", "GIBI", "HER", "AMA", "DAHA", "ÇOK", "ŞEY")
            : Arrays.asList("THE", "BE", "TO", "OF", "AND", "A", "IN", "THAT", "HAVE", "I", "IT", "FOR", "NOT", "ON", "WITH", "HE", "AS", "YOU", "DO", "AT");

        this.upperAlphabet = (_alphabet == 29) ? TURKISH : ENGLISH;
        this.possibleAValues = (_alphabet == 29) ? new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28} 
                                                 : new int[]{1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25};
    }

    /**
     * Computes the modular inverse of a number 'a' modulo the alphabet length.
     * 
     * @param a  the integer whose modular inverse is to be calculated
     * @return   the modular inverse of a if it exists, or -1 if it doesn't
     */
    private int modularInverse(int a) {
        int inverterA = -1;
        
        for (int i = 0; i < _alphabet; i++) 
        {
            if ((a * i) % _alphabet == 1) 
            {
                inverterA = i;
                break;
            }
        }
        return inverterA;
    }

    /**
     * Finds the index of a character within the specified alphabet array.
     * 
     * @param c         the character to locate
     * @param alphabet  the array representing the alphabet
     * @return          the index of the character in the array, or -1 if not found
     */
    private int getIndex(char c, char[] alphabet) 
    {
        int result = -1;
    
        for (int i = 0; i < alphabet.length; i++) 
        {
            if (alphabet[i] == c) {
                result = i;
                break;
            }
        }
    
        return result;
    }
    
    /**
     * Encrypts a message using the Affine cipher with the provided key.
     * 
     * @param message  the plain text message to encrypt
     * @param key      an array where key[0] is the multiplicative constant and key[1] is the additive constant
     * @return         the encrypted message as a string
     */
    public String encryptMessage(String message, Number[] key) 
    {
        StringBuilder newMessage = new StringBuilder();
        message = message.toUpperCase();
        
        for (char i : message.toCharArray()) 
        {
            if (Character.isUpperCase(i))
            {
                int index = getIndex(i, upperAlphabet);
                if (index != -1) 
                    newMessage.append(upperAlphabet[(key[0].intValue() * index + key[1].intValue()) % _alphabet]);
                else 
                    newMessage.append(i); // Append as-is if not in alphabet
            } 
            else 
                newMessage.append(i);
        }
        
        return newMessage.toString();
    }
    
    /**
     * Decrypts a message encrypted using the Affine cipher with the provided key.
     * 
     * @param message  the encrypted message to decrypt
     * @param key      an array where key[0] is the multiplicative constant and key[1] is the additive constant
     * @return         the decrypted message as a string
     */
    public String decryptMessage(String message, Number[] key) 
    {
        StringBuilder newMessage = new StringBuilder();
        message = message.toUpperCase();

        int inverterA  = modularInverse(key[0].intValue());

        for (char i : message.toCharArray()) 
        {
            if (Character.isUpperCase(i)) 
            {
                int index = getIndex(i, upperAlphabet);
                if (index != -1) 
                {
                    int decryptedValue = (inverterA * (index - key[1].intValue() + _alphabet) % _alphabet) % _alphabet;
                    newMessage.append(upperAlphabet[decryptedValue]);
                }
                else 
                    newMessage.append(i);
            }
            else
                newMessage.append(i);
        }
        
        return newMessage.toString();
    }
    
    /**
     * Performs a brute-force attack on an Affine cipher by trying all possible keys,
     * and finds a decrypted message that matches most common words.
     * 
     * @param encryptedMessage  the encrypted message to brute-force
     */
    public void bruteForceAffine(String encryptedMessage) {

        Number[] potentialNumber = new Number[2];
        String potentialDecryptedMessage = "";
        int maxMatchCount = 0;

        for (int a : possibleAValues) 
        {
            Number[] key = new Number[2];
            key[0] = a;

            for (int b = 0; b < _alphabet; b++) 
            {
                key[1] = b;

                String decryptedMessage = decryptMessage(encryptedMessage, key);
                int matchCount = 0;

                String[] words = decryptedMessage.split("\\s+");

                for (String word : words)
                    if (mostCommonWords.contains(word)) 
                        matchCount++;

                if(matchCount >= maxMatchCount){
                    potentialNumber[0] = a;
                    potentialNumber[1] = b;
                    maxMatchCount = matchCount;
                    potentialDecryptedMessage = decryptedMessage;
                }
            }
        }
        
        System.out.println("Potential match found! a = " + potentialNumber[0] + ", b = " + potentialNumber[1] + " => " + potentialDecryptedMessage);
    }
}