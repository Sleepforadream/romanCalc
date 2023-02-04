import java.util.*;

public class Main {

    static String[] romanInputNumbers = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
    static int minArabicInputNumbers = 1;
    static int maxArabicInputNumbers = 10;
    static Exception InvalidInputFormat = new Exception("Формат математической операции не удовлетворяет заданию");
    static Exception InvalidRomanNumber = new Exception("Римское число не может быть меньше 1");

    static boolean checkIsValidArabicNumber(String number) {
        try {
            int intNumber = Integer.parseInt(number);
            return intNumber >= minArabicInputNumbers && intNumber <= maxArabicInputNumbers;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean checkIsValidRomanNumber(String number) {
        for (String romanNumber : romanInputNumbers) {
            if (Objects.equals(number, romanNumber)) {
                return true;
            }
        }
        return false;
    }

    static boolean checkIsSameValidTypes(String first, String second) {
        if (checkIsValidArabicNumber(first) && checkIsValidArabicNumber(second)) {
            return true;
        } else return checkIsValidRomanNumber(first) && checkIsValidRomanNumber(second);
    }

    static String getInput() throws Exception {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] separatedInput;
        separatedInput = input.split(" ");
        String first = separatedInput[0];
        String second = separatedInput[2];
        String operator = separatedInput[1];
        if (separatedInput.length != 3) {
            throw InvalidInputFormat;
        }
        if (!Objects.equals(operator, "-") && !Objects.equals(operator, "+") &&
                !Objects.equals(operator, "*") && !Objects.equals(operator, "/")) {
            throw InvalidInputFormat;
        }
        if (checkIsSameValidTypes(first, second)) {
            return first + " " + operator + " " + second;
        } else {
            throw InvalidInputFormat;
        }
    }

    static int romanToArabic(String number) {
        String[] romansKeys = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int[] arabicKeys = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};

        int result = 0;

        String[] strToArray = number.split("");
        ArrayList<String> listNumber = new ArrayList<>();
        Collections.addAll(listNumber, strToArray);

        if (listNumber.size() >= 2) {
            for (int i = 0; i < listNumber.size() - 1; i++) {
                for (int j = 0; j < romansKeys.length; j++) {
                    if (listNumber.size() >= 2 && Objects.equals(listNumber.get(i) + listNumber.get(i + 1), romansKeys[j])) {
                        result = result + arabicKeys[j];
                        listNumber.remove(i);
                        listNumber.remove(i);
                    }
                }
            }
        }
        for (String s : listNumber) {
            for (int j = 0; j < romansKeys.length; j++) {
                if (Objects.equals(s, romansKeys[j])) {
                    result = result + arabicKeys[j];
                }
            }
        }
        return result;
    }

    static String arabicToRoman(int number) throws Exception {
        if (number <= 0) {
            throw InvalidRomanNumber;
        }
        String[] romansKeys = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        int[] arabicKeys = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < romansKeys.length; i++) {
            while (number >= arabicKeys[i]) {
                int doubles = number / arabicKeys[i];
                number = number % arabicKeys[i];
                result.append(romansKeys[i].repeat(doubles));
            }
        }
        return result.toString();
    }

    public static String calc(String input) throws Exception {
        String[] separatedInput = input.split(" ");
        int first = romanToArabic(separatedInput[0]);
        int second = romanToArabic(separatedInput[2]);
        String results = "";
        if (checkIsValidRomanNumber(separatedInput[0])) {
            if (input.contains("+")) {
                results = arabicToRoman(first + second);
            } else if (input.contains("-")) {
                results = arabicToRoman(first - second);
            } else if (input.contains("/")) {
                results = arabicToRoman(first / second);
            } else if (input.contains("*")) {
                results = arabicToRoman(first * second);
            }
        } else {
            if (input.contains("+")) {
                results = String.valueOf(Integer.parseInt(separatedInput[0]) + Integer.parseInt(separatedInput[2]));
            } else if (input.contains("-")) {
                results = String.valueOf(Integer.parseInt(separatedInput[0]) - Integer.parseInt(separatedInput[2]));
            } else if (input.contains("/")) {
                results = String.valueOf(Integer.parseInt(separatedInput[0]) / Integer.parseInt(separatedInput[2]));
            } else if (input.contains("*")) {
                results = String.valueOf(Integer.parseInt(separatedInput[0]) * Integer.parseInt(separatedInput[2]));
            }
        }
        return results;
    }

    public static void main(String[] args) throws Exception {
        String input = getInput();
        System.out.println(calc(input));
    }
}