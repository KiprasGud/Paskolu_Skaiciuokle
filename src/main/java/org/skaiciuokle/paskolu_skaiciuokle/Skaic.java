package org.skaiciuokle.paskolu_skaiciuokle;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableView;

import java.time.Year;
import java.time.YearMonth;

import static org.skaiciuokle.paskolu_skaiciuokle.Controller.series;

public class Skaic {

    private abstract static class Skaiciuokle {
        @FXML
        LineChart<Number, Number> diagrama;

        @FXML
        TableView<MokejimoDuom> mokejimoLent;

        protected int metai;
        protected int menesiai;
        protected double suma;
        protected double proc;
        protected String grafikas;
        protected double kreditas;
        protected double palukanos;
        protected int menesiaiMoket;
        NumberAxis xAxis;
        NumberAxis yAxis;

        Skaiciuokle(LineChart<Number, Number> diagrama, TableView<MokejimoDuom> mokejimoLent, int metai, int menesiai,double suma, double proc, String grafikas) {
            this.diagrama = diagrama;
            this.mokejimoLent = mokejimoLent;
            this.metai = metai;
            this.menesiai = menesiai;
            this.suma = suma;
            this.proc = proc;
            this.grafikas = grafikas;
        }

        protected void init() {
            mokejimoLent.getItems().clear();
            diagrama.getData().clear();
            series.getData().clear();

            menesiaiMoket = ((metai - Year.now().getValue()) * 12) +  menesiai - YearMonth.now().getMonthValue();
            kreditas = suma / menesiaiMoket;

            xAxis = (NumberAxis) diagrama.getXAxis();
            yAxis = (NumberAxis) diagrama.getYAxis();

            xAxis.setLowerBound(Year.now().getValue());
            xAxis.setUpperBound(metai + 1);
            xAxis.setTickUnit(1);
            yAxis.setLowerBound(0);
            yAxis.setUpperBound(Math.round(kreditas + suma * (proc / 100) / 12 + ((kreditas + suma * (proc / 100) / 12) / 3)));
            yAxis.setTickUnit(10);


        }

        protected abstract void skaiciuoti();

        protected abstract void skaiciuotiAtidejim(double atidProc, int nuoMen, int pliusMen, double suma);
    }

    static class LinijineSkaiciuokle extends Skaiciuokle{

        LinijineSkaiciuokle(LineChart<Number, Number> diagrama, TableView<MokejimoDuom> mokejimoLent, int metai, int menesiai, double suma, double proc, String grafikas) {
            super(diagrama, mokejimoLent, metai, menesiai, suma, proc, grafikas);
        }

        @Override
        protected void skaiciuoti() {

            super.init();

            for (int i = 0; i < menesiaiMoket; ++i) {
                palukanos = suma * (this.proc / 100) / 12;
                suma -= kreditas;

                MokejimoDuom duom = new MokejimoDuom((YearMonth.now().plusMonths(i + 1).toString()),(double)Math.round((kreditas + palukanos) * 100) / 100,(double)Math.round(kreditas * 100) / 100, (double)Math.round(palukanos * 100) / 100, (double)Math.round(this.suma * 100) / 100);
                this.mokejimoLent.getItems().add(duom);
                series.getData().add(new XYChart.Data<>((double) YearMonth.now().plusMonths(i + 1).getYear() + ((double)(YearMonth.now().plusMonths(i + 1).getMonthValue())) / 12.1,(double)Math.round((kreditas + palukanos) * 100) / 100));

            }
            diagrama.getData().add(series);
            diagrama.layout();
        }

        @Override
        protected void skaiciuotiAtidejim(double atidProc, int  nuoMen, int pliusMen, double suma) {
            diagrama.getData().clear();
            this.suma = suma;

            //menesiaiMoket = ((this.metai - Year.now().getValue()) * 12) +  this.menesiai - YearMonth.now().getMonthValue();

            xAxis.setUpperBound(metai + nuoMen /12);

            menesiaiMoket = ((metai - Year.now().getValue()) * 12) +  menesiai - YearMonth.now().getMonthValue() ;
            kreditas = this.suma / menesiaiMoket;

            mokejimoLent.getItems().remove(nuoMen, menesiaiMoket);
            series.getData().remove(nuoMen, menesiaiMoket);


            for (int i = 0; i < menesiaiMoket; ++i) {
                if (i >= nuoMen) {
                    palukanos = this.suma * ((proc + atidProc) / 100) / 12;
                    this.suma -= kreditas;

                    MokejimoDuom duom = new MokejimoDuom((YearMonth.now().plusMonths(i + 1 + pliusMen).toString()),(double)Math.round((kreditas + palukanos) * 100) / 100,(double)Math.round(kreditas * 100) / 100, (double)Math.round(palukanos * 100) / 100, (double)Math.round(this.suma * 100) / 100);
                    mokejimoLent.getItems().add(duom);
                    series.getData().add(new XYChart.Data<>((double) YearMonth.now().plusMonths(i + 1 + pliusMen).getYear() + ((double)(YearMonth.now().plusMonths(i + 1 + pliusMen).getMonthValue())) / 12.1,(double)Math.round((kreditas + palukanos) * 100) / 100));
                }
                else {
                    palukanos = this.suma * (this.proc / 100) / 12;
                    this.suma -= kreditas;
                }
            }
            diagrama.getData().add(series);
            diagrama.layout();
        }
    }

