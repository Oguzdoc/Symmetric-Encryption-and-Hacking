import io
import sys
from AffineCipher import AffineCipher
from GenerateKey import GenerateKey
from MonoalphabeticCipher import MonoalphabeticCipher
from ReadWriteFile import ReadWriteFile
from PolyalphabeticCipher import PolyalphabeticCipher
from DES import DES
# -*- coding: utf-8 -*-
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

lengthOfAlphabet = 26

readWriteFile = ReadWriteFile()
generateKey = GenerateKey()
affineCipher = AffineCipher(lengthOfAlphabet)
monoalphabeticCipher = MonoalphabeticCipher()
polyalphabeticCipher = PolyalphabeticCipher(lengthOfAlphabet)
des = DES()

message = readWriteFile.ReadFile("Alice.txt")

if message == "error":
    print("File does not exist!")
    exit

    
a, b = generateKey.GenerateAffineKey(lengthOfAlphabet)
alphabet,monoalphabeticKey = generateKey.GenerateMonoalphabeticKey()
key = generateKey.GenerateDESKey()
polyalphabeticKey = monoalphabeticKey[:3]

print("AffineKey : ",a," ",b)
print("\nMonoalphabeticKey :",monoalphabeticKey)
print("\nPolyalphabeticKey :",polyalphabeticKey)
print("\nDESKey :",key)
print("\n\n")

encryptAffineMessage = affineCipher.EncryptMessage(message, a, b)
readWriteFile.WriteFile("EncryptAffine.txt", encryptAffineMessage)
decryptAffineMessage = affineCipher.DecryptMessage(encryptAffineMessage,a,b)
readWriteFile.WriteFile("DecryptAffine.txt", decryptAffineMessage)
bruteAffineMessage = affineCipher.bruteForceAffineCipher(encryptAffineMessage)
readWriteFile.WriteFile("EnglishBruteAffine.txt", bruteAffineMessage)

encryptMonoalphabeticMessage = monoalphabeticCipher.EncryptMessage(message,alphabet,monoalphabeticKey)
readWriteFile.WriteFile("EncryptMonoalphabetic.txt", encryptMonoalphabeticMessage)
decryptMonoalphabeticMessage = monoalphabeticCipher.DecryptMessage(encryptMonoalphabeticMessage,alphabet,monoalphabeticKey)
readWriteFile.WriteFile("DecryptMonoalphabetic.txt", decryptMonoalphabeticMessage)
analysisMonoalphabeticMessage = monoalphabeticCipher.GenerateAndCountNGrams(encryptMonoalphabeticMessage,alphabet)
readWriteFile.WriteFile("EnglishAnalysisMonoalphabetic.txt", analysisMonoalphabeticMessage)

encryptPolyalphabeticMessage = polyalphabeticCipher.EncryptMessage(message,polyalphabeticKey)
readWriteFile.WriteFile("EncryptPolyalphabetic.txt", encryptPolyalphabeticMessage)
decryptPolyalphabeticMessage = polyalphabeticCipher.DecryptMessage(encryptPolyalphabeticMessage,polyalphabeticKey)
readWriteFile.WriteFile("DecryptPolyalphabetic.txt", decryptPolyalphabeticMessage)
brutePolyalphabeticMessage = polyalphabeticCipher.BruteForce(encryptPolyalphabeticMessage)
readWriteFile.WriteFile("EnglishBrutePolyalphabetic.txt", brutePolyalphabeticMessage)

encryptDESMessage = des.EncryptMessage(message, key)
readWriteFile.WriteBinaryFile("EncryptDES.txt", encryptDESMessage)
decryptDESMessage = des.DecryptMessage(readWriteFile.ReadBinaryFile("EncryptDES.txt"), key)
readWriteFile.WriteFile("DecryptDES.txt", decryptDESMessage)


print("\n\n")
print("***********************TURKISH PART************************")
print("\n\n")

lengthOfAlphabet = 29

generateKey = GenerateKey("tr")

affineCipher = AffineCipher(lengthOfAlphabet)
monoalphabeticCipher = MonoalphabeticCipher()
polyalphabeticCipher = PolyalphabeticCipher(lengthOfAlphabet)
des = DES()

message = readWriteFile.ReadFile("TurkishAlice.txt")
a, b = generateKey.GenerateAffineKey(lengthOfAlphabet)
alphabet,monoalphabeticKey = generateKey.GenerateMonoalphabeticKey()
polyalphabeticKey = monoalphabeticKey[:3]

print("AffineKey : ",a," ",b)
print("\nMonoalphabeticKey :",monoalphabeticKey)
print("\nPolyalphabeticKey :",polyalphabeticKey)
print("\n\n")

encryptAffineMessage = affineCipher.EncryptMessage(message, a, b)
readWriteFile.WriteFile("TurkishEncryptAffine.txt", encryptAffineMessage)
decryptAffineMessage = affineCipher.DecryptMessage(encryptAffineMessage,a,b)
readWriteFile.WriteFile("TurkishDecryptAffine.txt", decryptAffineMessage)
bruteAffineMessage = affineCipher.bruteForceAffineCipher(encryptAffineMessage)
readWriteFile.WriteFile("TurkishBruteAffine.txt", bruteAffineMessage)

encryptMonoalphabeticMessage = monoalphabeticCipher.EncryptMessage(message,alphabet,monoalphabeticKey)
readWriteFile.WriteFile("TurkishEncryptMonoalphabetic.txt", encryptMonoalphabeticMessage)
decryptMonoalphabeticMessage = monoalphabeticCipher.DecryptMessage(encryptMonoalphabeticMessage,alphabet,monoalphabeticKey)
readWriteFile.WriteFile("TurkishDecryptMonoalphabetic.txt", decryptMonoalphabeticMessage)
analysisMonoalphabeticMessage = monoalphabeticCipher.GenerateAndCountNGrams(encryptMonoalphabeticMessage,alphabet)
readWriteFile.WriteUTF8File("TurkishAnalysisMonoalphabetic.txt", analysisMonoalphabeticMessage)

encryptPolyalphabeticMessage = polyalphabeticCipher.EncryptMessage(message,polyalphabeticKey)
readWriteFile.WriteFile("TurkishEncryptPolyalphabetic.txt", encryptPolyalphabeticMessage)
decryptPolyalphabeticMessage = polyalphabeticCipher.DecryptMessage(encryptPolyalphabeticMessage,polyalphabeticKey)
readWriteFile.WriteFile("TurkishDecryptPolyalphabetic.txt", decryptPolyalphabeticMessage)
brutePolyalphabeticMessage = polyalphabeticCipher.BruteForce(encryptPolyalphabeticMessage)
readWriteFile.WriteFile("TurkishBrutePolyalphabetic.txt", brutePolyalphabeticMessage)