/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sbs.easymbank.utility;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.axis.encoding.Base64;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author alex
 */
public class MoovTokenGenerator {

    public static String generate(String counter, String username, String password, String hexKey) throws Exception {

        String plaintext = counter + ":" + username + ":" + password;
        print("Plain Text = " + plaintext);
        byte plain[] = plaintext.getBytes("UTF-8");
       // String hexKey = "746C633132333435746C633132333435746C633132333435746C633132333435";
        byte[] key = Hex.decode(hexKey);
        print("Plain Hex = " + new String(plain));

        SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(new byte[cipher.getBlockSize()]);
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
        byte encrypted[] = cipher.doFinal(plain, 0, plain.length);
        print("Encrypted Hex = " + new String(Hex.encode(encrypted)));

        String encode = Base64.encode(encrypted);
        print("Token Text = " + encode);
        return encode;

    }

    public static void print(String str) {
        System.out.println(str);
    }

}
