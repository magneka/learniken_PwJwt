package uc.no.identity.datalayer.travel;

public class DestinationDto {
    
    public DestinationDto() {
    }
    
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    private String iataCode;  
    public String getIataCode() {
        return iataCode;
    }
    public void setIataCode(String iataCode) {
        this.iataCode = iataCode;
    }
}
