import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Helpers 
{

    //region VARIABLES
    private Random _random;
    private int _lengthOfAlphabet = 26;
    private String _alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    //endregion

    /**
     * Initializes the Helpers class with the default English alphabet.
     */
    public Helpers() 
    {
        this(true);
    }

    /**
     * Initializes the Helpers class with a specified alphabet based on language preference.
     * 
     * @param isEnglish  if true, uses English alphabet; otherwise, uses a Turkish alphabet variant.
     */
    public Helpers(boolean isEnglish) 
    {
        _random = new Random();
        
        if(!isEnglish)
        {
            _lengthOfAlphabet = 29;
            _alphabet = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ";
        }
    }

    //region GENERATEKEYS

    /**
     * Checks if two integers are coprime using the Euclidean algorithm.
     * 
     * @param a  the first integer
     * @param b  the second integer
     * @return   the greatest common divisor of the two integers
     */
    private int checkIsCoprime(int a, int b)
    {
        if(b==0)
            return a;
        else
            return checkIsCoprime(b, a % b);
    }

    /**
     * Generates a random Affine Cipher key, consisting of two numbers where
     * the first is coprime to the alphabet length.
     * 
     * @return   an array of two integers representing the Affine key
     */
    public Number[] generateAffineKey()
    {
        Number[] result = new Number[2];
        result[0] = _random.nextInt(_lengthOfAlphabet-1) + 2;
        
        if(checkIsCoprime(result[0].intValue(), _lengthOfAlphabet) != 1)
            return generateAffineKey();
        
        result[1] = _random.nextInt(_lengthOfAlphabet);
        return  result;
    }

    /**
     * Generates a random Monoalphabetic Cipher key by shuffling the alphabet.
     * 
     * @return   a string representing the shuffled alphabet
     */
    public String generateMonoalphabeticKey()
    {        
        List<Character> characters = new ArrayList<>();
        for (char c : _alphabet.toCharArray()) {
            characters.add(c);
        }

        Collections.shuffle(characters);
        StringBuilder monoAlphabeticKey = new StringBuilder();
        for (char c : characters) {
            monoAlphabeticKey.append(c);
        }

        return monoAlphabeticKey.toString();
    }

    /**
     * Generates a random Polyalphabetic Cipher key of length 3.
     * 
     * @return   a string representing the polyalphabetic key
     */
    public String generatePolyalphabeticKey(){
        int lengthOfKey = 3;
        List<Character> characters = new ArrayList<>();

        for (char c : _alphabet.toCharArray()) 
            characters.add(c);

        Collections.shuffle(characters);
        StringBuilder polyAlphabeticKey = new StringBuilder();

        for (int i = 0; i<lengthOfKey ; i++) 
            polyAlphabeticKey.append(characters.get(i));

        return  polyAlphabeticKey.toString();
    }

    /**
     * Generates a random DES key of 56 bits represented as a binary string.
     * 
     * @return   a 56-bit binary string representing the DES key
     */
    public String generateDESKey() {
        Random random = new Random();
        StringBuilder desKey = new StringBuilder();

        for (int i = 0; i < 56; i++) {
            desKey.append(random.nextInt(2));
        }

        return desKey.toString();
    }

    //endregion

    //region READWRITEOPERATIONS

    /**
     * Reads the content of a text file specified by fileName.
     * 
     * @param fileName  the name of the file to read from
     * @return          the content of the file as a string, or an error message if the file cannot be read
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public String readFile(String fileName) 
    {
        if (!Files.exists(Paths.get(fileName))) 
        {
            return "The file cannot be found.";
        }
        
        StringBuilder content = new StringBuilder();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                content.append(line).append("\n");
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return "The file cannot be opened.";
        }
        
        return content.toString();
    }
    
    /**
     * Writes the specified text to a file, creating directories if necessary.
     * 
     * @param fileName  the name of the file to write to
     * @param text      the text content to write into the file
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void writeFile(String fileName, String text) 
    {
        File file = new File(fileName);
        File directory = file.getParentFile();
        
        if (directory != null && !directory.exists()) 
        {
            directory.mkdirs(); 
        }
        
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8))) 
        {
            writer.write(text);
        } 
        catch (IOException e) 
        {
            System.err.println(e.getMessage());
        }
    }
    
    /**
     * Writes a binary file by converting a list of binary values to bytes and writing them to the file.
     * 
     * @param fileName       the name of the file to write to
     * @param encryptedBits  a list of integers representing binary values to write as bytes
     */
    @SuppressWarnings("CallToPrintStackTrace")
    public void writeBinaryFile(String fileName, List<Integer> encryptedBits) 
    {
        try (FileOutputStream fos = new FileOutputStream(fileName)) 
        {
            byte[] byteArray = new byte[encryptedBits.size() / 8];
            
            for (int i = 0; i < encryptedBits.size(); i += 8) 
            {
                int byteValue = 0;
                
                for (int j = 0; j < 8; j++) 
                    byteValue = (byteValue << 1) | encryptedBits.get(i + j);

                byteArray[i / 8] = (byte) byteValue;
            }
            
            fos.write(byteArray);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    //endregion
}
