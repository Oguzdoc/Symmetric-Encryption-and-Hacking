from collections import Counter
import re


class MonoalphabeticCipher:
    
    TOP_K = 20           
    MAX_N_GRAM = 3      
    
    def __init__(self) -> None:
        pass
    
    def EncryptMessage(self, message,alphabet,key):
        newMessage = ''
        message = message.upper()
        for i in message:
            if i in alphabet:
                index = alphabet.index(i)
                newMessage += key[index]
            else:
                newMessage += i
        
        return newMessage
    
    def DecryptMessage(self, message,alphabet,key):
        newMessage = ''
        message = message.upper()
        for i in message:
            if i in alphabet:
                index = key.index(i)
                newMessage += alphabet[index]
            else:
                newMessage += i
        
        return newMessage
    

    def GenerateAndCountNGrams(self, text,alphabet):
        text = text.replace('\n', ' ').strip()
        text = ''.join([char for char in text if char in alphabet])
        result = ""
        for n in range(1, self.MAX_N_GRAM + 1):
            result += f"\n------------------------------------------\n"
            result += f"{n}-gram (Top {self.TOP_K}):\n"
            
            ngram_counts = {}
            
            for i in range(len(text) - n + 1):
                ngram = text[i:i+n]
                
                if ngram in ngram_counts:
                    ngram_counts[ngram] += 1
                else:
                    ngram_counts[ngram] = 1
            
            sorted_ngrams = sorted(ngram_counts.items(), key=lambda x: x[1], reverse=True)[:self.TOP_K]
            
            for ngram, count in sorted_ngrams:
                result += f"{ngram}: {count}\n"
        
        return result

