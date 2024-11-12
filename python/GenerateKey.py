import random
# -*- coding: utf-8 -*-
class GenerateKey:
        
    def __init__(self, language="en") -> None:
        if language == "tr":
            self.alphabet = "ABCÇDEFGĞHIİJKLMNOÖPRSŞTUÜVYZ"
        else:
            self.alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    
    def GenerateMonoalphabeticKey(self):
        return self.alphabet,''.join(random.sample(self.alphabet, len(self.alphabet)))
    
    @staticmethod
    def IfCoprime(a, b):
        if b == 0:
            return a
        else:
            return GenerateKey.IfCoprime(b, a % b)

    @staticmethod
    def GenerateAffineKey(lengthOfAlphabet):
        a = random.randint(1, lengthOfAlphabet)
        
        if GenerateKey.IfCoprime(a, lengthOfAlphabet) != 1:
            return GenerateKey.GenerateAffineKey(lengthOfAlphabet)
        
        b = random.randint(0, lengthOfAlphabet - 1)
        return a, b
    
    def GenerateDESKey(self,length=56):
        return ''.join(map(str, [random.randint(0, 1) for _ in range(length)]))


