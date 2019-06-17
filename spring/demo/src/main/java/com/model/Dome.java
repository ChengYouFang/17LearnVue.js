package com.model;

import java.awt.print.Book;

public class Dome {

    private Integer code;
    private Dome2 data;
    
	
    public Dome(){

    }


    public Dome(Integer code, String data,String name,String[] tmp) {
        super();
        this.code = code;

    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

 
	public Dome2 getData() {
		return data;
	}
	public void setData(Dome2 data) {
		this.data = data;
	}
}
