/*
 * (C) Copyright 2005 Davide Brugali, Marco Torchiano
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307  USA
 */
package com.github.leertaken.leertaak1.opdracht19.multiformat;

/**
 * The multiformat calculator
 */
public class Calculator {
  private Rational operand_0 = new Rational();
  private Rational operand_1 = new Rational();
  
  // The current format of the calculator
  private Format format = new FixedPointFormat();
  // The current numberbase of the calculator
  private Base base = new DecimalBase();

  public void addOperand(String newOperand) throws FormatException, NumberBaseException{
    newOperand = newOperand.toUpperCase();

    if (base.getDigits().contains(newOperand)) {
      operand_1 = operand_0;
      operand_0 = format.parse(newOperand, base);
      return;
    }

    if (base instanceof DecimalBase) {
      try {
        Double.parseDouble(newOperand);

        operand_1 = operand_0;
        operand_0 = format.parse(newOperand, base);
        return;
      }catch(NumberFormatException e){
        throw new NumberBaseException(base);
      }
    }
    if(base instanceof BinaryBase){
      try{
        //Integer.parseInt(newOperand,2);
        operand_1 = operand_0;
        operand_0 = format.parse(newOperand, base);
        return;
      }
      catch (NumberFormatException e){
        System.out.println("TestGame bin");
        throw new NumberBaseException(base);
      }
    }
    if(base instanceof HexBase){
      try{
        //Integer.parseInt(newOperand,2);
        operand_1 = operand_0;
        operand_0 = format.parse(newOperand, base);
        return;
      }
      catch (NumberFormatException e){
        System.out.println("TestGame Hex");
        throw new NumberBaseException(base);
      }
    }
    if(base instanceof OctalBase){
      try{
        //Integer.parseInt(newOperand,2);
        operand_1 = operand_0;
        operand_0 = format.parse(newOperand, base);
        return;
      }
      catch (NumberFormatException e){
        System.out.println("TestGame oct");
        throw new NumberBaseException(base);
      }
    }
    throw new NumberBaseException(base);
  }

  public void add(){
    operand_0 = operand_1.plus(operand_0);
    operand_1 = new Rational();
  }
  public void subtract() {
    operand_0 = operand_1.minus(operand_0);
    operand_1 = new Rational();
  }
  public void multiply() {
    //try {
      /*if (((     format.parse("0", base).equals(operand_0.getNumerator())
              &&format.parse("0", base).equals(operand_0.getDenominator()))
              ||(format.parse("0", base).equals(operand_1.getNumerator())
              &&format.parse("0", base).equals(operand_1.getDenominator())))) {*/
        operand_0 = operand_1.mul(operand_0);
        operand_1 = new Rational();
        System.out.println("One 0");
      /*} else {
        operand_0.setNumerator(0);
        operand_0.setDenominator(0);
        operand_1 = new Rational();
        System.out.println("No 0");
      }*/
    //}
    /*catch (FormatException e){

    }*/
  }
  public void divide() {
    //if((operand_0.getNumerator()!=0&&operand_0.getDenominator()!=0)||(operand_1.getNumerator()!=0&&operand_1.getDenominator()!=0)){
    try {
      if (((format.parse("0", base).equals(operand_0.toString())
              && format.parse("0", base).equals(operand_0.getDenominator()))
              || (format.parse("0", base).equals(operand_1.toString())
              && format.parse("0", base).equals(operand_1.getDenominator())))) {
        throw new IllegalArgumentException("Can not devide by zero!");
      }
    }

  catch(FormatException e){}
    operand_0 = operand_1.div(operand_0);
    operand_1 = new Rational();
  }
  public void delete() {
    operand_0 = operand_1;
    operand_1 = new Rational();
  }

  public String firstOperand(){
    return format.toString(operand_1,base);
  }
  public String secondOperand(){
    return format.toString(operand_0,base);
  }

  public void setBase(Base newBase){
    base = newBase;
  }
  public Base getBase(){
    return base;
  }
  public void setFormat(Format newFormat){
    format = newFormat;
  }
  public Format getFormat(){
    return format;
  }
}