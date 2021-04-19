/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.journalisation;

/**
 *
 * @author ADMIN
 */
public class test {
    public static void main(String[] args) {
        String pathdoc="//192.168.1.10/Users\\ADMIN\\Documents\\Projets\\Documents\\pase\\Docs\\GTIN Intro.pdf";
        pathdoc=pathdoc.replaceFirst("([\\/]{1,2}[0-9\\.]+[\\/])", "/Volumes");
                System.out.println(pathdoc);
    }
}
