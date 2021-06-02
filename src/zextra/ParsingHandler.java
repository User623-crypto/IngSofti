package zextra;


//import com.jfoenix.controls.JFXDatePicker;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ParsingHandler {


    /**
     * Kjo metode do te marre nje tekst dhe do ta kthej ne numer nese eshte e mundur
     * perndryshe do te kthej 0
     * @param text
     * @return
     */
    public static int getNumbersFromText(String text)
    {
        int number;
        try {
            number= Integer.parseInt(text);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }
        if (number<0)return 0;
        return number;

    }

    /**
     * Kjo metode do te marre nje tekst dhe do ta kthej ate ne dobule nese eshte e mundur
     * perndryshe do te kthej 0
     * @param text
     * @return
     */
    public static double getDoubleFromText(String text)
    {
        if (text == null) return 0;
        double number;
        try {
            number= Double.parseDouble(text);
        }
        catch (NumberFormatException e)
        {
            number = 0;
        }
        return number;
    }
    public static StringConverter<LocalDate> konvertoDaten()
    {
        String pattern = "dd/MM/yyyy";
        return new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
    }

//    public static void parseJFXDatapickerFormat(JFXDatePicker ...datePicker)
//    {
//        for (JFXDatePicker date :
//                datePicker) {
//            date.setConverter(new StringConverter<LocalDate>()
//            {
//                private DateTimeFormatter dateTimeFormatter=DateTimeFormatter.ofPattern("dd/MM/yyyy");
//
//                @Override
//                public String toString(LocalDate localDate)
//                {
//                    if(localDate==null)
//                        return "";
//                    return dateTimeFormatter.format(localDate);
//                }
//
//                @Override
//                public LocalDate fromString(String dateString)
//                {
//                    if(dateString==null || dateString.trim().isEmpty())
//                    {
//                        return null;
//                    }
//                    return LocalDate.parse(dateString,dateTimeFormatter);
//                }
//            });
//        }
//    }



}

