package org.skaiciuokle.paskolu_skaiciuokle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.URL;
import java.time.Year;
import java.time.YearMonth;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller implements Initializable{



    double suma;
    int metai;
    int menesiai;
    double proc;
    String grafikas;
    static XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
    Skaic.LinijineSkaiciuokle linSkc;
    Skaic.AnuitetoSkaiciuokle anuitSkc;
    Eksport.EksportuotiDuom eksp;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField piniguSuma;

    @FXML
    private TextField metProc;

    @FXML
    Label klaidosZinute;

    @FXML
    Button skaicavimoKeit;

    @FXML
    Button griztPrad;

    @FXML
    private ComboBox<Integer> metaiPas;

    @FXML
    private ComboBox<Integer> menesiaiPas;

    @FXML
    private ComboBox<String> grafikoPas;

    @FXML
    private VBox pradEkran;

    @FXML
    private SplitPane paskolosEkran;

    @FXML
    private TableView<MokejimoDuom> mokejimoLent;

    @FXML
    private TableColumn<MokejimoDuom, String> data;

    @FXML
    private TableColumn<MokejimoDuom, Double> bendrSum;

    @FXML
    private TableColumn<MokejimoDuom, Double> paskSum;

    @FXML
    private TableColumn<MokejimoDuom, Double> palSum;

    @FXML
    private TableColumn<MokejimoDuom, Double> likSum;

    @FXML
    private LineChart<Number, Number> diagrama;

    @FXML
    private ComboBox<YearMonth> atidejData;

    @FXML
    private TextField atidejProc;

    @FXML
    private VBox atidejEkran;

    @FXML
    private TextField atidejMen;


    @FXML
    private VBox filtrEkran;

    @FXML
    private ComboBox<YearMonth> nuoData;

    @FXML
    private ComboBox<YearMonth> ikiData;



    public void PiniguSuma(ActionEvent event) {
            try {
                suma = Double.parseDouble(piniguSuma.getText());
                if (metaiPas.getSelectionModel().getSelectedItem() == null) {
                    throw new NoSuchFieldException("Nepasirinkti metai");
                }
                metai = Integer.parseInt(metaiPas.getSelectionModel().getSelectedItem().toString());
                if (menesiaiPas.getSelectionModel().getSelectedItem() == null) {
                    throw new NoSuchFieldException("Nepasirinkti menesiai");
                }
                menesiai = Integer.parseInt(menesiaiPas.getSelectionModel().getSelectedItem().toString());
                if (grafikoPas.getSelectionModel().getSelectedItem() == null) {
                    throw new NoSuchFieldException("Nepasirinktas grafikas");
                }
                grafikas = grafikoPas.getSelectionModel().getSelectedItem();
                proc = Double.parseDouble(metProc.getText());
                klaidosZinute.setText("");
                pradEkran.setVisible(false);

                paskolosLang();

                paskolosEkran.setVisible(true);


            } catch (NumberFormatException e) {
                klaidosZinute.setText("Ivestas ne skaicius arba nieko neivesta");
            } catch (NoSuchFieldException e) {
                klaidosZinute.setText(e.getMessage());

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    public void atnaujintDuom() {
        /*
        suma = 0;
        metai = 0;
        menesiai = 0;
        proc = 0;
        grafikas = null;
        piniguSuma.clear();
        metaiPas.valueProperty().setValue(null);
        menesiaiPas.valueProperty().setValue(null);
        grafikoPas.valueProperty().setValue(null);
        metProc.clear();
         */
        linSkc = null;
        anuitSkc = null;

        paskolosEkran.setVisible(false);
        pradEkran.setVisible(true);
    }

    public void pakeitGrafik() {
        try {
            if (Objects.equals(grafikas, "Linijinis")) {
                grafikas = "Anuiteto";
            }
            else if (Objects.equals(grafikas, "Anuiteto")) {
                grafikas = "Linijinis";
            }
            else {
                throw new Exception("Nepavyksta pakeisti Grafiko");
            }
            paskolosLang();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void paskolosLang() throws Exception {

            if (Objects.equals(grafikas, "Linijinis")) {
                linSkc = new Skaic.LinijineSkaiciuokle(diagrama, mokejimoLent, metai, menesiai, suma, proc, grafikas);
                linSkc.skaiciuoti();
            } else if (Objects.equals(grafikas, "Anuiteto")) {
                anuitSkc = new Skaic.AnuitetoSkaiciuokle(diagrama, mokejimoLent, metai, menesiai, suma, proc, grafikas);
                anuitSkc.skaiciuoti();
            } else {
                throw new Exception("Nepavykta sukurti grafiko su lentele");
            }

    }

    public void DuomEksportas() {
        eksp = new Eksport.EksportuotiDuom(mokejimoLent);
        try {
            eksp.Eksportuoti();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    public void AtidejEkranas() throws Exception {
        paskolosLang();
        paskolosEkran.setVisible(false);
        atidejEkran.setVisible(true);
        GeneruotiPas(atidejData);
    }


    public void SkaiciuotiAtidej() {
        double proc = Double.parseDouble(atidejProc.getText());
        YearMonth data = atidejData.getSelectionModel().getSelectedItem();

        int nuoMen = ((data.getYear() - YearMonth.now().getYear()) * 12) + data.getMonthValue() - menesiai;
        int pliusMen = Integer.parseInt(atidejMen.getText());

        if (Objects.equals(this.grafikas, "Linijinis")) {
            linSkc.skaiciuotiAtidejim(proc,nuoMen,pliusMen, suma);
        } else if (Objects.equals(this.grafikas, "Anuiteto")) {
            anuitSkc.skaiciuotiAtidejim(proc,nuoMen,pliusMen, suma);
        }
        paskolosEkran.setVisible(true);
        atidejEkran.setVisible(false);
    }

    public void FiltrEkran() throws Exception {
        paskolosLang();
        paskolosEkran.setVisible(false);
        filtrEkran.setVisible(true);
        GeneruotiPas(nuoData);
        GeneruotiPas(ikiData);

    }

    public void filtData() {
        YearMonth nuoData = this.nuoData.getSelectionModel().getSelectedItem();
        YearMonth ikiData = this.ikiData.getSelectionModel().getSelectedItem();
        if (ikiData.getYear() - nuoData.getYear() > 0 || (ikiData.getYear() == nuoData.getYear() && ikiData.getMonthValue() >= nuoData.getMonthValue())) {
            int ikiMen = ((ikiData.getYear() - YearMonth.now().getYear()) * 12) + ikiData.getMonthValue() - menesiai;
            int nuoMen = ((nuoData.getYear() - YearMonth.now().getYear()) * 12) + nuoData.getMonthValue() - menesiai - 1;

            int lentelesDyd = mokejimoLent.getItems().size();
            int seriesDyd = series.getData().size();

            if (nuoMen < 0) {
                nuoMen = 0;
            }
            if (ikiMen < 0) {
                ikiMen = 0;
            }
            if (nuoMen > lentelesDyd) {
                nuoMen = lentelesDyd;
            }
            if (ikiMen > lentelesDyd) {
                ikiMen = lentelesDyd;
            }
            if (nuoMen > seriesDyd) {
                nuoMen = seriesDyd;
            }
            if (ikiMen > seriesDyd) {
                ikiMen = seriesDyd;
            }

            if (ikiMen < lentelesDyd) {
                mokejimoLent.getItems().remove(ikiMen, lentelesDyd);
            }
            if (nuoMen > 0) {
                mokejimoLent.getItems().remove(0, nuoMen);
            }

            if (ikiMen < seriesDyd) {
                series.getData().remove(ikiMen, seriesDyd);
            }
            if (nuoMen > 0) {
                series.getData().remove(0, nuoMen);
            }

            diagrama.getData().clear();
            diagrama.getData().add(series);
            diagrama.layout();

            filtrEkran.setVisible(false);
            paskolosEkran.setVisible(true);
        }
    }

    private void GeneruotiPas(ComboBox<YearMonth> box) {

        int menesiaiMoket = ((metai - Year.now().getValue()) * 12) + menesiai - YearMonth.now().getMonthValue();
        for (int i = 0; i < menesiaiMoket; ++i) {
            box.getItems().add(YearMonth.now().plusMonths(i + 1));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        xAxis.setAutoRanging(false);
        yAxis.setAutoRanging(true);

        xAxis.setLabel("Metai");
        yAxis.setLabel("Įmokos dydis");

        diagrama.setCreateSymbols(false);
        diagrama.setLegendVisible(false);

        for (int i = 0; i < 12; ++i)
        {
            menesiaiPas.getItems().add(i + 1);
        }
        for (int i = 0; i < 101; ++i)
        {
            metaiPas.getItems().add(i + Year.now().getValue() + 1);
        }

        grafikoPas.getItems().add("Linijinis");
        grafikoPas.getItems().add("Anuiteto");

        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        bendrSum.setCellValueFactory(new PropertyValueFactory<>("bendrSum"));
        paskSum.setCellValueFactory(new PropertyValueFactory<>("paskSum"));
        palSum.setCellValueFactory(new PropertyValueFactory<>("palSum"));
        likSum.setCellValueFactory(new PropertyValueFactory<>("likSum"));
    }
}
