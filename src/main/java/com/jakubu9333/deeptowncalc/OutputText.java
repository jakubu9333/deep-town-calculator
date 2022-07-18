package com.jakubu9333.deeptowncalc;

/**
 * @author Jakub Uhlarik
 */
public class OutputText {
    private int depth;
    private Resource resource;
    private double amount;


    public OutputText(int depth, Resource resource, double amount) {
        this.depth = depth;
        this.resource = resource;
        this.amount = amount;
    }

    @Override
    public String toString() {


        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("|");
        for (int i = 0; i < 2*(depth-1); i++) {
            stringBuilder.append("-");
        }
        stringBuilder.append(resource+": "+amount);
        return stringBuilder.toString();
    }
}
