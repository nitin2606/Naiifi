package Models;



public class SalonListModel {
    private String name, phoneNo;
    private float rating;
    private int anInt;

    public SalonListModel(String name, String phoneNo, float rating, int anInt) {

        this.name = name;
        this.phoneNo = phoneNo;
        this.rating = rating;
        this.anInt = anInt;
    }

    public SalonListModel(){

    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }
}
