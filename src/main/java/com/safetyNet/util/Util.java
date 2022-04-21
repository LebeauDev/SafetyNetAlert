package com.safetyNet.util;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Util {
	public static int age(Date birthday) {
	    DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	    int d1 = Integer.parseInt(formatter.format(birthday));
	    int d2 = Integer.parseInt(formatter.format(new Date()));
	    int age = (d2-d1)/10000;
	    return age;
	}

}
