package sample;

public class mainPageModelTable {
    int movieid;
    String moviename;
    double movierating;

    public mainPageModelTable(int movieid, String moviename, double movierating) {
        this.movieid = movieid;
        this.moviename = moviename;
        this.movierating = movierating;
    }

    public int getMovieid() {
        return movieid;
    }

    public void setMovieid(int movieid) {
        this.movieid = movieid;
    }

    public String getMoviename() {
        return moviename;
    }

    public void setMoviename(String moviename) {
        this.moviename = moviename;
    }

    public double getMovierating() {
        return movierating;
    }

    public void setMovierating(double movierating) {
        this.movierating = movierating;
    }
}
