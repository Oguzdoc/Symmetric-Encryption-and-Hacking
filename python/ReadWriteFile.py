import os

class ReadWriteFile:
    
    def __init__(self) -> None:
        pass
            
    def ReadFile(self, fileName):
        if not os.path.exists(fileName):
            return "error"
        
        with open(fileName, 'r') as file:
            return file.read()    
    
    def ReadBinaryFile(self, fileName):
        with open(fileName, 'rb') as f:
            encryptedBits = []
            byte = f.read(1)
            while byte:
                byteValue = ord(byte)
                encryptedBits += [int(bit) for bit in f"{byteValue:08b}"]
                byte = f.read(1)
            return encryptedBits 
    
    def WriteFile(self, fileName, text):
        with open(fileName, 'w+') as file:
            file.write(text)
            
    def WriteUTF8File(self, fileName, text):
        with open(fileName, 'w', encoding="utf-8") as file:
            file.write(text)
    
    def WriteBinaryFile(self, fileName, encryptedBits):
        with open(fileName, 'wb') as f:
            byteArray = bytearray()
            for i in range(0, len(encryptedBits), 8):
                byteValue = int(''.join(map(str, encryptedBits[i:i+8])), 2)
                byteArray.append(byteValue)
            f.write(byteArray)