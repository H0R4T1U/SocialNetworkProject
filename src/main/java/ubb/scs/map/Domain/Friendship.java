package ubb.scs.map.Domain;


import java.time.LocalDate;

public class Friendship extends Entity<Tuple<Long,Long>> {
    LocalDate date;
    String user1;
    String user2;

    public Friendship() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }
}
