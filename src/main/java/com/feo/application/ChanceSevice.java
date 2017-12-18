package com.feo.application;

import java.text.ParseException;

public interface ChanceSevice {

     /**
      * 老天网录音对接
      * @throws ParseException
      */
     public void speechChanceDataPersistence() throws ParseException;

     /**
      * 新天网录音对接
      * @throws ParseException
      */
     public void newSpeechChanceDataPersistence()throws ParseException;

     /**
      * 全量录音对接
      * @throws ParseException
      */
     public void allChanceDataPersistence()throws ParseException;
}
