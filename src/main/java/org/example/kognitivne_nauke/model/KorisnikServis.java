package org.example.kognitivne_nauke.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class KorisnikServis {
    private static final String FAJL_PUTANJA = "korisnici.txt";

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

    private List<Korisnik> ucitajKorisnike() {
        List<Korisnik> korisnici = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(FAJL_PUTANJA))) {
            String linija;
            while ((linija = br.readLine()) != null) {
                String[] delovi = linija.split(";");

                if (delovi.length == 3) {
                    String username = delovi[0];
                    String lozinka = delovi[1];
                    String role = delovi[2];
                    Korisnik korisnik = new Korisnik(username, lozinka, role);
                    korisnici.add(korisnik);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return korisnici;
    }


    public Korisnik login(String username, String password) {
        List<Korisnik> sviKorisnici = ucitajKorisnike();

        for(Korisnik k : sviKorisnici){
            if(k.getUsername().equals(username)){
                if(k.getPassword().equals(password)){
                    return k;
                }
            }
        }
        return null;
    }



}