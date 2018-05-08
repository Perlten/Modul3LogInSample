/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FunctionLayer.calculator;

import FunctionLayer.entities.Customization;
import FunctionLayer.entities.Shed;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adamlass
 */
public class Calculator {

    private Customization cust;
    private Shed shed;

    private List<Product> products;
    private final int rafterWoodLength = 600; //TODO make db
    private final int maxHeight = 480;

    public Calculator(Customization cust) {
        this.products = new ArrayList<>();
        this.cust = cust;
        this.shed = cust.getShed();
    }

    public void calculate() {
        calculateRafter();
        if(shed != null){
          calculateCladding();
        }
    }

    public List<Product> getProducts() {
        return products;
    }

    private void calculateRafter() {
        int amountOfRafters = (int) (cust.getLength() / 50);
        int width = cust.getWidth();
        double remainder = ((double) width) / rafterWoodLength; //TODO make db
        if (remainder % 1 != 0) {
            remainder++;
        }

        int pitstops = (int) (remainder - 1);

        polesAndBeams(pitstops);

        amountOfRafters *= remainder;

        double lengthOfRafters = (width / remainder);

        if (amountOfRafters > 0) {
            products.add(new Product("Rafter", "Used on beam", "pcs", amountOfRafters, lengthOfRafters, 0)); //TODO make db
        }
    }

    private void polesAndBeams(int pitstops) {
        int lanes = 2 + pitstops;
        int placingLength = cust.getLength() - Customization.padding;

        double amountOfBeams = ((double) placingLength) / rafterWoodLength;

        if (amountOfBeams < 1) {
            amountOfBeams = 1;
        }

        if (amountOfBeams % 1 != 0) {
            int lastBeam = placingLength % rafterWoodLength;

            products.add(new Product("Beam", "Used together with the poles to support the rafters. This is the end piece!", "pcs", lanes, lastBeam, 0)); //TODO make db
        }

        int beamsOnLane = (int) amountOfBeams;

        products.add(new Product("Beam", "Used together with the poles to support the rafters.", "pcs", beamsOnLane * lanes, rafterWoodLength, 0)); //TODO make db

        int poles = 1;

        poles += placingLength / (rafterWoodLength / 2);

        if ((placingLength / ((double) (rafterWoodLength)) / 2) % 1 != 0) {
            poles++;
        }

        products.add(new Product("Pole", "Used to support the beams", "pcs", poles * lanes, cust.getHeight(), 0)); //TODO make db
    }

    private void calculateCladding() {
        int circumsference = shed.getLength() + shed.getWidth() * 2;
        int claddingNeeded = (int) Math.ceil(circumsference * 0.125);
        int claddingHeight = cust.getHeight() - 20; //20 cm air from floor
        products.add(new Product("Cladding", "Cladding used og the shed", "pcs", claddingNeeded, claddingHeight, 0)); //TODO makedb
        
    }

    public static void main(String[] args) {
        Shed shed = new Shed(200, 640);
        shed = null;
        Customization cust = new Customization(700, 700, 200, 0, shed, null, null);
        Calculator cal = new Calculator(cust);
        cal.calculate();
        System.out.println(cal.getProducts());
    }

}