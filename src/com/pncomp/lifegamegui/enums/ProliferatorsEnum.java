package com.pncomp.lifegamegui.enums;

import com.pncomp.lifegame.proliferators.DefaultAdjacentFieldProliferator;
import com.pncomp.lifegame.proliferators.PairProliferator;
import com.pncomp.lifegame.proliferators.Proliferator;

public enum ProliferatorsEnum {

    DEFAULT_PROLIFERATOR("Simple Default"), PAIR_PROLIFERATOR("Simple Pair");

    public String getDescription() {
        return description;
    }

    final String description;

    ProliferatorsEnum( String description){
        this.description=description;
    }

    public static class Factory {

        public static Proliferator createProliferator(ProliferatorsEnum proliferatorsEnum){
            switch (proliferatorsEnum){
                case DEFAULT_PROLIFERATOR:
                    return new DefaultAdjacentFieldProliferator();
                case PAIR_PROLIFERATOR:
                    return new PairProliferator();
                    default:
                        return null;

            }
        }
    }



}
