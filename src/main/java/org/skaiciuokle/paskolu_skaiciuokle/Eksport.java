package org.skaiciuokle.paskolu_skaiciuokle;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

public class Eksport {

    private abstract static class Eksportavimas {
        @FXML
        TableView<MokejimoDuom> mokejimoLent;

        Eksportavimas(TableView<MokejimoDuom> mokejimoLent) {
            this.mokejimoLent = mokejimoLent;
        }

        protected abstract void Eksportuoti() throws Exception;
    }

    static class EksportuotiDuom extends Eksportavimas {
        EksportuotiDuom(TableView<MokejimoDuom> mokejimoLent) {
            super(mokejimoLent);
        }

        @Override
        protected void Eksportuoti() throws Exception {
            Writer writer = null;
            try {
                File file = new File("PaskolosDuomenys.csv");
                writer = new BufferedWriter(new FileWriter(file));
                writer.write("Data,Įmokos suma,Kredito suma,Palūkanų suma,Likus suma\n");
                for (MokejimoDuom duom : mokejimoLent.getItems()) {

                    String text = duom.getData() + "," + duom.getBendrSum() + "," + duom.getPaskSum()  + "," + duom.getPalSum() + "," + duom.getLikSum() + "\n";

                    writer.write(text);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            finally {
                writer.flush();
                writer.close();
            }

        }
    }
}
