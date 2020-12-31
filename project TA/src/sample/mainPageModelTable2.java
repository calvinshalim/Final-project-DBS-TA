package sample;

public class mainPageModelTable2 {
    String descUsername;
    String descDescription;
    double descRating;

    public mainPageModelTable2(String descUsername, String descDescription, double descRating) {
        this.descUsername = descUsername;
        this.descDescription = descDescription;
        this.descRating = descRating;
    }

    public String getDescUsername() {
        return descUsername;
    }

    public void setDescUsername(String descUsername) {
        this.descUsername = descUsername;
    }

    public String getDescDescription() {
        return descDescription;
    }

    public void setDescDescription(String descDescription) {
        this.descDescription = descDescription;
    }

    public double getDescRating() {
        return descRating;
    }

    public void setDescRating(double descRating) {
        this.descRating = descRating;
    }
}
