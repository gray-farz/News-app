package com.example.finalstep2.model;

import com.google.gson.annotations.SerializedName;

public class Response {

	@SerializedName("date")
	private String date;

	@SerializedName("image")
	private String image;

	@SerializedName("cat")
	private String cat;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("cat_image")
	private String catImage;

	public String getDate(){
		return date;
	}

	public String getImage(){
		return image;
	}

	public String getCat(){
		return cat;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getCatImage(){
		return catImage;
	}
}