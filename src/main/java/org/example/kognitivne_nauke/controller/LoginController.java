package org.example.kognitivne_nauke.controller;

import org.example.kognitivne_nauke.model.Korisnik;
import org.example.kognitivne_nauke.model.KorisnikServis;
import org.example.kognitivne_nauke.view.LoginProzor;
import org.example.kognitivne_nauke.view.RegisterProzor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginProzor prozorLogin;
    private KorisnikServis servis;

    public LoginController(LoginProzor prozorLogin, KorisnikServis servis){
        this.prozorLogin = prozorLogin;
        this.servis = servis;

        this.prozorLogin.getBtnLogin().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String unetoIme = prozorLogin.getTxtUsername().getText();

                String unetaLozinka = new String(prozorLogin.getTxtPassword().getPassword());

                Korisnik k = servis.login(unetoIme,unetaLozinka);

                if(k != null){
                    JOptionPane.showMessageDialog(prozorLogin, "Uspesna prijava! Vasa uloga je: " + k.getRole());
                }else{
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


