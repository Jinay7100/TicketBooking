package org.example.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.sql.Time;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonIgnoreProperties(ignoreUnknown = true)

public class Train {
    private String trainId;
    private String trainNo;
    private List<List<Integer>> trainSeats;
    private Map<String, String> stationTimes;
    private List<String> stations;
    public Train (String trainId, String trainNo, List<List<Integer>> trainSeats, Map<String, String> stationTimes, List<String> stations
    )
    {
        this.trainId = trainId;
        this.trainNo = trainNo;
        this.trainSeats = trainSeats;
        this.stationTimes = stationTimes;
        this.stations = stations;
    };

    public Train(){};

    public List<String> getStations(){
        return stations;
    }
    public List<List<Integer>> getSeats(){
        return trainSeats;
    }
    public void  setSeats(List<List<Integer>> seats){
        this.trainSeats = seats;
    }
    public String getTrainId(){
        return trainId;
    }
    public Map<String, String> getStationTimes(){
        return stationTimes;
    }
    public String getTrainNo(){
        return trainNo;
    }
    public void setTrainNo(String trainNo){
        this.trainNo = trainNo;
    }
    public void setTrainId(String trainId){
        this.trainId = trainId;
    }
    public void setStationTime(Map<String, String> stationTimes){
        this.stationTimes = stationTimes;
    }
    public  String getTrainInfo(){
        return String.format("Train ID: %s, Train No: %s", trainId, trainNo);
    }

    public Map<String, String> getStationTime() {
    return stationTimes;
    }
}
