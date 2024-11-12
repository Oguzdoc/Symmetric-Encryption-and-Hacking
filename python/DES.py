class DES:
    
    INITIAL_PERMUTATION_TABLE = [
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7,
        56, 48, 40, 32, 24, 16, 8, 0,
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6
    ]

    FINAL_PERMUTATION_TABLE = [
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25,
        32, 0, 40, 8, 48, 16, 56, 24
    ]

    KEY_PERMUTATION_TABLE = [
        48, 40, 32, 24, 16, 8, 0, 49,
        41, 33, 25, 17, 9, 1, 50, 42,
        34, 26, 18, 10, 2, 51, 43, 35,
        27, 19, 11, 3, 52, 44, 36, 28,
        20, 12, 4, 53, 45, 37, 29, 21,
        13, 5, 54, 46, 38, 30, 22, 14,
        6, 55, 47, 39, 31, 23, 15, 7
    ]

    COMPRESSION_PERMUTATION_TABLE = [
        13, 16, 10, 23, 0, 4,
        2, 27, 14, 5, 20, 9,
        22, 18, 11, 3, 25, 7,
        15, 6, 26, 19, 12, 1,
        40, 51, 30, 36, 46, 54,
        29, 39, 50, 44, 32, 47,
        43, 48, 38, 55, 33, 52,
        45, 41, 49, 35, 28, 31
    ]

    EXPANSION_PERMUTATION_TABLE = [
        31, 0, 1, 2, 3, 4,
        3, 4, 5, 6, 7, 8,
        7, 8, 9, 10, 11, 12,
        11, 12, 13, 14, 15, 16,
        15, 16, 17, 18, 19, 20,
        19, 20, 21, 22, 23, 24,
        23, 24, 25, 26, 27, 28,
        27, 28, 29, 30, 31, 0
    ]

    P_BOX_TABLE = [
        15, 6, 19, 20, 28, 11, 27, 16,
        0, 14, 22, 25, 4, 17, 30, 9,
        1, 7, 23, 13, 31, 26, 2, 8,
        18, 12, 29, 5, 21, 10, 3, 24
    ]

    S_BOXES = [
        [
            [14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7],
            [0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8],
            [4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0],
            [15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13]
        ],
        [
            [15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10],
            [3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5],
            [0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15],
            [13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9]
        ],
        [
            [10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8],
            [13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1],
            [13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7],
            [1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12]
        ],
        [
            [7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15],
            [13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9],
            [10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4],
            [3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14]
        ],
        [
            [2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9],
            [14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6],
            [4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14],
            [11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3]
        ],
        [
            [12, 1, 10, 15, 9, 2, 6, 8, 0, 5, 14, 4, 3, 11, 7, 13],
            [10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8],
            [9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6],
            [4, 3, 2, 12, 1, 10, 15, 9, 8, 5, 14, 11, 6, 7, 0, 13]
        ],
        [
            [4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1],
            [13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6],
            [1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2],
            [6, 1, 3, 10, 13, 8, 7, 4, 11, 14, 9, 5, 0, 15, 2, 12]
        ],
        [
            [13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7],
            [1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2],
            [7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8],
            [2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 3, 5, 0, 6, 11]
        ]
    ]
    
    def __init__(self) -> None:
        pass
    
    def permute(self,inputBits, permutationTable):
        return [inputBits[i] for i in permutationTable]

    def xor(self,bits1, bits2):
        return [b1 ^ b2 for b1, b2 in zip(bits1, bits2)]

    def sBoxesSubstitution(self,inputBits):
        outputBits = []
        for i in range(0, 48, 6):
            block = inputBits[i:i+6]
            row = (block[0] << 1) | block[5]
            col = (block[1] << 3) | (block[2] << 2) | (block[3] << 1) | block[4]
            sValue = self.S_BOXES[i // 6][row][col]
            outputBits += [int(bit) for bit in f"{sValue:04b}"]
        return outputBits

    def desRound(self,leftBits, rightBits, subkey):
        expandedBits = self.permute(rightBits, self.EXPANSION_PERMUTATION_TABLE)
        xorResult = self.xor(expandedBits, subkey)
        sOutput = self.sBoxesSubstitution(xorResult)
        pOutput = self.permute(sOutput, self.P_BOX_TABLE)
        return rightBits, self.xor(leftBits, pOutput)

    def generateSubkeys(self,keyBits):
        keyBits = self.permute(keyBits, self.KEY_PERMUTATION_TABLE)
        leftKey = keyBits[:28]
        rightKey = keyBits[28:]
        SHIFT_TABLE = [1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1]
        subkeys = []

        for i in range(16):
            leftKey = leftKey[SHIFT_TABLE[i]:] + leftKey[:SHIFT_TABLE[i]]
            rightKey = rightKey[SHIFT_TABLE[i]:] + rightKey[:SHIFT_TABLE[i]]
            subkey = self.permute(leftKey + rightKey, self.COMPRESSION_PERMUTATION_TABLE)
            subkeys.append(subkey)

        return subkeys

    def encryptBlock(self,blockBits, keyBits):
        blockBits = self.padBits(blockBits)
        blockBits = self.permute(blockBits, self.INITIAL_PERMUTATION_TABLE)
        leftBits = blockBits[:32]
        rightBits = blockBits[32:]
        subkeys = self.generateSubkeys(keyBits)
        for subkey in subkeys:
            leftBits, rightBits = self.desRound(leftBits, rightBits, subkey)
        finalBits = self.permute(rightBits + leftBits, self.FINAL_PERMUTATION_TABLE)
        return finalBits

    def decryptBlock(self, blockBits, keyBits):
        blockBits = self.padBits(blockBits)
        blockBits = self.permute(blockBits, self.INITIAL_PERMUTATION_TABLE)
        leftBits = blockBits[:32]
        rightBits = blockBits[32:]
        subkeys = self.generateSubkeys(keyBits)[::-1]
        for subkey in subkeys:
            leftBits, rightBits = self.desRound(leftBits, rightBits, subkey)
        finalBits = self.permute(rightBits + leftBits, self.FINAL_PERMUTATION_TABLE)
        return finalBits

    def padBits(self, bits):
        paddingLength = 64 - (len(bits) % 64)
        padding = [0] * paddingLength
        padding[-8:] = [int(bit) for bit in f"{paddingLength:08b}"]
        return bits + padding

    def unpadBits(self, bits):
        paddingLength = int(''.join(map(str, bits[-8:])), 2)
        return bits[:-paddingLength]

    def textToBits(self, text):
        bits = []
        for char in text:
            bits += [int(bit) for bit in f"{ord(char):08b}"]
        return self.padBits(bits)

    def bitsToText(self, bits):
        bits = self.unpadBits(bits)
        chars = [chr(int(''.join(map(str, bits[i:i+8])), 2)) for i in range(0, len(bits), 8)]
        return ''.join(chars)
    
    def EncryptMessage(self, message, key):
        
        keyBits = self.textToBits(key)
        encryptedBits = []

        # Read message in 64-bit (8 bytes) blocks
        for i in range(0, len(message), 8):
            block = message[i:i+8]
            blockBits = self.textToBits(block)
            encryptedBlockBits = self.encryptBlock(blockBits, keyBits)
            encryptedBits += encryptedBlockBits
        
        return encryptedBits
    
    def DecryptMessage(self,message, key):
        
        keyBits = self.textToBits(key)
        decryptedBits = []

        # Read message in 64-bit (8 bytes) blocks
        for i in range(0, len(message), 64):
            blockBits = message[i:i+64]
            if len(blockBits) == 64:  # Ensure complete block
                decryptedBlockBits = self.decryptBlock(blockBits, keyBits)
                decryptedBits += decryptedBlockBits

        decryptedText = self.bitsToText(decryptedBits)
        return decryptedText