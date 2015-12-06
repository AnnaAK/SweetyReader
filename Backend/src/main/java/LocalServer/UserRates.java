package main.java.LocalServer;

import java.util.HashMap;

public class UserRates{
    private Long id = new Long(0);
    public Long getUserId (){return id;}
    public void setUserId (Long newId) {id = newId;}

    private HashMap<Long, Double> rates = new HashMap<Long, Double>();
    public HashMap<Long, Double> getRates (){return rates;}
    public void setRates (HashMap<Long, Double> newRates) {rates = newRates;}
}