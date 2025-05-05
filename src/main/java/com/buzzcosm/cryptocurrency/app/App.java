package com.buzzcosm.cryptocurrency.app;

import com.buzzcosm.cryptocurrency.cryptocurrency.CryptographyHelper;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.KeyPair;
import java.security.Security;
import java.util.Base64;

public class App {

    public static void main(String[] args) {

        // we use bouncy castle (BC) as the cryptography related provider
        Security.addProvider(new BouncyCastleProvider());

        KeyPair keys = CryptographyHelper.ellipticCurveCrypto();

        System.out.println(Base64.getEncoder().encodeToString(keys.getPrivate().getEncoded()));
        System.out.println(Base64.getEncoder().encodeToString(keys.getPublic().getEncoded()));
    }
}
