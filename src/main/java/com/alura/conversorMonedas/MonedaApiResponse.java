package com.alura.conversorMonedas;

import java.util.Map;

public class MonedaApiResponse {
    public String result;
    public String documentation;
    public String terms_of_use;
    public long time_last_update_unix;
    public String time_last_update_utc;
    public long time_next_update_unix;
    public String time_next_update_utc;
    public String base_code;

    public Map<String, Double> conversion_rates;

    @Override
    public String toString() {
        return "MonedaApiResponse{" +
                "result='" + result + '\'' +
                ", base_code='" + base_code + '\'' +
                ", conversion_rates=" + conversion_rates +
                '}';
    }
}
