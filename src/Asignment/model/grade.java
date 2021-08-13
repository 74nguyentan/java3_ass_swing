/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Asignment.model;


import java.io.Serializable;

public class grade implements Serializable {

    public String MaSV, Name;
    public double TiengAnh, TinHoc, GDTC, avg;
    public int ID;

    public grade(int ID,String MaSV, String name, double TiengAnh, double TinHoc, double GDTC, double avg) {
        this.Name = name;
        this.MaSV = MaSV;
        this.ID = ID;
        this.TiengAnh = TiengAnh;
        this.TinHoc = TinHoc;
        this.GDTC = GDTC;
        this.avg = avg;
    }

    public grade() {
    }

    public String getMaSV() {
        return MaSV;
    }

    public void setMaSV(String MaSV) {
        this.MaSV = MaSV;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getTiengAnh() {
        return TiengAnh;
    }

    public void setTiengAnh(double TiengAnh) {
        this.TiengAnh = TiengAnh;
    }

    public double getTinHoc() {
        return TinHoc;
    }

    public void setTinHoc(double TinHoc) {
        this.TinHoc = TinHoc;
    }

    public double getGDTC() {
        return GDTC;
    }

    public void setGDTC(double GDTC) {
        this.GDTC = GDTC;
    }

    public double getAvg() {
        avg = (TiengAnh + TinHoc + GDTC) / 3 ;
        return Math.ceil(avg*1000)/1000;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}

