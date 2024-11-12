import java.util.List;

public class App {
    public static void main(String[] args) throws Exception 
    {
        
//#region English
        Helpers helpers = new Helpers();
        AffineCipher affineCipher = new AffineCipher(26);
        MonoalphabeticCipher monoalphabeticCipher = new MonoalphabeticCipher();
        PolyalphabeticCipher polyalphabeticCipher = new PolyalphabeticCipher(26);
        DataEncryptionStandard des = new DataEncryptionStandard();

        Number[] affineCipherKey = helpers.generateAffineKey();
        String monoalphabeticKey = helpers.generateMonoalphabeticKey();
        String polyalphabeticKey = helpers.generatePolyalphabeticKey();
        String desKey = helpers.generateDESKey();

        System.out.println("\nGenerated Keys\n");
        System.out.printf("Affine Keys: %d %d \t Monoalphabetic Key: %s \t Polyalphabetic Key: %s \t DES Key: %s\n\n",
        affineCipherKey[0], 
        affineCipherKey[1],
        monoalphabeticKey,
        polyalphabeticKey,
        desKey);

        String importantMessage = helpers.readFile("src\\ImportantMessage.txt");
        
        String encryptedAffineMessage = affineCipher.encryptMessage(importantMessage, affineCipherKey);
        helpers.writeFile("src\\Outputs\\encryptedAffineMessage.txt", encryptedAffineMessage);

        String decryptedAffineMessage = affineCipher.decryptMessage(encryptedAffineMessage, affineCipherKey);
        helpers.writeFile("src\\Outputs\\decryptedAffineMessage.txt", decryptedAffineMessage);

        String encryptedMonoalphabeticMessage = monoalphabeticCipher.encryptMessage(importantMessage, monoalphabeticKey);
        helpers.writeFile("src\\Outputs\\encryptedMonoalphabeticMessage.txt", encryptedMonoalphabeticMessage);

        String decryptedMonoalphabeticMessage = monoalphabeticCipher.decryptMessage(encryptedMonoalphabeticMessage, monoalphabeticKey);
        helpers.writeFile("src\\Outputs\\decryptedMonoalphabeticMessage.txt", decryptedMonoalphabeticMessage);

        String encryptedPolyalphabeticMessage = polyalphabeticCipher.encryptMessage(importantMessage, polyalphabeticKey);
        helpers.writeFile("src\\Outputs\\encryptedPolyalphabeticMessage.txt", encryptedPolyalphabeticMessage);

        String decryptedPolyalphabeticMessage = polyalphabeticCipher.decryptMessage(encryptedPolyalphabeticMessage, polyalphabeticKey);
        helpers.writeFile("src\\Outputs\\decryptedPolyalphabeticMessage.txt", decryptedPolyalphabeticMessage);

        List<Integer> encryptedDESMessage =  des.encryptMessage(importantMessage,desKey);
        helpers.writeBinaryFile("src\\Outputs\\encryptedDESMessage.txt", encryptedDESMessage);

        String decryptedDESMessage =  des.decryptMessage(encryptedDESMessage,desKey);
        helpers.writeFile("src\\Outputs\\decryptedDESMessage.txt", decryptedDESMessage);

        System.out.println("\nAffine Brute Force\n");
        affineCipher.bruteForceAffine(encryptedAffineMessage);
        System.out.println("\nMonoalphabetic Analysis\n");
        monoalphabeticCipher.analyzeNGrams(encryptedMonoalphabeticMessage);
        System.out.println("\nPolyalphabetic Brute Force\n");
        polyalphabeticCipher.bruteForcePolyalphabet(encryptedPolyalphabeticMessage);

//#endregion

//#region Turkish

        helpers = new Helpers(false);
        affineCipherKey = helpers.generateAffineKey();

        monoalphabeticKey = helpers.generateMonoalphabeticKey();
        polyalphabeticKey = helpers.generatePolyalphabeticKey();
        
        affineCipher = new AffineCipher(29);
        monoalphabeticCipher = new MonoalphabeticCipher(false);
        polyalphabeticCipher = new PolyalphabeticCipher(29);

        System.out.println("\nGenerated Keys For Turkish Part\n");
        System.out.printf("Affine Keys: %d %d \t Monoalphabetic Key: %s \t Polyalphabetic Key: %s \t \n\n",
        affineCipherKey[0], 
        affineCipherKey[1],
        monoalphabeticKey,
        polyalphabeticKey);

        importantMessage = helpers.readFile("src\\TurkishImportantMessage.txt");

        encryptedAffineMessage = affineCipher.encryptMessage(importantMessage, affineCipherKey);
        helpers.writeFile("src\\TurkishOutputs\\TurkishEncryptedAffineMessage.txt", encryptedAffineMessage);

        decryptedAffineMessage = affineCipher.decryptMessage(encryptedAffineMessage, affineCipherKey);
        helpers.writeFile("src\\TurkishOutputs\\TurkishDecryptedAffineMessage.txt", decryptedAffineMessage);

        encryptedMonoalphabeticMessage = monoalphabeticCipher.encryptMessage(importantMessage, monoalphabeticKey);
        helpers.writeFile("src\\TurkishOutputs\\TurkishEncryptedMonoalphabeticMessage.txt", encryptedMonoalphabeticMessage);

        decryptedMonoalphabeticMessage = monoalphabeticCipher.decryptMessage(encryptedMonoalphabeticMessage, monoalphabeticKey);
        helpers.writeFile("src\\TurkishOutputs\\TurkishDecryptedMonoalphabeticMessage.txt", decryptedMonoalphabeticMessage);

        encryptedPolyalphabeticMessage = polyalphabeticCipher.encryptMessage(importantMessage, polyalphabeticKey);
        helpers.writeFile("src\\TurkishOutputs\\TurkishEncryptedPolyalphabeticMessage.txt", encryptedPolyalphabeticMessage);

        decryptedPolyalphabeticMessage = polyalphabeticCipher.decryptMessage(encryptedPolyalphabeticMessage, polyalphabeticKey);
        helpers.writeFile("src\\TurkishOutputs\\TurkishDecryptedPolyalphabeticMessage.txt", decryptedPolyalphabeticMessage);

        System.out.println("\nTurkish Affine Brute Force\n");
        affineCipher.bruteForceAffine(encryptedAffineMessage);
        System.out.println("\nTurkish Monoalphabetic Analysis\n");
        monoalphabeticCipher.analyzeNGrams(encryptedMonoalphabeticMessage);
        System.out.println("\nTurkish Polyalphabetic Brute Force\n");
        polyalphabeticCipher.bruteForcePolyalphabet(encryptedPolyalphabeticMessage);

//#endregion
        System.out.println("Check Outputs!!!");
    }
}
