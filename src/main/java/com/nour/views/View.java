package com.nour.views;

import java.util.List;

public interface View {
    int choixSousMenu(String menuTxt, int valueSup);

    String obligatoire(String menuTxt, String errorTxt);

    <T> void showListElements(List<T> list, String entity);
}
