import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){

        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if(input.length() <= 2){
            try {
                throw new IOException();
            } catch (IOException e){
                System.err.println("Cтрока не является математической операцией");
                System.exit(1);
            }
        }
        System.out.println(calc(input));
    }
    public static String calc(String input){
        String [] arrayFromInput = inputInArray(input);
        String finalOutput = choiceArabicOrRome(arrayFromInput);
        return finalOutput;
    }
    static String [] inputInArray(String input) {
        String[] operation = new String[]{"+", "-", "*", "/", "="};
        for (int i = 0; i < operation.length; i++) {
            if (input.contains(operation[i])) {
                String operationWithSpaces = " " + operation[i] + " ";
                input = input.replace(operation[i], operationWithSpaces);
            }
        }
        input = input.trim();
        String[] inputs = input.split("\\s+");
        if(inputs.length < 2) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("Значение не является математической операцией определяемой в условии задачи (+, -, /, *)");
                System.exit(1);
            }
        }
        if (inputs.length > 3) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("Формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
                System.exit(1);
            }
        }
        String correctOperation = String.valueOf(inputs[1]);
        String operationInString = String.join("", operation);
        if (!operationInString.contains(correctOperation)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("Значение не является математической операцией определяемой в условии задачи (+, -, /, *)");
                System.exit(1);
            }
        }
        return inputs;
    }
    static String choiceArabicOrRome(String [] arrayFromInput){
        Rome arrayRomeConstants[] = Rome.values();
        String [] arrayRomeValueFromEnum = new String[arrayRomeConstants.length];
        String [] arrayArabicValueFromEnum = new String[arrayRomeConstants.length];
        for(int i = 0; i < arrayRomeValueFromEnum.length; i++){
            arrayRomeValueFromEnum[i] = arrayRomeConstants[i].romeValue;
            arrayArabicValueFromEnum[i] = String.valueOf(arrayRomeConstants[i].arabicValue);
        }
        String numberOneFromScanner = String.valueOf(arrayFromInput[0]);
        String numberTwoFromScanner = String.valueOf(arrayFromInput[2]);
        String stringRomeNumbers = String.join(" ", arrayRomeValueFromEnum);
        String stringArabicNumbers = String.join(" ", arrayArabicValueFromEnum);
        int indexOfOneNumber = stringRomeNumbers.lastIndexOf(numberOneFromScanner);
        int indexOfTwoNumber = stringRomeNumbers.lastIndexOf(numberTwoFromScanner);
        if (stringArabicNumbers.contains(numberOneFromScanner) && stringRomeNumbers.contains(numberTwoFromScanner)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("Используются одновременно разные системы счисления");
                System.exit(1);
            }
        }
        if (stringRomeNumbers.contains(numberOneFromScanner) && stringArabicNumbers.contains(numberTwoFromScanner)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("Используются одновременно разные системы счисления");
                System.exit(1);
            }
        }
        if (stringArabicNumbers.contains(numberOneFromScanner) && !stringArabicNumbers.contains(numberTwoFromScanner)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("В калькуляторе можно использовать арабские цифры от 1 до 10");
                System.exit(1);
            }
        }
        if (!stringArabicNumbers.contains(numberOneFromScanner) && stringArabicNumbers.contains(numberTwoFromScanner)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("В калькуляторе можно использовать арабские цифры от 1 до 10");
                System.exit(1);
            }
        }
        if (stringRomeNumbers.contains(numberOneFromScanner) && !stringRomeNumbers.contains(numberTwoFromScanner)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("В калькуляторе можно использовать римские цифры от I до X");
                System.exit(1);
            }
        }
        if (!stringRomeNumbers.contains(numberOneFromScanner) && stringRomeNumbers.contains(numberTwoFromScanner)) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("В калькуляторе можно использовать римские цифры от I до X");
                System.exit(1);
            }
        }
        Rome romeNumberOne = Rome.O;
        Rome romeNumberTwo = Rome.O;
        if (indexOfOneNumber == -1 && indexOfTwoNumber == -1) {
            int numberOne = 0;
            int numberTwo = 0;
            if(stringArabicNumbers.contains(numberOneFromScanner) && stringArabicNumbers.contains(numberTwoFromScanner)){
                numberOne = Integer.parseInt(arrayFromInput[0]);            //Преобразуем строку в число
                numberTwo = Integer.parseInt(arrayFromInput[2]);
            }
            int result = resultOfOperation((arrayFromInput[1]), numberOne, numberTwo);
            return String.valueOf(result);                                //выводим результат
        }
        else {
            if (stringRomeNumbers.contains(numberOneFromScanner) && stringRomeNumbers.contains(numberTwoFromScanner)) {
                romeNumberOne = Rome.valueOf(arrayFromInput[0]);
                romeNumberTwo = Rome.valueOf(arrayFromInput[2]);
            }
        }
        int result = resultOfOperation((arrayFromInput[1]), romeNumberOne.arabicValue, romeNumberTwo.arabicValue);
        return finalOutputOfRome(result, arrayRomeValueFromEnum);
    }
    static int resultOfOperation(String operation, int numberOne, int numberTwo){
        int result = 0;
        if (numberOne == 0 || numberTwo == 0) {
            try {
                throw new IOException();
            } catch (IOException e) {
                System.err.println("Недопустимое выражение для расчета");
                System.exit(1);
            }
        }
        switch (operation){
            case "+":
                result = numberOne + numberTwo;
                break;
            case "-":
                result = numberOne - numberTwo;
                break;
            case "*":
                result = numberOne * numberTwo;
                break;
            case "/":
                result = numberOne / numberTwo;
                break;
        }
        return result;
    }
    static String finalOutputOfRome(int result, String [] arrayRomeFromEnum){
        ResultOfRome FinalResult = new ResultOfRome();
        String romeStringFinal;
        int numberOfTen = FinalResult.numberOfTen(result);
        int remainder = FinalResult.remainder(result);
        if(result < 10){
            romeStringFinal = String.valueOf(arrayRomeFromEnum[result]);
        } else if (result < 39) {
            String [] arrayNumbersOfTen = new String[numberOfTen + 1];
            for (int i = 0; i <= numberOfTen - 1; i++) {
                arrayNumbersOfTen[i] = arrayRomeFromEnum[10];
            }
            arrayNumbersOfTen[numberOfTen] = arrayRomeFromEnum[remainder];
            romeStringFinal = String.join("", arrayNumbersOfTen);
        } else if (result > 39 && result < 50) {
            romeStringFinal = "XL" + arrayRomeFromEnum[remainder];
        } else if (result > 49 && result < 90) {
            String [] numberArray = new String[numberOfTen - 3];
            numberArray[0] = "L";
            for (int i = 1; i <= numberOfTen - 5; i++) {
                numberArray[i] = arrayRomeFromEnum[10];
            }
            numberArray[numberOfTen - 4] = arrayRomeFromEnum[remainder];
            romeStringFinal = String.join("", numberArray);
        } else if (result > 89 && result < 100) {
            romeStringFinal = "XC" + arrayRomeFromEnum[remainder];
        } else {
            romeStringFinal = "C";
        }
        return romeStringFinal;
    }
}
class ResultOfRome {
    int numberOfTen(int result){
        return result / 10;
    }
    int remainder(int result){
        if (result % 10 == 0){
            return 0;
        } else {
            return result % 10;
        }
    }
}
enum Rome{
    O("", 0), I("I", 1), II("II", 2), III("III", 3), IV("IV", 4), V("V", 5), VI("VI", 6), VII("VII", 7), VIII("VIII", 8), IX("IX", 9), X("X", 10), L("L", 50), C("C", 100);
    String romeValue;
    int arabicValue;
    Rome(String romeValue, int arabicValue){
        this.romeValue = romeValue;
        this.arabicValue = arabicValue;
    }
}