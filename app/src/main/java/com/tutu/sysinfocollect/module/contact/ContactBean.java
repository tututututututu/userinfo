package com.tutu.sysinfocollect.module.contact;

/**
 * Created by tutu on 2017/7/24.
 */

public class ContactBean {
    private String name;
    private String phone;
    private String sortKey;
    private int id;

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

    public String getSortKey() {
        return sortKey;
    }

    public void setSortKey(String sortKey) {
        this.sortKey = sortKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ContactBean(String name, String phone, String sortKey, int id) {
        setName(name);
        setPhone(phone);
        setSortKey(sortKey);
        setId(id);
    }
}
