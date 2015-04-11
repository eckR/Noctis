package com.rosinen.noctis.Model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rosinen.noctis.R;
import com.rosinen.noctis.activity.NoctisApplication;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

/**
 * Created by Harald on 20.03.2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class NoctisEvent {
    private static final long serialVersionUID = 1L;

    private String facebookId;
    private String name;
    @JsonIgnore
    private Date start;
    @JsonIgnore
    private Date endTime;
    private String location;
    private Double latitude;
    private Double longitude;
    private int attendingCount;
    private String description;
    private String coverUrl;
    private String pictureUrl;
    private float distance;
    private Integer rating;

    /**
     * used to store the thumbnail
     */
    private Bitmap pictureSmall;
    /**
     * stores the thumbnail too if no fullscale picture is available
     * so it is safe to access this even if there is no picture available
     */
    private Bitmap pictureBig;

    public NoctisEvent() {
        Context ctx = NoctisApplication.getContext();
        pictureBig = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.kalender); // default init with "no event img available" picture so no nullchecks are neccessary

    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public NoctisEvent(String facebookId, String name, Date start, Date endTime, String location, Double latitude, Double longitude, String url, int attendingCount, String description, String pictureUrl, String pictureSmallUrl, float distance){
        this.facebookId = facebookId;
        this.name=name;
        this.start=start;
        this.endTime = endTime;
        this.location=location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.coverUrl =url;
        this.attendingCount = attendingCount;
        this.description=description;
        this.pictureUrl = pictureUrl;
        this.description=description.replaceAll("\\\\n", System.getProperty("line.separator"));
        this.coverUrl = pictureSmallUrl;
        this.distance = distance;

        Context ctx = NoctisApplication.getContext();
        pictureBig = BitmapFactory.decodeResource(ctx.getResources(),R.drawable.kalender); // default init with "no event img available" picture so no nullchecks are neccessary

    }

    public String getKey(){
        StringBuilder sb = new StringBuilder();//TODO set capacity (fbid might always be the same length
        sb.append(getFacebookId()).append(getAttendingCount());
        return sb.toString();
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public void setAttendingCount(int attendingCount) {
        this.attendingCount = attendingCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getFacebookId(){
        return facebookId;
    }
    public String getName(){
        return name;
    }
    public Date getStart(){
        return start;
    }
    public Date getEndTime(){
        return endTime;
    }
    public String getLocation(){
        return location;
    }
    public LatLng getCoords(){
        return new LatLng(latitude, longitude);
    }
    public String getSmallPicUrl(){
        return coverUrl;
    }
    public int getAttendingCount(){
        return attendingCount;
    }

    public String getDescription(){
        return description;
    }
    public String getPictureUrl(){
        return pictureUrl;
    }

    public Bitmap getPictureSmall() {
        return pictureSmall;
    }

    public void setPictureSmall(Bitmap pictureSmall) {
        this.pictureSmall = pictureSmall;
    }

    public Bitmap getPictureBig() {
        return pictureBig;
    }

    public void setPictureBig(Bitmap pictureBig) {
        this.pictureBig = pictureBig;
    }

}
