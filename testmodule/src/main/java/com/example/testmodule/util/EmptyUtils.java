package com.example.testmodule.util;

import java.util.List;

public   class EmptyUtils {


     public static boolean isNotEmpty(List<String> codes) {
         if(codes==null||codes.size()==0){
             return false;
         }else {
             return true;
         }

     }


    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0||s.equals("null");
    }

 }
