package com.txr.forlove.domain.demo;

import java.util.ArrayList;
import java.util.List;

public class UserDm {

    private Long id;
    private String name;
    private String phone;

    public static List<UserDm> createdList(int num){
        List<UserDm> list = new ArrayList<>();
        for(int i=0;i<num;i++){
            UserDm userDm = new UserDm();
            userDm.setId((long)i);
            userDm.setName("name" + i);
            list.add(userDm);
        }
        return list;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
