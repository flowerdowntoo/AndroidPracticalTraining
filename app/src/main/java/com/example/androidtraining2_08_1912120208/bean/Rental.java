package com.example.androidtraining2_08_1912120208.bean;


import java.io.Serializable;

/**
 * 
 * @TableName rental
 */

public class Rental implements Serializable {
    /**
     * 
     */

    private Integer id;

    /**
     * 
     */
    private String  userid;

    /**
     * 
     */
    private Integer carid;

    /**
     * 
     */
    private String startday;

    /**
     * 
     */
    private String finishday;

    /**
     * 
     */
    private String starttime;

    /**
     * 
     */
    private String finishtime;

    /**
     * 
     */
    private Integer examinestate;


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userid=").append(userid);
        sb.append(", carid=").append(carid);
        sb.append(", startday=").append(startday);
        sb.append(", finishday=").append(finishday);
        sb.append(", starttime=").append(starttime);
        sb.append(", finishtime=").append(finishtime);
        sb.append(", examinestate=").append(examinestate);

        sb.append("]");
        return sb.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getCarid() {
        return carid;
    }

    public void setCarid(Integer carid) {
        this.carid = carid;
    }

    public String getStartday() {
        return startday;
    }

    public void setStartday(String startday) {
        this.startday = startday;
    }

    public String getFinishday() {
        return finishday;
    }

    public void setFinishday(String finishday) {
        this.finishday = finishday;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(String finishtime) {
        this.finishtime = finishtime;
    }

    public Integer getExaminestate() {
        return examinestate;
    }

    public void setExaminestate(Integer examinestate) {
        this.examinestate = examinestate;
    }
}