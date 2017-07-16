package com.lvgou.qdd.model;

import java.util.List;

/**
 * Created by sampson on 2017/7/15.
 */

public class Shopping {
    private  String goodname;
    private  String allnum;
    private  String last_name;
    private  String time;
    private  List<Good> goods;


    public String getGoodname() {
        return goodname;
    }

    public void setGoodname(String goodname) {
        this.goodname = goodname;
    }

    public String getAllnum() {
        return allnum;
    }

    public void setAllnum(String allnum) {
        this.allnum = allnum;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }

    @Override
    public String toString() {
        return "Shopping{" +
                "goodname='" + goodname + '\'' +
                ", allnum='" + allnum + '\'' +
                ", last_name='" + last_name + '\'' +
                ", time=" + time +
                ", goods=" + goods +
                '}';
    }

    public static class Good{
        private  String id;

        private  String name;

        private  String contents;

        private  String price;

        private  String num;

        public  Good(){
            //解决fastjson的问题
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        @Override
        public String toString() {
            return "Good{" +
                    "id='" + id + '\'' +
                    ", name='" + name + '\'' +
                    ", contents='" + contents + '\'' +
                    ", num='" + num + '\'' +
                    '}';
        }
    }




}
