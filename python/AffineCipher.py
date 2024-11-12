# -*- coding: utf-8 -*-

class AffineCipher:
    
    def __init__(self,lengthOfAlphabet) -> None:
        self.lengthOfAlphabet = lengthOfAlphabet
        
        if lengthOfAlphabet == 29:
            self.alphabet = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ"
        else:
            self.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        pass

    def EncryptMessage(self, message, a, b):
        newMessage = ''
        message = message.upper()

        for i in message:
            if i in self.alphabet:
                index = self.alphabet.index(i)
                newMessage += self.alphabet[(a * index + b) % len(self.alphabet)]
            else:
                newMessage += i

        return newMessage

    def DecryptMessage(self, message, a, b):
        newMessage = ''
        inverterA = 0
        message = message.upper()
        

        for i in range(len(self.alphabet)):
            if (a * i) % len(self.alphabet) == 1:
                inverterA = i
                break

        for i in message:
            if i in self.alphabet:
                index = self.alphabet.index(i)
                newMessage += self.alphabet[((inverterA * (index - b)) % len(self.alphabet))]
            else:
                newMessage += i

        return newMessage
    
    def bruteForceAffineCipher(self,message):
        maxCount = 0
        keyA = 0
        keyB = 0
        potentialMessage = ""
        
        for a in range(1, len(self.alphabet)):
            
            if self.ifCoprime(a, len(self.alphabet)) == 1:
                for b in range(len(self.alphabet)):
                    decryptedMessage = self.DecryptMessage(message, a, b)
                    
                    numberOfMatch= self.is_valid_decryption(decryptedMessage)
                    
                    if numberOfMatch > maxCount:
                        maxCount = numberOfMatch
                        keyA = a
                        keyB = b
                        potentialMessage = decryptedMessage
       
        # -*- coding: utf-8 -*-
        return f"Key A: {keyA} Key B : {keyB} Message : {potentialMessage}"

   
    def ifCoprime(self,a,b):
        
        if b == 0:
            return a
        else:
            return self.ifCoprime(b, a % b)    
                     
    def is_valid_decryption(self,decrypted_message):
        count = 0
        
        valid_words = ["THE", "AND", "OF", "TO", "IS", "IN", "THAT", "A", "IT", "ON", "WITH","BUT","WE","AS"]

        if len(self.alphabet) == 29 :
            valid_words = ["VE", "KENDİ", "OLAN", "SEN", "ŞİMDİ", "TÜM", "GüZEL", "ONUN", "GENÇLİK", "KI", "BU", "TAMAMEN", "İLE", "HATIRASINI", "TAŞISIN", "OLAN", "ANCAK", "ZAMANLA", "BİR", "KENDİNE"]

        words = decrypted_message.upper().split()
        
        for word in words:
            if word in valid_words:
                count = count +1 
                
        return count