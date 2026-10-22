package org.skaiciuokle.paskolu_skaiciuokle;

    public class MokejimoDuom {
        private String data;
        private double bendrSum;
        private double paskSum;
        private double palSum;
        private double likSum;

        MokejimoDuom(String data, double bendrSum, double paskSum, double palSum, double likSum) {
            this.data = data;
            this.bendrSum = bendrSum;
            this.paskSum = paskSum;
            this.palSum = palSum;
            this.likSum = likSum;
        }

        public String getData() {
            return data;
        }
        public double getBendrSum() {
            return bendrSum;
        }
        public double getPaskSum() {
            return paskSum;
        }
        public double getPalSum() {
            return palSum;
        }
        public double getLikSum() {
            return likSum;
        }
    }
