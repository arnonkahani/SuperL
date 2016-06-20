package com.Common;

import com.Workers.Objects.Date;
import com.Workers.Workers;

import java.util.Random;

/**
 * Created by dani on 19/06/2016.
 */
public class randomFill {
    public static void fill() {
        Random rnd = new Random();
        for (int i = 0; i < 15; i++) {
            String ID;
            String Name;
            String BankNO;
            Date EmpDate;

            try {
                ID = "" + (200000000 + rnd.nextInt(100000000));
                Name = nameArray[rnd.nextInt(50)];
                BankNO = "" + (rnd.nextInt(1000000000));
                EmpDate = new Date(1 + rnd.nextInt(31) + "/" + 1 + rnd.nextInt(12) + "/" + 2010 + rnd.nextInt(6));
                com.Workers.DatabaseObjects.DAL.getDAL().addWorker(ID, BankNO, EmpDate, Name);
            } catch (Exception e) {

            }
        }
    }

    private static String[] nameArray = ("Bobbie Milnes  \n" +
            "Warren Bloodsaw  \n" +
            "Milo Lusher  \n" +
            "Tamiko Alvord  \n" +
            "Larue Poplar  \n" +
            "Gwyn Burket  \n" +
            "Burt Finneran  \n" +
            "Trinity Schnur  \n" +
            "Betsy Billups  \n" +
            "Tangela Correia  \n" +
            "Cathern Laursen  \n" +
            "Rozella Fullmer  \n" +
            "Valene Pinney  \n" +
            "Luciano Pilson  \n" +
            "Jon Rollinson  \n" +
            "Fransisca Wohlwend  \n" +
            "Fern Cahall  \n" +
            "Leone Solberg  \n" +
            "Maxwell Didonato  \n" +
            "Katharyn Peter  \n" +
            "Taneka Pryor  \n" +
            "Vanesa Najera  \n" +
            "Claretta Jenkin  \n" +
            "Sharika Mcchristian  \n" +
            "Alonzo Gove  \n" +
            "Hortense Eshelman  \n" +
            "Kimber Loudermilk  \n" +
            "Denae Cada  \n" +
            "Krystin Bartmess  \n" +
            "Florance Tseng  \n" +
            "Tawny Swopes  \n" +
            "Marinda Buntin  \n" +
            "Richard Brigance  \n" +
            "Jeanette Estrella  \n" +
            "Karl Gaston  \n" +
            "Anastacia Brookins  \n" +
            "Darci Ritch  \n" +
            "Arlie Espinosa  \n" +
            "Fritz Bergmann  \n" +
            "Chere Manzer  \n" +
            "Zoraida Taube  \n" +
            "Merissa Zoller  \n" +
            "Mitchell Conners  \n" +
            "Yang Farnes  \n" +
            "Shamika Kern  \n" +
            "Desiree Ashman  \n" +
            "Devon Marse  \n" +
            "Daryl Bradstreet  \n" +
            "Mervin Glasco  \n" +
            "Geneva Rhymes").split("  \n");
}
