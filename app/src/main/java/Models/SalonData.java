package Models;


public class SalonData {
    private String name, address, distance, imageUrl;
    private Boolean isBookmarked;

    public SalonData(String name, String address, String distance, String imageUrl, Boolean isBookmarked) {
        this.name = name;
        this.address = address;
        this.distance = distance;
        this.imageUrl = imageUrl;
        this.isBookmarked = isBookmarked;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public Boolean getBookmarked() {
        return isBookmarked;
    }

    public void setBookmarked(Boolean bookmarked) {
        isBookmarked = bookmarked;
    }



}
