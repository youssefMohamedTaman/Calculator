package com.alexu.calculator.ui.model;

import org.springframework.lang.NonNull;

public class Operation {

    private int result ;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public static double makeOperation(double operand1, double operand2, char operator) throws ArithmeticException {
        double result =  0 ;
        switch (operator) {
            case '+' :
                result =operand1 + operand2;
                break ;
            case '-' :
                result =operand1 - operand2;
                break ;
            case '*' :
                result =operand1 * operand2;
                break ;
            case '/':
                if (operand2 == 0) {
                    throw new ArithmeticException() ;
                }
                result = operand1 /operand2 ;
                break ;
        }
        return (result);
    }

    public static double makeSinglyOperation(double operand1, String operator) throws ArithmeticException{
        double result =0  ;
        switch (operator) {
            case "root" :
                if (operand1 < 0) {
                    throw new ArithmeticException();
                }
                result = Math.sqrt(operand1);
                break;
            case "square" :
                result = Math.pow(operand1, 2);
                break ;
            case "inverse" :
                if (operand1 == 0) {
                throw new ArithmeticException();
            }
                result = Math.pow(operand1, -1) ;
                break ;
            case "mod" :
                result = operand1 / 100 ;
        }
        return (result);
    }




}
