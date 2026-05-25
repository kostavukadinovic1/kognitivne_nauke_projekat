package org.example.kognitivne_nauke;

import org.example.kognitivne_nauke.controller.LoginController;
import org.example.kognitivne_nauke.model.KorisnikServis;
import org.example.kognitivne_nauke.view.LoginProzor;
import org.example.kognitivne_nauke.view.RegisterProzor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KognitivneNaukeApplication {

    public static void main(String[] args) {
        KorisnikServis ks = new KorisnikServis();
        LoginProzor lp = new LoginProzor();
        LoginController controller = new LoginController(lp,ks);
    }

}
