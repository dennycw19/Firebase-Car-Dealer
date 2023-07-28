package com.example.cardealerproject;

//deklarasi kelas mobil
public class Mobil {

    String id;
    String kd_brg;
    String merk_mobil;
    Integer tahun_produksi;
    String jenis;
    String keterangan;
    Integer jumlah_stok;

    public Mobil(){
        //jangan dihapus
    }

    public Mobil(String id,String kd_brg, String merk_mobil, Integer tahun_produksi, String jenis, String keterangan, Integer jumlah_stok) {
        this.id = id;
        this.kd_brg = kd_brg;
        this.merk_mobil = merk_mobil;
        this.tahun_produksi = tahun_produksi;
        this.jenis = jenis;
        this.keterangan = keterangan;
        this.jumlah_stok = jumlah_stok;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKd_brg() {
        return kd_brg;
    }

    public void setKd_brg(String kd_brg) {
        this.kd_brg = kd_brg;
    }

    public String getMerk_mobil() {
        return merk_mobil;
    }

    public void setMerk_mobil(String merk_mobil) {
        this.merk_mobil = merk_mobil;
    }

    public Integer getTahun_produksi() {
        return tahun_produksi;
    }

    public void setTahun_produksi(Integer tahun_produksi) {
        this.tahun_produksi = tahun_produksi;
    }

    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public Integer getJumlah_stok() {
        return jumlah_stok;
    }

    public void setJumlah_stok(Integer jumlah_stok) {
        this.jumlah_stok = jumlah_stok;
    }
}
