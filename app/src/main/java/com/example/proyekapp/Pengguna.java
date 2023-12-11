package com.example.proyekapp;

public class Pengguna {
    private String inputNama;
    private String inputTanggal;
    private String inputjnsKelamin;
    private String inputNoTelepon;
    private String inputEmail;
    private String inputPassword;

    public Pengguna(){

    }

    public Pengguna(String inputNama, String inputTanggal, String inputjnsKelamin, String inputNoTelepon, String inputEmail, String inputPassword) {
        this.inputNama = inputNama;
        this.inputTanggal = inputTanggal;
        this.inputjnsKelamin = inputjnsKelamin;
        this.inputNoTelepon = inputNoTelepon;
        this.inputEmail = inputEmail;
        this.inputPassword = inputPassword;
    }

    public String getInputNama() {
        return inputNama;
    }

    public void setInputNama(String inputNama) {
        this.inputNama = inputNama;
    }

    public String getInputTanggal() {
        return inputTanggal;
    }

    public void setInputTanggal(String inputTanggal) {
        this.inputTanggal = inputTanggal;
    }

    public String getInputjnsKelamin() {
        return inputjnsKelamin;
    }

    public void setInputjnsKelamin(String inputjnsKelamin) {
        this.inputjnsKelamin = inputjnsKelamin;
    }

    public String getInputNoTelepon() {
        return inputNoTelepon;
    }

    public void setInputNoTelepon(String inputNoTelepon) {
        this.inputNoTelepon = inputNoTelepon;
    }

    public String getInputEmail() {
        return inputEmail;
    }

    public void setInputEmail(String inputEmail) {
        this.inputEmail = inputEmail;
    }

    public String getInputPassword() {
        return inputPassword;
    }

    public void setInputPassword(String inputPassword) {
        this.inputPassword = inputPassword;
    }
}
