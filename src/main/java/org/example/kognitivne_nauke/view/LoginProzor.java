package org.example.kognitivne_nauke.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class LoginProzor extends JFrame {

    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Prijavi se");
    private JButton btnRegister = new JButton("Registruj se");


    public LoginProzor(){
        setTitle("Prijava na sistem");
        setSize(350,200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel panelForm = new JPanel(new GridLayout(2,2,5,5));

        panelForm.add(new JLabel("Korisnicko ime:"));
        panelForm.add(txtUsername);
        panelForm.add(new JLabel("Lozinka:"));
        panelForm.add(txtPassword);

        JPanel panelDugmici = new JPanel(new FlowLayout());

        panelDugmici.add(btnLogin);
        panelDugmici.add(btnRegister);

        add(panelForm, BorderLayout.CENTER);

        add(panelDugmici, BorderLayout.SOUTH);

        setVisible(true);
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

    public JButton getBtnLogin() {
        return btnLogin;
    }

    public void setBtnLogin(JButton btnLogin) {
        this.btnLogin = btnLogin;
    }

    public JButton getBtnRegister() {
        return btnRegister;
    }

    public void setBtnRegister(JButton btnRegister) {
        this.btnRegister = btnRegister;
    }
}
