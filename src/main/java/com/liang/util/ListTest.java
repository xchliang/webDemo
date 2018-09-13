package com.liang.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListTest {
	static int n = 10000;
	
	public static void main(String[] args) {
		List<Integer> alist = new ArrayList<Integer>();
		alist.add(1);
		System.out.println(alist.size());
		
		
		LinkedList<Integer> list = new LinkedList<Integer>();
		System.out.println("LinkedList添加："+add(list)+" ms");
		System.out.println("LinkedList头部添加："+addFirst(list)+" ms");
		System.out.println("LinkedList for循环遍历："+readListByFor(list)+" ms");
		System.out.println("LinkedList for each遍历："+readListByForEach(list)+" ms");
//		System.out.println("LinkedList remove："+removeFirst(list)+" ms");
		System.out.println("LinkedList remove："+removeLast(list)+" ms");
		/////////////////////////////////////////////////////////////////////
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		System.out.println("ArrayList添加："+add(list2)+" ms");
		System.out.println("ArrayList头部添加："+addFirst(list2)+" ms");
		System.out.println("ArrayList for循环遍历："+readListByFor(list2)+" ms");
		System.out.println("ArrayList for each遍历："+readListByForEach(list2)+" ms");
		System.out.println("ArrayList remove："+remove(list2)+" ms");
		
		
		
	}
	
	public static long add(List<Integer> list){
		long t = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			list.add(i);
		}
		return System.currentTimeMillis()-t;
	}
	
	public static long addFirst(List<Integer> list){
		long t = System.currentTimeMillis();
		for (int i = 0; i < n; i++) {
			list.add(1,i);
		}
		return System.currentTimeMillis()-t;
	}
	
	public static long readListByFor(List<Integer> list){
		long t = System.currentTimeMillis();
		for (int i = 0; i < list.size(); i++) {
			list.get(i);
		}
		return System.currentTimeMillis()-t;
	}
	
	public static long readListByForEach(List<Integer> list){
		long t = System.currentTimeMillis();
		for (Integer i:list) {
		}
		return System.currentTimeMillis()-t;
	}
	
	public static long remove(List<Integer> list){
		long t = System.currentTimeMillis();
		for (int j = n; j >0; j--) {
			list.remove(list.size()-1);
		}
		return System.currentTimeMillis()-t;
	}

	public static long removeFirst(LinkedList<Integer> list){
		long t = System.currentTimeMillis();
		for (int j = n; j >0; j--) {
			list.removeFirst();
		}
		return System.currentTimeMillis()-t;
	}
	
	public static long removeLast(LinkedList<Integer> list){
		long t = System.currentTimeMillis();
		for (int j = n; j >0; j--) {
			list.removeLast();
		}
		return System.currentTimeMillis()-t;
	}
	
	
	
	
}
