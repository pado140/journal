/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation.security;

import java.security.MessageDigest;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author Padovano
 */
public class Hash {
    public static String crypt(String text){
        return DigestUtils.sha512Hex(text);
    }
}
