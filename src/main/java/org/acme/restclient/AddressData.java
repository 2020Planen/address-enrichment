/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.acme.restclient;

/**
 *
 * @author Mathias
 */
public class AddressData {
    public String id;
    public String vejnavn;
    public String husnr;
    public String postnr;
    public Double wgs84koordinat_bredde;
    public Double wgs84koordinat_længde;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVejnavn() {
        return vejnavn;
    }

    public void setVejnavn(String vejnavn) {
        this.vejnavn = vejnavn;
    }

    public String getHusnr() {
        return husnr;
    }

    public void setHusnr(String husnr) {
        this.husnr = husnr;
    }

    public String getPostnr() {
        return postnr;
    }

    public void setPostnr(String postnr) {
        this.postnr = postnr;
    }



    public Double getWgs84koordinat_bredde() {
        return wgs84koordinat_bredde;
    }

    public void setWgs84koordinat_bredde(Double wgs84koordinat_bredde) {
        this.wgs84koordinat_bredde = wgs84koordinat_bredde;
    }

    public Double getWgs84koordinat_længde() {
        return wgs84koordinat_længde;
    }

    public void setWgs84koordinat_længde(Double wgs84koordinat_længde) {
        this.wgs84koordinat_længde = wgs84koordinat_længde;
    }

    @Override
    public String toString() {
        return "AddressData{" + "id=" + id + ", vejnavn=" + vejnavn + ", husnr=" + husnr + ", postnr=" + postnr + ", wgs84koordinat_bredde=" + wgs84koordinat_bredde + ", wgs84koordinat_l\u00e6ngde=" + wgs84koordinat_længde + '}';
    }

 



    
    
    
    
    }

