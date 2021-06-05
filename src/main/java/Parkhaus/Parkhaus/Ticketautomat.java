package Parkhaus.Parkhaus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Class for issues parking tickets, management is up to the carpark
 */
public class Ticketautomat {
    /**
     * The price to park for one hour
     */
    float priceHour;
    /**
     * Datatable for tickets that have not been used
     */
    final HashMap<String, Ticket> tickets;

    /**
     * Default constructor
     */
    public Ticketautomat() {
        this.tickets = new HashMap<String, Ticket>();
    }

    /**
     * Constructor with the price for every hour
     * @param priceHour The price the customer pays for every hour
     */
    public Ticketautomat(float priceHour) {
        this.tickets = new HashMap<String, Ticket>();
        this.priceHour = priceHour;
    }

    /**
     * Getter
     * @return The price for every hour stayed
     */
    public float getPrice() {
        return this.priceHour;
    }

    /**
     * Attempts ti find a ticket by its id
     * @return The ticket or null if it does not exist or has already been used
     */
    public Ticket getTicketById(String id) {
        return tickets.get(id);
    }

    /**
     * Calculates the price based on the amount of hours
     * @param entered The first and lesser date
     * @param now The second and latter date
     */
    public float calculatePrice(Calendar entered, Calendar now) {
        long delta = Ticketautomat.getHourDelta(entered, now);

        return delta * this.priceHour;
    }

    /**
     * Calculates the delta of hours between two dates
     * @param past The first and lesser date
     * @param present The second and latter date
     */
    public static long getHourDelta(Calendar past, Calendar present) {
        LocalDate d1 = LocalDate.ofInstant(past.toInstant(), ZoneId.systemDefault());
        LocalDate d2 = LocalDate.ofInstant(present.toInstant(), ZoneId.systemDefault());

        return ChronoUnit.HOURS.between(d1, d2);
    }

    /**
     * Generates a new ticket
     * @return The new ticket
     */
    public Ticket generateTicket(String id) {
        return generateTicket(id, Calendar.getInstance());
    }

    /**
     * Simulates putting a ticket into the Ticket machine
     * @param date The custom time that the ticket was issued
     * @return The new ticket
     */
    public Ticket generateTicket(String id, Calendar date) {
        Ticket ticket = new Ticket(id, date);

        tickets.put(id, ticket);

        return ticket;
    }

    /**
     * Simulates putting a ticket into the Ticket machine
     * @param ticket The ticket to use
     * @return The price of the stay
     */
    public float useTicket(Ticket ticket) {
        float price = this.calculatePrice(ticket.getDate(), Calendar.getInstance());

        ticket.Use();
        tickets.remove(ticket.getId(), ticket);

        return price;
    }
}

/**
 * Internal class to represent a ticket issued by a ticket machine in a carpark
 */
class Ticket {
    final Calendar date;
    final String id;
    boolean used = false;

    /**
     * Creates a ticket with a custom time for testing purposes
     * @param id Unique identifier
     * @param date The custom time that the ticket was issued
     */
    public Ticket(String id, Calendar date) {
        this.date = date;
        this.id = id;
    }

    /**
     * Getter
     * @return Id
     */
    public String getId() {
        return this.id;
    }

    /**
     * Getter
     * @return Calendar date issued
     */
    public Calendar getDate() {
        return this.date;
    }

    /**
     * Returns if the ticket has been used or not
     * @return If the ticket has been used or not
     */
    public boolean isUsed() {
        return this.used;
    }

    /**
     * Simulates the use of the ticket
     */
    public void Use() {
        if (this.used)
            return;

        this.used = true;
    }
}