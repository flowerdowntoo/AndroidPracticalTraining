package com.example.androidtraining2_08_1912120208.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

/**
 * 
 * @TableName car
 */

public class Car implements Serializable, MultiItemEntity {
    /**
     * 
     */

    private Integer id;

    /**
     * 
     */
    private String platenumber;

    /**
     * 
     */
    private String brand;

    /**
     * 
     */
    private String color;

    /**
     * 
     */
    private String userid;

    /**
     * 
     */
    private Integer carstate;

    /**
     * 
     */
    private String image;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", platenumber=").append(platenumber);
        sb.append(", brand=").append(brand);
        sb.append(", color=").append(color);
        sb.append(", userid=").append(userid);
        sb.append(", carstate=").append(carstate);
        sb.append(", image=").append(image);
        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlatenumber() {
        return platenumber;
    }

    public void setPlatenumber(String platenumber) {
        this.platenumber = platenumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getCarstate() {
        return carstate;
    }

    public void setCarstate(Integer carstate) {
        this.carstate = carstate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}