# Symmetric-Encryption-and-Hacking
A project demonstrating encryption and decryption using Affine, Monoalphabetic, Polyalphabetic, and DES ciphers with hacking simulations

# Symmetric Encryption and Hacking Project

This project demonstrates encryption and decryption using several symmetric cipher systems: Affine, Monoalphabetic, Polyalphabetic, and DES ciphers. The project includes hacking simulations to decipher messages without knowing the encryption key, using brute-force and analysis techniques.

## Project Description

The project consists of two main tasks:
1. **Encryption and Decryption with Affine, Monoalphabetic, and Polyalphabetic Ciphers**:
   - **Affine Cipher**: Hacked using brute-force.
   - **Monoalphabetic Cipher**: Hacked using frequency analysis.
   - **Polyalphabetic Cipher**: Hacked using brute-force.
2. **DES Encryption and Decryption**:
   - Implements DES cipher with a 56-bit key length.
   - Generates random keys, encodes and decodes text, and saves the results.

## Features

- Random key generation for encryption.
- Encoder and decoder functions for each cipher type.
- Text encoding and decoding with results saved in `.txt` files.
- Hacking functions using brute-force and analysis techniques.
- Supports multilingual messages (e.g., English and Turkish).

## Project Structure

```plaintext
.
├── java               # Java implementation
│   ├── src
├── python             # Python implementation
│   ├── AffineCipher .py
│   ├── MonoalphabeticCipher.py
│   ├── PolyalphabeticCipher.py
│   ├── DES.py
│   ├── GenerateKey.py
│   └── ReadWriteFile.py
│   └── main.py
├── README.md
