package org.talos.telbotbank.model;

import lombok.Data;

import java.util.Date;

@Data
public class CurrencyModel {
    Integer cur_ID;
    Date date;
    String cur_Abbr;
    Integer cur_Scale;
    String cur_Name;
    Double cur_OfficialRate;
}
