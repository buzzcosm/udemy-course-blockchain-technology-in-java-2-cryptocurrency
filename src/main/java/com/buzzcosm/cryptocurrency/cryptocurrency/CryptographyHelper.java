package com.buzzcosm.cryptocurrency.cryptocurrency;

import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class CryptographyHelper {

    public static String generateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexadecimalString = new StringBuilder();

            for (byte b : hash) {
                String hexadecimal = Integer.toHexString(0xff & b);
                if (hexadecimal.length() == 1) {
                    hexadecimalString.append('0');
                }
                hexadecimalString.append(hexadecimal);
            }

            return hexadecimalString.toString();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ECC (Elliptic Curve Cryptography) to sign the given transaction (message)
    // Elliptic Curve Digital Signature Algorithm (ECDSA)
    public static byte[] sign(PrivateKey privateKey, String input) {
        Signature signature;
        byte[] output = new byte[0];

        try {
            // we use bouncy castle (BC) for ECC
            signature = Signature.getInstance("ECDSA", "BC");
            signature.initSign(privateKey);
            signature.update(input.getBytes(StandardCharsets.UTF_8));
            output = signature.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    // checks whether the given transaction belongs to the sender based on the signature
    public static boolean verify(PublicKey publicKey, String data, byte[] signature) {
        try {
            Signature signatureToVerify = Signature.getInstance("ECDSA", "BC");
            signatureToVerify.initVerify(publicKey);
            signatureToVerify.update(data.getBytes(StandardCharsets.UTF_8));
            return signatureToVerify.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // generate public and private key
    // private key: 256 bits long random integer
    // public key: point on an elliptic curve
    // coordinates: (x, y) - both of these are 256 bits long
    public static KeyPair ellipticCurveCrypto() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECDSA", "BC");
            ECGenParameterSpec params = new ECGenParameterSpec("prime256v1");
            keyPairGenerator.initialize(params);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
