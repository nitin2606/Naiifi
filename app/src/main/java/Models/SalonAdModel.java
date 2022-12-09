package Models;

public class SalonAdModel {
    private String SalonName, discount, imageUrl;

    public SalonAdModel(String salonName, String discount, String imageUrl) {
        SalonName = salonName;
        this.discount = discount;
        this.imageUrl = imageUrl;
    }

    public String getSalonName() {
        return SalonName;
    }

    public void setSalonName(String salonName) {
        SalonName = salonName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
