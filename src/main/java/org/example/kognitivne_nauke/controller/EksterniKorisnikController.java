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
                boolean uspeh = azurirajNalogUFajlu(EksterniKorisnikController.this.ulogovaniUsername, novoIme, novaLozinka);

                    if (uspeh) {
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
                boolean obrisano = obrisiNalogIzFajla(EksterniKorisnikController.this.ulogovaniUsername);

                if(obrisano){
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
        File fajl = new File("korisnici.txt");
        try(BufferedReader br = new BufferedReader(new FileReader(fajl))) {
            String linija;
            while((linija = br.readLine())!= null){
                if(linija.startsWith(username + ";")){
                    String[] delovi = linija.split(";");
                    if(delovi.length >= 2){
                        return delovi[1].equals(lozinka);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
        return false;
    }

    private boolean azurirajNalogUFajlu(String staroIme, String novoIme, String novaLozinka) {
        File fajl = new File("korisnici.txt");
        List<String> sveLinije = new ArrayList<>();
        boolean pronadjen = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fajl))) {
            String linija;
            while ((linija = br.readLine()) != null) {
                if (linija.startsWith(staroIme + ";")) {
                    String novaLinija = novoIme + ";" + novaLozinka + ";" + "EKSTERNI";
                    sveLinije.add(novaLinija);
                    pronadjen = true;
                } else {
                    sveLinije.add(linija);
                }

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (pronadjen) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(fajl))) {
                for (String l : sveLinije) {
                    pw.println(l);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    private boolean obrisiNalogIzFajla(String username){
        File fajl = new File("korisnici.txt");
        List<String> sveLinije = new ArrayList<>();
        boolean pronadjen = false;

        try(BufferedReader br = new BufferedReader(new FileReader(fajl))) {
            String linija;
            while((linija = br.readLine())!= null){
                if(linija.startsWith(username + ";")){
                    pronadjen = true;
                }else{
                    sveLinije.add(linija);
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }if(pronadjen){
            try(PrintWriter pw = new PrintWriter(new FileWriter(fajl))){
                for(String l: sveLinije){
                    pw.println(l);
                }
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return false;
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
}

