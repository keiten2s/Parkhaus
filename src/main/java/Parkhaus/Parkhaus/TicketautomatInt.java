package Parkhaus.Parkhaus;

import java.util.Calendar;

public interface TicketautomatInt {

    public void Ticketautomat(float priceHour);
    public float getPrice();
    public float calculatePrice(Calendar entered, Calendar now);
    public long getHourDelta(Calendar past, Calendar present);
    public Ticket generateTicket(String id);
    public Ticket generateTicket(String id, Calendar date);
    public float useTicket(Ticket ticket);
}
