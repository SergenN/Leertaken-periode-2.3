package com.github.leertaken.leertaak3.opdracht7;

public class Best {
    int row;
    int column;
    int value;

    public Best(int value) {
        this(value, 0, 0);
    }

    public Best(int value, int row, int column) {
        this.value = value;
        this.row = row;
        this.column = column;
    }
}