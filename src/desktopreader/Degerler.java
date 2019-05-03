/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopreader;

import java.util.Comparator;

/**
 *
 * @author uset
 */
public class Degerler implements Comparable<Degerler>{
    
    protected double benzerlik;
    protected int id;
    protected int isbn_sayi;
    protected double benzerlik_katsayi;
    
    public Degerler(double benzerlik,int id,int isbn_sayi,double benzerlik_katsayi){
        this.benzerlik=benzerlik;
        this.id=id;
        this.isbn_sayi=isbn_sayi;
        this.benzerlik_katsayi=benzerlik_katsayi;
    }

    @Override
    public int compareTo(Degerler o) {
        return new Double(o.benzerlik_katsayi).compareTo(benzerlik_katsayi);
    }
    
    
}
