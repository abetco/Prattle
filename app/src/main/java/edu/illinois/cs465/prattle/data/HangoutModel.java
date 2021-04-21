package edu.illinois.cs465.prattle.data;

public class HangoutModel {
    private String title;
    private String date;
    private String location;
    private String description;
    private String[] participants;
    private int minParticipants;
    private int maxParticipants;

     public HangoutModel(String title, String date, String location, String description,
                         String[] participants, int minParticipants, int maxParticipants) {
         this.title = title;
         this.date = date;
         this.location = location;
         this.description = description;
         this.participants = participants;
         this.minParticipants = minParticipants;
         this.maxParticipants = maxParticipants;
     }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String[] getParticipants() {
        return participants;
    }

    public int getMinParticipants() {
        return minParticipants;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }
}
