package help.pawanchouhan.orangeportal;

public class feedback_adapter {

    String username;
    String useremail;
    String userthoughts;


    public feedback_adapter(String username, String useremail, String userthoughts) {
        this.username = username;
        this.useremail = useremail;
        this.userthoughts = userthoughts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUserthoughts() {
        return userthoughts;
    }

    public void setUserthoughts(String userthoughts) {
        this.userthoughts = userthoughts;
    }
}
