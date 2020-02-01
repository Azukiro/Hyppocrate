package com.hyppocrate.components;

import com.hyppocrate.utilities.ThrowableSupplier;
import com.hyppocrate.utilities.Utils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Supplier;


public class SQLUnitTest {

    public static String allTest() throws Exception {
        //faire tout les appels de test et ajouter tout les string entre eux avec un string builder
        String s="";
        s+= Utils.UnitTest(()->SQLManager.getInstance().getString("Voiture","en"),"Test",null);
        return  s;
    }
}

