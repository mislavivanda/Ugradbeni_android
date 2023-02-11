package com.example.projekt;

public class PrisutnostView {
        // the resource ID for the imageView
        private String cname;

        // TextView 1
        private int cscore;

        // TextView 1
        private int ctotal;

        // create constructor to set the values for all the parameters of the each single view
        public PrisutnostView(String name, int score, int total) {
            cname = name;
            cscore = score;
            ctotal = total;
        }

        // getter method for returning the ID of the imageview
        public String getName() {
            return cname;
        }

        // getter method for returning the ID of the TextView 1
        public int getScore() {
            return cscore;
        }

        // getter method for returning the ID of the TextView 2
        public int getTotal() {
            return ctotal;
        }

}
