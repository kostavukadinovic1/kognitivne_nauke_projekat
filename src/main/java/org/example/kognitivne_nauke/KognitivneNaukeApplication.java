package org.example.kognitivne_nauke;

import org.example.kognitivne_nauke.controller.LoginController;
import org.example.kognitivne_nauke.model.KorisnikServis;
import org.example.kognitivne_nauke.view.LoginProzor;
import org.example.kognitivne_nauke.KonekcijaBaze;
import org.example.kognitivne_nauke.view.RegisterProzor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;

@SpringBootApplication
public class KognitivneNaukeApplication {

    public static void main(String[] args) {
        Connection konekcija = KonekcijaBaze.kreirajKonekciju();
        if (konekcija == null) {
            System.out.println("UPOZORENJE: Aplikacija je podignuta bez baze podataka. SQL upiti neće raditi!");
        }
        KorisnikServis ks = new KorisnikServis(konekcija);
        LoginProzor lp = new LoginProzor();
        LoginController controller = new LoginController(lp,ks,konekcija);
    }

}
