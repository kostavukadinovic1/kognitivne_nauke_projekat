package org.example.kognitivne_nauke.controller;

import lombok.Builder;
import org.example.kognitivne_nauke.view.EksterniKorisnikProzor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.print.attribute.standard.ReferenceUriSchemesSupported.FILE;

public class EksterniKorisnikController {
    private EksterniKorisnikProzor ekProzor;
    private Connection konekcija;
    private String ulogovaniUsername;


    public EksterniKorisnikController(EksterniKorisnikProzor ekProzor, Connection konekcija, String ulogovaniUsername) {
        this.ekProzor = ekProzor;
        this.konekcija = konekcija;
        this.ulogovaniUsername = ulogovaniUsername;

        ucitajLaboratorijeIIstrazivace();

        this.ekProzor.getBtnAzurirajNalog().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String novoIme = ekProzor.getTxtNovoKorisnickoIme().getText().trim();
                String novaLozinka = new String(ekProzor.getTxtNovaLozinka().getPassword());

                if (novoIme.isEmpty() || novaLozinka.isEmpty()) {
                    JOptionPane.showMessageDialog(ekProzor, "Sva polja moraju biti popunjena!", "Greška", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                boolean uspeh = azurirajNalog(EksterniKorisnikController.this.ulogovaniUsername, novoIme, novaLozinka);

                    if (uspeh) {
                        azurirajNalogUFajlu(EksterniKorisnikController.this.ulogovaniUsername,novoIme,novaLozinka);
                        JOptionPane.showMessageDialog(ekProzor, "Nalog je uspešno ažuriran!");

                        EksterniKorisnikController.this.ulogovaniUsername = novoIme;
                    } else {
                        JOptionPane.showMessageDialog(ekProzor, "Greška pri ažuriranju naloga.", "Greška", JOptionPane.ERROR_MESSAGE);
                    }
                 }
            });

        this.ekProzor.getBtnObrisiNalog().addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                String unetaLozinka = new String(ekProzor.getTxtPotvrdaLozinkeZaBrisanje().getPassword());

                if(unetaLozinka.isEmpty()){
                    JOptionPane.showMessageDialog(ekProzor, "Morate uneti lozinku za potvrdu brisanja!", "Upozorenje", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean lozinkaTacna = proveriLozinku(EksterniKorisnikController.this.ulogovaniUsername, unetaLozinka);

                if(!lozinkaTacna){
                    JOptionPane.showMessageDialog(ekProzor, "Uneli ste pogrešnu lozinku!", "Greška", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                boolean obrisanoUBAZI = obrisiNalog(EksterniKorisnikController.this.ulogovaniUsername);

                if(obrisanoUBAZI){
                    obrisiNalogIzFajla(EksterniKorisnikController.this.ulogovaniUsername);
                    JOptionPane.showMessageDialog(ekProzor, "Vaš nalog je trajno obrisan. Aplikacija se zatvara.");
                    ekProzor.dispose();
                    System.exit(0);
                }else{
                    JOptionPane.showMessageDialog(ekProzor, "Greška prilikom brisanja naloga.", "Greška", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private boolean proveriLozinku(String username, String lozinka){
        String sql = "SELECT * FROM korisnik WHERE username = ? AND password = ?";
        try(PreparedStatement psProveri = konekcija.prepareStatement(sql)){
            psProveri.setString(1,username);
            psProveri.setString(2,lozinka);

            try (ResultSet rs = psProveri.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean azurirajNalog(String staroIme, String novoIme, String novaLozinka) {
        String sql = "UPDATE korisnik SET username = ?, password = ? WHERE username = ?";

        try(PreparedStatement psAzuriraj = konekcija.prepareStatement(sql)){
            psAzuriraj.setString(1,novoIme);
            psAzuriraj.setString(2,novaLozinka);
            psAzuriraj.setString(3,staroIme);

            int izmenjenoRedova = psAzuriraj.executeUpdate();
            return izmenjenoRedova > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean obrisiNalog(String username){
            String brisanjeSql = "DELETE FROM korisnik WHERE username = ?";

            try(PreparedStatement psBrisanje = konekcija.prepareStatement(brisanjeSql)){
                psBrisanje.setString(1,username);

                int obrisano = psBrisanje.executeUpdate();
                return obrisano > 0;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }


    public void ucitajLaboratorijeIIstrazivace(){
        DefaultTableModel model = ekProzor.getTableModel();
        model.setRowCount(0);

        String sql = "SELECT \n" +
                "    l.naziv AS Laboratorija, \n" +
                "    l.lokacija AS Lokacija, \n" +
                "    CONCAT(ist.ime, ' ', ist.prezime) AS Istrazivac, \n" +
                "    ist.kvalifikacija AS Kvalifikacija\n" +
                "FROM laboratorija l\n" +
                "JOIN izvodjenje i ON i.laboratorija_id = l.laboratorija_id\n" +
                "JOIN izvodjac_tim it ON it.izvodjenje_id = i.izvodjenje_id\n" +
                "JOIN istrazivac ist ON ist.istrazivac_id = it.istrazivac_id;";

        try (Statement stmt = konekcija.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String lab = rs.getString("Laboratorija");
                String lokacija = rs.getString("Lokacija");
                String istrazivac = rs.getString("Istrazivac");
                String kvalifikacija = rs.getString("Kvalifikacija");

                model.addRow(new Object[]{lab, lokacija, istrazivac, kvalifikacija});
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void azurirajNalogUFajlu(String staroIme, String novoIme, String novaLozinka) {
        File stariFajl = new File("korisnici.txt");
        List<String> linije = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(stariFajl))) {
            String linija;
            while ((linija = br.readLine()) != null) {
                String[] delovi = linija.split(";");
                if (delovi[0].equals(staroIme)) {
                    linije.add(novoIme + ";" + novaLozinka + ";" + delovi[2]);
                } else {
                    linije.add(linija);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter(stariFajl))) {
            for (String l : linije) {
                pw.println(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void obrisiNalogIzFajla(String username) {
        File stariFajl = new File("korisnici.txt");
        List<String> linije = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(stariFajl))) {
            String linija;
            while ((linija = br.readLine()) != null) {
                String[] delovi = linija.split(";");
                if(!(delovi[0].equals(username))){
                    linije.add(linija);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Na kraju prebrisujemo fajl sa preostalim korisnicima
        try (PrintWriter pw = new PrintWriter(new FileWriter(stariFajl))) {
            for (String l : linije) {
                pw.println(l);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

