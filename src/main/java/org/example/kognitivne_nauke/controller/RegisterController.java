package org.example.kognitivne_nauke.controller;

import org.example.kognitivne_nauke.model.KorisnikServis;
import org.example.kognitivne_nauke.view.LoginProzor;
import org.example.kognitivne_nauke.view.RegisterProzor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterController {
    private RegisterProzor regProzor;
    private KorisnikServis servis;
    private LoginProzor logProzor;

    public RegisterController(RegisterProzor regProzor, KorisnikServis servis,LoginProzor logProzor){
        this.regProzor = regProzor;
        this.servis = servis;
        this.logProzor = logProzor;


        this.regProzor.getBtnLogin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                regProzor.dispose();    // Ugasi registraciju
                logProzor.setVisible(true); // Vrati login prozor
            }
        });

        this.regProzor.getBtnRegistracija().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = regProzor.getTxtUsername().getText().trim();
                String lozinka = new String(regProzor.getTxtPassword().getPassword());
                String potvrda = new String(regProzor.getTxtConfirmPassword().getPassword());

                if (username.isEmpty() || lozinka.isEmpty() || potvrda.isEmpty()) {
                    JOptionPane.showMessageDialog(regProzor, "Sva polja moraju biti popunjena!", "Greska", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!lozinka.equals(potvrda)) {
                    JOptionPane.showMessageDialog(regProzor, "Lozinke se ne poklapaju!", "Greska", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Upis preko servisa
                boolean uspeh = servis.registrujKorisnika(username, lozinka);

                if (uspeh) {
                    JOptionPane.showMessageDialog(regProzor, "Uspešna registracija! Sada se možete prijaviti.", "Uspeh", JOptionPane.INFORMATION_MESSAGE);
                    regProzor.dispose();
                    logProzor.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(regProzor, "Korisničko ime je zauzeto!", "Greska", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
