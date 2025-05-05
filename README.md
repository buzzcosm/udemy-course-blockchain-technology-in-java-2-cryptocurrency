# Cryptocurrency Project

[Udemy course - Blockchain](https://www.udemy.com/course/learn-blockchain-technology-in-java)

## Description

Implementation of a cryptocurrency using blockchain technology.

## Topics

### Elliptic Curve Diffie-Hellman (ECDH) Key Exchange

Elliptic Curve Diffie-Hellman (ECDH) is a key agreement protocol, a variant of the Diffie-Hellman (DH) algorithm, that uses elliptic curve cryptography to establish a shared secret between two parties over an insecure channel. ECDH relies on the elliptic curve discrete logarithm problem (ECDLP) instead of the discrete logarithm problem (DLP) used in standard DH. 

Here's a breakdown of how ECDH works:

1. Elliptic Curve Group Agreement:  
   Both parties agree on an elliptic curve group (E) and a point (P) on that curve, which is assumed to be known to any potential eavesdropper.
2. Private Key Generation:  
   Each party (A and B) selects a random private key, which is a scalar integer (a for A and b for B).
3. Public Key Calculation:  
   Each party computes a public key (Q = aP for A and R = bP for B) by multiplying their private key with the agreed-upon point P on the curve.
4. Exchange of Public Keys:  
   A sends Q to B, and B sends R to A.
5. Shared Secret Calculation:  
   - A computes a shared secret key (S = aR) by multiplying their private key (a) with B's public key (R).
   - B computes a shared secret key (S = bQ) by multiplying their private key (b) with A's public key (Q).
6. Shared Secret Equality:  
   Both A and B arrive at the same shared secret key (S) because of the properties of elliptic curve multiplication: S = aR = abP = bQ.

The shared secret key can then be used to encrypt subsequent communications using a symmetric-key cipher. ECDH offers advantages like smaller key sizes for the same security level and resistance to certain attacks.

Elliptic-curve Diffie–Hellman (ECDH) is a key agreement protocol that allows two parties, each having an elliptic-curve public–private key pair, to establish a shared secret over an insecure channel. This shared secret may be directly used as a key, or to derive another key.