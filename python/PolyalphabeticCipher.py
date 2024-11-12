class PolyalphabeticCipher:
    
    def __init__(self,lengthOfAlphabet) -> None:
        self.lengthOfAlphabet = lengthOfAlphabet
        
        if lengthOfAlphabet == 29:
            self.alphabet = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ"
        else:
            self.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        pass
    
    def EncryptMessage(self, message ,key):
        newMessage = ''
        message = message.upper()
        count=0
        for i in message:
            if i in self.alphabet:
                message_index = self.alphabet.index(i)
                key_index = self.alphabet.index(key[count % len(key)].upper())
                
                new_index = (message_index + key_index) % self.lengthOfAlphabet
                newMessage += self.alphabet[new_index]
            else:
                newMessage += i
            count += 1
        return newMessage
    
    def DecryptMessage(self, message,key):
        newMessage = ''
        message = message.upper()
        count=0
        for i in message:
            if i in self.alphabet:
                message_index = self.alphabet.index(i)
                key_index = self.alphabet.index(key[count % len(key)].upper())
                
                new_index = (message_index - key_index) % self.lengthOfAlphabet
                newMessage += self.alphabet[new_index]
            else:
                newMessage += i
            count += 1
        return newMessage

    def BruteForce(self, encrypted_message):
        maxCount = 0
        potentialKey = ""
        potentialMessage = ""
        
        max_key_length = 3 
        for key_length in range(1, max_key_length + 1):
            for key_tuple in self.generate_keys(self.alphabet, key_length):
                key = ''.join(key_tuple)
                decrypted_message = self.DecryptMessage(encrypted_message, key)
                count = self.is_valid_decryption(decrypted_message)
                
                if count > maxCount:
                    maxCount = count
                    potentialKey = key
                    potentialMessage = decrypted_message
        
        return f"Key : {potentialKey} Message : {potentialMessage}".encode('utf-8').decode('utf-8')

    
    def generate_keys(self, alphabet, length):

        if length == 0:
            return

        for letter in alphabet:
            if length == 1:
                yield letter
            else:
                for suffix in self.generate_keys(alphabet, length - 1):
                    yield letter + suffix
    
    def is_valid_decryption(self,decrypted_message):
        count = 0
        
        valid_words = ["THE", "AND", "OF", "TO", "IS", "IN", "THAT", "A", "IT", "ON", "WITH","BUT","WE","AS"]
        
        if(len(self.alphabet) == 29):
            valid_words = ["VE", "KENDİ", "OLAN", "SEN", "ŞİMDİ", "TÜM", "GüZEL", "ONUN", "GENÇLİK", "KI", "BU", "TAMAMEN", "İLE", "HATIRASINI", "TAŞISIN", "OLAN", "ANCAK", "ZAMANLA", "BİR", "KENDİNE"]

        words = decrypted_message.upper().split()
        
        for word in words:
            if word in valid_words:
                count = count +1 
                
        return count