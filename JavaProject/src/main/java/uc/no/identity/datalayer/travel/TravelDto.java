package uc.no.identity.datalayer.travel;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
public class TravelDto {

    public TravelDto() {}

    private String id;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String userId;
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private DestinationDto airline;

    public DestinationDto getAirline() {
        return airline;
    }

    public void setAirline(DestinationDto airline) {
        this.airline = airline;
    }

    private DestinationDto from;
    
    public DestinationDto getFrom() {
        return from;
    }

    public void setFrom(DestinationDto from) {
        this.from = from;
    }

    private DestinationDto to;

    public DestinationDto getTo() {
        return to;
    }

    public void setTo(DestinationDto to) {
        this.to = to;
    }

    private String seat;
    
    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
