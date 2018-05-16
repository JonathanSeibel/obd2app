package de.dhbw.obdzweidashboard.obd2_application;

/**
 * Created by JZX8NT on 01.05.2018.
 */

public class TestZahlenCreator {

    double zufall = 0;

    static double geschwindigkeit;
    static double drehzahl;
    static double temperatur;
    static double winkel;
    static double ladedruck;
    static double verbrauch;

    static String sgeschwindigkeit = "0";
    static String sdrehzahl = "0";
    static String stemperatur = "50";
    static String swinkel = "0";
    static String sladedruck = "0";
    static String sverbrauch = "0";

    public static double generate(double aktuell, int obereGrenze, int untereGrenze, double multiplikator){
        if (aktuell<obereGrenze){
            double entscheid = Math.random()*10;
            if (entscheid<2){
                return aktuell;
            }
            if (entscheid<8){
                return aktuell+1*multiplikator;
            }
            else return aktuell+5*multiplikator;
        }
        else return untereGrenze;
    }

    public static String genGeschwindigkeit(String ges){
        geschwindigkeit = Double.parseDouble(ges);
        geschwindigkeit = generate(geschwindigkeit, 255, 0, 1);
        return Double.toString(geschwindigkeit);
    }
    public static String genDrehzahl(String dre){
        drehzahl = Double.parseDouble(dre);
        drehzahl = generate(drehzahl, 6500, 0, 200);
        return Double.toString(drehzahl);
    }
    public static String genTemperatur(String tem){
        temperatur = Double.parseDouble(tem);
        temperatur = generate(temperatur, 100, 20, 1);
        return Double.toString(temperatur);
    }
    public static String genWinkel(String win){
        winkel = Double.parseDouble(win);
        winkel = generate(winkel, 100, 0, 1);
        return Double.toString(winkel);
    }
    public static String genLadedruck(String lad){
        ladedruck = Double.parseDouble(lad);
        ladedruck = generate(ladedruck, 3,0,0.01);
        return Double.toString(ladedruck);
    }
    public static String genVerbaruch(String veb){
        verbrauch = Double.parseDouble(veb);
        verbrauch = generate(verbrauch, 20, 0, 1);
        return Double.toString(geschwindigkeit);
    }

    public static String[] genAlles(){
        sgeschwindigkeit = TestZahlenCreator.genGeschwindigkeit(sgeschwindigkeit);
        sdrehzahl = TestZahlenCreator.genDrehzahl(sdrehzahl);
        stemperatur = TestZahlenCreator.genTemperatur(stemperatur);
        sladedruck = TestZahlenCreator.genLadedruck(sladedruck);
        swinkel = TestZahlenCreator.genWinkel(swinkel);
        sverbrauch = TestZahlenCreator.genDrehzahl(sverbrauch);

        String[] result = new String[6];
        result[0]=sgeschwindigkeit;
        result[1]=sdrehzahl;
        result[2]=stemperatur;
        result[3]=sladedruck;
        result[4]=swinkel;
        result[5]=sverbrauch;

        return result;
    }



    public static void main(String args) {
        int counter = 0;
        String[] rückgabe = new String[6];


        while(true){
            if(counter==50){
                rückgabe = genAlles();
            }
            counter++;
            if(counter==100) counter=0;


        }
    }
}