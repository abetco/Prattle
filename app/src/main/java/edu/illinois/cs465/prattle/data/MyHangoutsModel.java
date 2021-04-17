package edu.illinois.cs465.prattle.data;

public class MyHangoutsModel {
    // To be changed
    String name;
    String date;
    String location;
    String participants;
     public MyHangoutsModel(String name, String date, String location, String participants) {
         this.name = name;
         this.date = date;
         this.location = location;
         this.participants = participants;
     }

     public String getName() {
         return name;
     }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getParticipants() {
        return participants;
    }
}
