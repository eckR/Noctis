package at.rosinen.Noctis.Model;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Harald on 20.03.2015.
 */
public class NoctisEvent {
    private static final long serialVersionUID = 1L;
    private Long fbID;
    private String name;
    private Date start;
    private Date end;
    private String location;
    private Double lat;
    private Double lng;
    private String pictureSmallUrl;
    private int attending;
    private String description;
    private String pictureBigUrl;
    private float distance;

    public NoctisEvent() {

    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public NoctisEvent(long fbID, String name, Date start, Date end, String location, Double lat, Double lng, String url, int attending, String description, String pictureBigUrl, String pictureSmallUrl, float distance){
        this.fbID=fbID;
        this.name=name;
        this.start=start;
        this.end=end;
        this.location=location;
        this.lat =lat;
        this.lng =lng;
        this.pictureSmallUrl=url;
        this.attending=attending;
        this.description=description;
        this.pictureBigUrl=pictureBigUrl;
        this.description=description.replaceAll("\\\\n", System.getProperty("line.separator"));
        this.pictureSmallUrl = pictureSmallUrl;
        this.distance = distance;

    }

    public void setFbID(Long fbID) {
        this.fbID = fbID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public void setPictureSmallUrl(String pictureSmallUrl) {
        this.pictureSmallUrl = pictureSmallUrl;
    }

    public void setAttending(int attending) {
        this.attending = attending;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureBigUrl(String pictureBigUrl) {
        this.pictureBigUrl = pictureBigUrl;
    }

    public Long getFBID(){
        return fbID;
    }
    public String getName(){
        return name;
    }
    public Date getStart(){
        return start;
    }
    public Date getEnd(){
        return end;
    }
    public String getLocation(){
        return location;
    }
    public LatLng getCoords(){
        return new LatLng(lat, lng);
    }
    public String getSmallPicUrl(){
        return pictureSmallUrl;
    }
    public int getAttending(){
        return attending;
    }

    public String getDescription(){
        return description;
    }
    public String getPictureBigUrl(){
        return pictureBigUrl;
    }
}
