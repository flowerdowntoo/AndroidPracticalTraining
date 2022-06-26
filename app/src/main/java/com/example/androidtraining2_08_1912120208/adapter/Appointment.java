package com.example.androidtraining2_08_1912120208.adapter;



import java.io.Serializable;

public class Appointment implements Serializable {
    /**
     * 
     */

    private Integer id;

    /**
     * 
     */
    private String rentalid;

    /**
     * 
     */
    private String appointday;

    /**
     * 
     */
    private String appointtime;

    /**
     * 
     */
    private String employerid;

    /**
     * 
     */
    private String telephone;

    /**
     * 1.可预约 2.预约中 3.已结束
     */
    private Integer orderstate;


    private static final long serialVersionUID = 1L;





    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", rentalid=").append(rentalid);
        sb.append(", appointday=").append(appointday);
        sb.append(", appointtime=").append(appointtime);
        sb.append(", employerid=").append(employerid);
        sb.append(", telephone=").append(telephone);
        sb.append(", orderstate=").append(orderstate);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRentalid() {
        return rentalid;
    }

    public void setRentalid(String rentalid) {
        this.rentalid = rentalid;
    }

    public String getAppointday() {
        return appointday;
    }

    public void setAppointday(String appointday) {
        this.appointday = appointday;
    }

    public String getAppointtime() {
        return appointtime;
    }

    public void setAppointtime(String appointtime) {
        this.appointtime = appointtime;
    }

    public String getEmployerid() {
        return employerid;
    }

    public void setEmployerid(String employerid) {
        this.employerid = employerid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getOrderstate() {
        return orderstate;
    }

    public void setOrderstate(Integer orderstate) {
        this.orderstate = orderstate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}