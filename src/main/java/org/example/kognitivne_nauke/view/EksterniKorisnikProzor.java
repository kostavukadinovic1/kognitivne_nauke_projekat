package org.example.kognitivne_nauke.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class EksterniKorisnikProzor extends JFrame {
    private JTable tabelaLabIstrazivaci;
    private DefaultTableModel tableModel;

    private JTextField txtNovoKorisnickoIme = new JTextField(15);
    private JPasswordField txtNovaLozinka = new JPasswordField(15);
    private JButton btnAzurirajNalog = new JButton("Azuriraj nalog");

    private JPasswordField txtPotvrdaLozinkeZaBrisanje = new JPasswordField(10);
    private JButton btnObrisiNalog = new JButton("Obrisi nalog");

    public EksterniKorisnikProzor(){
        setTitle("Eksterni korisnik - Upravljanje i pregled");
        setSize(900,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10,10));

        JPanel panelPregled = new JPanel(new BorderLayout());
        panelPregled.setBorder(BorderFactory.createTitledBorder("Pregled laboratorija i istrazivaca"));

        String[] kolone = {"Laboratorija", "Lokacija", "Istrazivac(Ime i prezime)", "Kvalifikacija"};
        tableModel = new DefaultTableModel(kolone,0);
        tabelaLabIstrazivaci = new JTable(tableModel);
        panelPregled.add(new JScrollPane(tabelaLabIstrazivaci), BorderLayout.CENTER);

        add(panelPregled, BorderLayout.CENTER);

        JPanel panelDesno = new JPanel();
        panelDesno.setLayout(new BoxLayout(panelDesno, BoxLayout.Y_AXIS));
        panelDesno.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        JPanel panelAzuriranje = new JPanel(new GridLayout(3,2,5,5));
        panelAzuriranje.setBorder(BorderFactory.createTitledBorder("Azuriranje naloga"));
        panelAzuriranje.add(new Label("Novo korisnicko ime"));
        panelAzuriranje.add(txtNovoKorisnickoIme);
        panelAzuriranje.add(new Label("Nova lozinka"));
        panelAzuriranje.add(txtNovaLozinka);
        panelAzuriranje.add(btnAzurirajNalog);

        JPanel panelBrisanje = new JPanel(new GridLayout(2,2,5,5));
        panelBrisanje.setBorder(BorderFactory.createTitledBorder("Brisanje naloga"));
        panelBrisanje.add(new Label("Unesite lozinku za potvrdu"));
        panelBrisanje.add(txtPotvrdaLozinkeZaBrisanje);
        panelBrisanje.setBackground(Color.red);
        panelBrisanje.setForeground(Color.white);
        panelBrisanje.add(btnObrisiNalog);

        panelDesno.add(panelAzuriranje);
        panelDesno.add(Box.createRigidArea(new Dimension(0,20)));
        panelDesno.add(panelBrisanje);

        add(panelDesno,BorderLayout.EAST);
    }

    public JTable getTabelaLabIstrazivaci() {
        return tabelaLabIstrazivaci;
    }

    public void setTabelaLabIstrazivaci(JTable tabelaLabIstrazivaci) {
        this.tabelaLabIstrazivaci = tabelaLabIstrazivaci;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTextField getTxtNovoKorisnickoIme() {
        return txtNovoKorisnickoIme;
    }

    public void setTxtNovoKorisnickoIme(JTextField txtNovoKorisnickoIme) {
        this.txtNovoKorisnickoIme = txtNovoKorisnickoIme;
    }

    public JPasswordField getTxtNovaLozinka() {
        return txtNovaLozinka;
    }

    public void setTxtNovaLozinka(JPasswordField txtNovaLozinka) {
        this.txtNovaLozinka = txtNovaLozinka;
    }

    public JButton getBtnAzurirajNalog() {
        return btnAzurirajNalog;
    }

    public void setBtnAzurirajNalog(JButton btnAzurirajNalog) {
        this.btnAzurirajNalog = btnAzurirajNalog;
    }

    public JPasswordField getTxtPotvrdaLozinkeZaBrisanje() {
        return txtPotvrdaLozinkeZaBrisanje;
    }

    public void setTxtPotvrdaLozinkeZaBrisanje(JPasswordField txtPotvrdaLozinkeZaBrisanje) {
        this.txtPotvrdaLozinkeZaBrisanje = txtPotvrdaLozinkeZaBrisanje;
    }

    public JButton getBtnObrisiNalog() {
        return btnObrisiNalog;
    }

    public void setBtnObrisiNalog(JButton btnObrisiNalog) {
        this.btnObrisiNalog = btnObrisiNalog;
    }
}
