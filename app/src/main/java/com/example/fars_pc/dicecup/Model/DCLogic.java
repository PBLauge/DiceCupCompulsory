package com.example.fars_pc.dicecup.Model;

import java.util.Random;

/**
 * Created by Casper on 16-02-2018.
 */

public class DCLogic implements IDCLogic {
    Random rand = new Random();

    @Override
    public int selectValueBetween1And6() {
        return rand.nextInt(6) + 1;
    }
}
