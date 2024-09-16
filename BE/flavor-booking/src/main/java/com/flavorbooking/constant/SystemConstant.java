package com.flavorbooking.constant;

import java.util.List;

public class SystemConstant {

    public static final String EXSITS_IN_DB = "đã tồn tại trong hệ thống";
    public static final String RES_DEFAULT = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQFLhgQhBcjv7P6Zk2Mwgarb1dPx7XcmHId_Ya45tzRjqGU15NEjvfLXaX5gbGV_zq3jB8&usqp=CAU";

    public static double convertTime(String time){
        int h = Integer.parseInt(time.substring(0,2));
        int s = Integer.parseInt(time.substring(3,5));
        double timeFormat = (h*100+s)*1.0/100;
        return timeFormat;
    }

    public static boolean checkTime(double a, double b, List<Integer> time, List<Double> clock){
        for(int i=0; i< time.size(); i++){
            if(a>b) {
                System.out.println("sai1");
                return false;
            }
            if(a<clock.get(0) || b>clock.get(clock.size()-1)) {
                System.out.println("sai2");
                return false;
            }
            if(a<clock.get(i) && clock.get(i)<=b){
                if(time.get(i) == 1) {
                    System.out.println("sai3");
                    return false;
                }
            }
        }
        return true;
    }
}
