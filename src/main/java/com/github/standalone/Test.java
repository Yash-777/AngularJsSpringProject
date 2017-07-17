package com.github.standalone;

public class Test {
	public static void main(String[] args) {
		String file = "my.file.png.jpeg";
		int lastIndexOf = file.lastIndexOf(".");
		System.out.println("Last Index : "+lastIndexOf);
		String extension = file.substring(lastIndexOf+1);
		System.out.println("Extension : "+extension);
	}
}
