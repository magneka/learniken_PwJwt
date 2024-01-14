public class TravelDTO {
    public int Id { get; set; }
    public string UserId { get; set; } = "";
    public Destination? Airline { get; set; }
    public Destination? From { get; set; }
    public Destination? To { get; set; }
    public string Seat { get; set; } = "";   
    public DateTime Date { get; set; }
}

public class Destination {
    public string Name { get; set; } = "";
    public string IataCode { get; set; } = "";
}
