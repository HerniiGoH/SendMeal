package isi.dam.sendmeal.Domain;

import android.content.Intent;

public class Hilo extends Thread {

    public void run (){

        Intent i = new Intent("etc");
        i.putExtra("resultado","HAY UN PLATO EN OFERTA");

    }


}
