package com.hyppocrate.components;

import java.util.ArrayList;

public class TestParameters {
        public ArrayList<Object> parameters = new ArrayList<>();
        public ArrayList<Exception> exceptions = new ArrayList<>();

        public TestParameters(ArrayList<Object> parameters, ArrayList<Exception> exceptions) {
            this.parameters = parameters;
            this.exceptions = exceptions;
        }



}
