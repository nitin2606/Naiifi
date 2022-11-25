package Codes;

public class Distance {

    public float coordinateDistance(double lat1, double lat2, double long1, double long2){

        long1 = Math.toRadians(long1);
        long2 = Math.toRadians(long2);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double dlong = long2 - long1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlong / 2),2);

        double c = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;
        return (float) (c * r);
    }
}