    static class AnuitetoSkaiciuokle extends Skaiciuokle{
        AnuitetoSkaiciuokle(LineChart<Number, Number> diagrama, TableView<MokejimoDuom> mokejimoLent, int metai, int menesiai, double suma, double proc, String grafikas) {
            super(diagrama, mokejimoLent, metai, menesiai, suma, proc, grafikas);
        }

        @Override
        protected void skaiciuoti() {
            super.init();

            palukanos = (this.proc / 100) / 12;
            double bendrSum = suma * ((palukanos * Math.pow((1 + palukanos),menesiaiMoket)) / (Math.pow((1 + palukanos),menesiaiMoket) - 1));
            double likutis = suma;

            for (int i = 0; i < menesiaiMoket; ++i) {

                double menPal = likutis * palukanos;
                double bePalSum = bendrSum - menPal;
                likutis -= bePalSum;

                MokejimoDuom duom = new MokejimoDuom((YearMonth.now().plusMonths(i + 1).toString()), (double) Math.round(bendrSum * 100) / 100,(double)Math.round(bePalSum * 100) / 100, (double)Math.round(menPal * 100) / 100, (double)Math.round(likutis * 100) / 100);
                mokejimoLent.getItems().add(duom);
                series.getData().add(new XYChart.Data<>((double) YearMonth.now().plusMonths(i + 1).getYear() +((double)(YearMonth.now().plusMonths(i + 1).getMonthValue())) / 12.1, bendrSum));

            }
            diagrama.getData().add(series);
            diagrama.layout();

        }

        @Override
        protected void skaiciuotiAtidejim(double atidProc, int nuoMen, int pliusMen, double suma) {
            diagrama.getData().clear();
            this.suma = suma;

            menesiaiMoket = ((metai - Year.now().getValue()) * 12) +  menesiai - YearMonth.now().getMonthValue();

            xAxis.setUpperBound(metai + nuoMen /12);

            mokejimoLent.getItems().remove(nuoMen, menesiaiMoket);
            series.getData().remove(nuoMen, menesiaiMoket);
            //diagrama.getData().remove(nuoData, menesiaiMoket);

            palukanos = (proc / 100) / 12;
            double bendrSum = this.suma * ((palukanos * Math.pow((1 + palukanos),menesiaiMoket)) / (Math.pow((1 + palukanos),menesiaiMoket) - 1));
            double likutis = this.suma;

            for (int i = 0; i < menesiaiMoket; ++i) {
                if (i == nuoMen) {
                    palukanos = ((proc + atidProc) / 100) / 12;
                    bendrSum = likutis * ((palukanos * Math.pow((1 + palukanos),menesiaiMoket - i)) / (Math.pow((1 + palukanos),menesiaiMoket - i) - 1));

                    //likutis = likutis * atidProc / 100;

                    double menPal = likutis * palukanos;
                    double bePalSum = bendrSum - menPal;
                    likutis -= bePalSum;

                    MokejimoDuom duom = new MokejimoDuom((YearMonth.now().plusMonths(i + 1 + pliusMen).toString()), (double) Math.round(bendrSum * 100) / 100,(double)Math.round(bePalSum * 100) / 100, (double)Math.round(menPal * 100) / 100, (double)Math.round(likutis * 100) / 100);
                    mokejimoLent.getItems().add(duom);
                    series.getData().add(new XYChart.Data<>((double) YearMonth.now().plusMonths(i + 1 + pliusMen).getYear() +((double)(YearMonth.now().plusMonths(i + 1).getMonthValue())) / 12.1, bendrSum));

                } else if (i > nuoMen) {

                    double menPal = likutis * palukanos;
                    double bePalSum = bendrSum - menPal;
                    likutis -= bePalSum;

                    MokejimoDuom duom = new MokejimoDuom((YearMonth.now().plusMonths(i + 1 + pliusMen).toString()), (double) Math.round(bendrSum * 100) / 100,(double)Math.round(bePalSum * 100) / 100, (double)Math.round(menPal * 100) / 100, (double)Math.round(likutis * 100) / 100);
                    mokejimoLent.getItems().add(duom);

                    series.getData().add(new XYChart.Data<>((double) YearMonth.now().plusMonths(i + 1 + pliusMen).getYear() +((double)(YearMonth.now().plusMonths(i + 1).getMonthValue())) / 12.1, bendrSum));

                } else {
                    double menPal = likutis * palukanos;
                    double bePalSum = bendrSum - menPal;
                    likutis -= bePalSum;
                }
            }
            diagrama.getData().add(series);
            diagrama.layout();
        }
    }
}
