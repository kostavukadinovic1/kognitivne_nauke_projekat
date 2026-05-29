package org.example.kognitivne_nauke.controller;

import org.example.kognitivne_nauke.model.Korisnik;
import org.example.kognitivne_nauke.model.KorisnikServis;
import org.example.kognitivne_nauke.view.EksterniKorisnikProzor;
import org.example.kognitivne_nauke.view.LoginProzor;
import org.example.kognitivne_nauke.view.RegisterProzor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

public class LoginController {
    private LoginProzor prozorLogin;
    private KorisnikServis servis;
    private Connection konekcija;

    public LoginController(LoginProzor prozorLogin, KorisnikServis servis, Connection konekcija){
        this.prozorLogin = prozorLogin;
        this.servis = servis;
        this.konekcija = konekcija;

        this.prozorLogin.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String unetoIme = prozorLogin.getTxtUsername().getText();
                String unetaLozinka = new String(prozorLogin.getTxtPassword().getPassword());

                Korisnik k = servis.login(unetoIme, unetaLozinka);

                if (k != null) {
                    if (k.getRole().trim().equalsIgnoreCase("EKSTERNI")) {
                        prozorLogin.dispose();
                        EksterniKorisnikProzor ekProzor = new EksterniKorisnikProzor();
                        new EksterniKorisnikController(ekProzor, LoginController.this.konekcija, unetoIme);
                        ekProzor.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(prozorLogin, "Uspešna prijava! Vaša uloga je: " + k.getRole());
                    }
                } else {
                    JOptionPane.showMessageDialog(prozorLogin, "Pogresno korisnicko ime ili lozinka!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        this.prozorLogin.getBtnRegister().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prozorLogin.setVisible(false);

                RegisterProzor regProzor = new RegisterProzor();

                new RegisterController(regProzor, servis, prozorLogin);

                regProzor.setVisible(true);
            }
        });
    }
}