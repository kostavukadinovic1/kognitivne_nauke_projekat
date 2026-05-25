package org.example.kognitivne_nauke.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class KorisnikServis {
    private static final String FAJL_PUTANJA = "korisnici.txt";
    List<Korisnik> korisnici = new ArrayList<>();


    public KorisnikServis() {
        File fajl = new File(FAJL_PUTANJA);

        if (!fajl.exists()) {
            // Ako ne postoji, otvaramo blok za pisanje u fajl
            // Koristićemo try-with-resources i FileWriter/PrintWriter
            try (PrintWriter out = new PrintWriter(new FileWriter(fajl))) {
                out.println("admin;admin123;ADMIN");
                out.println("istrazivac;istrazivac123;ISTRAZIVAC");
                out.println("eksterni;eksterni123;EKSTERNI");

            } catch (IOException e) {
                System.out.println("Greška prilikom kreiranja fajla: " + e.getMessage());
            }
        }

    }

    private void ucitajKorisnike() {
        File fajl = new File(FAJL_PUTANJA);

        // Ako fajl ne postoji, kreiramo ga i upisujemo inicijalnog admina
        if (!fajl.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fajl))) {
                writer.write("admin,admin123,ADMIN");
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Čitamo sve korisnike iz fajla i ubacujemo ih u našu listu [cite: 62]
        korisnici.clear(); // Očistimo listu pre punjenja
        try (BufferedReader reader = new BufferedReader(new FileReader(FAJL_PUTANJA))) {
            String linija;
            while ((linija = reader.readLine()) != null) {
                String[] delovi = linija.split(",");
                if (delovi.length == 3) {
                    Korisnik k = new Korisnik(delovi[0], delovi[1], delovi[2]);
                    korisnici.add(k); // Punimo listu!
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Korisnik login(String username, String password) {
        for (Korisnik k : korisnici) {
            if (k.getUsername().equals(username) && k.getPassword().equals(password)) {
                return k; // Pronađen korisnik [cite: 63]
            }
        }
        return null; // Pogrešni podaci
    }

    public boolean registrujKorisnika(String username, String lozinka){
        for(Korisnik k: korisnici){
            if(k.getUsername().equalsIgnoreCase(username)){
                return false;
            }
        }
        Korisnik novi = new Korisnik(username,lozinka,"EKSTERNI");
        korisnici.add(novi);
        try(java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter(FAJL_PUTANJA, true))) {
            writer.write(username + "," + lozinka + ";" + "EKSTERNI");
            writer.newLine();
            return true;
        } catch (java.io.IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}