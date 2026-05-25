package org.example.kognitivne_nauke.view;

import javax.swing.*;
import java.awt.*;

public class RegisterProzor extends JFrame {
    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JPasswordField txtConfirmPassword = new JPasswordField(20);
    private JButton btnRegistracija = new JButton("Registruj se");
    private JButton btnLogin = new JButton("Nazad na login");

    public RegisterProzor(){
        setTitle("Registracija novog korisnika");
        setSize(380,250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForma = new JPanel(new GridLayout(3,2,5,5));
        panelForma.add(new JLabel("Korisnicko ime"));
        panelForma.add(txtUsername);
        panelForma.add(new JLabel("Lozinka"));
        panelForma.add(txtPassword);
        panelForma.add(new JLabel("Potvrdi lozinku"));
        panelForma.add(txtConfirmPassword);

        JPanel panelDugmici = new JPanel(new FlowLayout());
        panelDugmici.add(btnRegistracija);
        panelDugmici.add(btnLogin);

        add(panelForma,BorderLayout.CENTER);
        add(panelDugmici,BorderLayout.SOUTH);
    }

    public JTextField getTxtUsername() {
        return txtUsername;
    }

    public void setTxtUsername(JTextField txtUsername) {
        this.txtUsername = txtUsername;
    }

    public JPasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(JPasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    public JPasswordField getTxtConfirmPassword() {
        return txtConfirmPassword;
    }

    public void setTxtConfirmPassword(JPasswordField txtConfirmPassword) {
        this.txtConfirmPassword = txtConfirmPassword;
    }

    public JButton getBtnRegistracija() {
        return btnRegistracija;
    }

    public void setBtnRegistracija(JButton btnRegistracija) {
        this.btnRegistracija = btnRegistracija;
    }

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }
}
