package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;


public class ChangeDate {
    private LocalDateTime localDateTime;

    public ChangeDate(String str, int type) {
        if(type==1) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==2){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==3){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yy/MM/dd HH:mm");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==4){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==5){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (HH:mm:ss)");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==6){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd  HH:mm");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==7){
            int minus=0;
            LocalDateTime now= LocalDateTime.now().withNano(0);
            if(str.contains("분 전")){
                minus=Integer.parseInt(str.replaceAll("[^\\d]", ""));
                this.localDateTime=now.minusMinutes(minus);
            }
            else if(str.contains("시간 전")){
                minus=Integer.parseInt(str.replaceAll("[^\\d]", ""));
                this.localDateTime=now.minusHours(minus);
            }
            else if(str.contains("일 전")){
                minus=Integer.parseInt(str.replaceAll("[^\\d]", ""));
                this.localDateTime=now.minusDays(minus);
            }
            else{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate date=LocalDate.parse(str,formatter);
                this.localDateTime = date.atStartOfDay();
            }
        }
        else if(type==8){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            this.localDateTime = LocalDateTime.parse(str, formatter);
        }
        else if(type==9){
            Date date = new Date();
            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");
            String today = transFormat.format(date);
            if(str.contains(":")){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.ddHH:mm");
                this.localDateTime = LocalDateTime.parse(today+str, formatter);
            }
            else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate date2=LocalDate.parse(str,formatter);
                this.localDateTime = date2.atStartOfDay();
            }
        }
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}