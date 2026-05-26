package org.example.kognitivne_nauke.model;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class KorisnikServis {
    private static final String FAJL_PUTANJA = "korisnici.txt";
    private Connection konekcija;


    public KorisnikServis(Connection konekcija) {
        this.konekcija = konekcija;

        File fajl = new File(FAJL_PUTANJA);
        if (!fajl.exists()) {
            try (PrintWriter out = new PrintWriter(new FileWriter(fajl))) {
                out.println("admin;admin123;ADMIN");
                out.println("istrazivac;istrazivac123;ISTRAZIVAC");
                out.println("eksterni;eksterni123;EKSTERNI");

            } catch (IOException e) {
                System.out.println("Greška prilikom kreiranja fajla: " + e.getMessage());
            }
        }
    }

    public Korisnik login(String username, String password) {
        String sql = "SELECT * FROM korisnik WHERE username = ? AND password = ?";

        try(PreparedStatement psLogin = konekcija.prepareStatement(sql)) {
            psLogin.setString(1, username);
            psLogin.setString(2, password);

            try (ResultSet rs = psLogin.executeQuery()) {
                if (rs.next()) {
                    return new Korisnik(rs.getString("username"), rs.getString("password"), rs.getString("role"));
                }
            }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        return null;
        }
        public boolean registrujKorisnika(String username, String lozinka){
        String sql = "INSERT INTO korisnik (username, password, role) VALUES (?,?,'EKSTERNI')";
        try(PreparedStatement psRegistruj = konekcija.prepareStatement(sql)){
            psRegistruj.setString(1,username);
            psRegistruj.setString(2,lozinka);
            psRegistruj.executeUpdate();

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FAJL_PUTANJA, true))) {
                writer.write(username + ";" + lozinka + ";EKSTERNI");
                writer.newLine();
            }
            return true;
        }
     catch (SQLException  | IOException e) {
            return false;
        }
    }
}