package com.example.cardealerproject;

//deklarasi kelas transaksi
public class Trans {
    String id;
    String kdbrgtrans;
    String tgl_jual;
    String no_faktur;
    Integer jml_jual;
    Integer hrg_jual;
    Integer total_jual;
    String status;

    public Trans(){
        //jangan dihapus
    }

    public Trans(String id, String kdbrgtrans, String tgl_jual, String no_faktur, Integer jml_jual, Integer hrg_jual, Integer total_jual, String status) {
        this.kdbrgtrans = kdbrgtrans;
        this.id = id;
        this.tgl_jual = tgl_jual;
        this.no_faktur = no_faktur;
        this.jml_jual = jml_jual;
        this.hrg_jual = hrg_jual;
        this.total_jual = total_jual;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKdbrgtrans() {
        return kdbrgtrans;
    }

    public void setKdbrgtrans(String kdbrgtrans) {
        this.kdbrgtrans = kdbrgtrans;
    }

    public String getTgl_jual() {
        return tgl_jual;
    }

    public void setTgl_jual(String tgl_jual) {
        this.tgl_jual = tgl_jual;
    }

    public String getNo_faktur() {
        return no_faktur;
    }

    public void setNo_faktur(String no_faktur) {
        this.no_faktur = no_faktur;
    }

    public Integer getJml_jual() {
        return jml_jual;
    }

    public void setJml_jual(Integer jml_jual) {
        this.jml_jual = jml_jual;
    }

    public Integer getHrg_jual() {
        return hrg_jual;
    }

    public void setHrg_jual(Integer hrg_jual) {
        this.hrg_jual = hrg_jual;
    }

    public Integer getTotal_jual() {
        return total_jual;
    }

    public void setTotal_jual(Integer total_jual) {
        this.total_jual = total_jual;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
