package com.example.androidtraining2_08_1912120208.bean;


import com.chad.library.adapter.base.entity.MultiItemEntity;

public class rentalDto extends Rental  implements MultiItemEntity {
    private User user;
    private Car car;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public int getItemType() {
        return 2;
    }
}
