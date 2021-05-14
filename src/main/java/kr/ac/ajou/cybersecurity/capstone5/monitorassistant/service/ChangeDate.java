package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd (E) HH:mm");
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
                this.localDateTime=now.minusMinutes(minus);
            }
            else if(str.contains("일 전")){
                minus=Integer.parseInt(str.replaceAll("[^\\d]", ""));
                this.localDateTime=now.minusMinutes(minus);
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
    }
    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}