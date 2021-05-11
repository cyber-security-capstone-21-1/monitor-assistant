package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChangeDate {
    private String str;
    private SimpleDateFormat dateFormat;

    public ChangeDate(String str) {
        this.str = str;
        SimpleDateFormat dateParser = new SimpleDateFormat("MM/dd/yy HH:mm");
        {
            try {
                Date date = dateParser.parse(str);
                System.out.println(date);
                SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yy");
                System.out.println(dateFormatter.format(date));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }
}