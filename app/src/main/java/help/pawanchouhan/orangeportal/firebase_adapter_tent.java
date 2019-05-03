package help.pawanchouhan.orangeportal;

public class firebase_adapter_tent {

    private String shopname;
    private String address;
    private String FirstName;
    private String LastName;
    private String contact;
    private String image;

    public firebase_adapter_tent(String shopname, String address, String FirstName, String LastName, String contact,String image ) {


        this.shopname = shopname;
        this.address = address;
       this.FirstName = FirstName;
       this.LastName = LastName;
        this.contact = contact;
        this.image = image;

    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }





    public firebase_adapter_tent() {

    }

    public interface OnNoteListener{
        void onNoteClick(int position);
    }

}
