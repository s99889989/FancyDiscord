package com.daxton.fancydiscord.api;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SubtractionTime {

	private long day;

	private long hour;

	private long minute;

	private long second;

	public SubtractionTime(String inOld, String inNow){

		SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date nowDate;
		Date oldDate;
		try {
			nowDate = sim.parse(inNow);
			oldDate = sim.parse(inOld);
		} catch (ParseException e) {

			return;
		}
		long countTime = nowDate.getTime() - oldDate.getTime();

		day = countTime/(24*60*60*1000);
		hour = (countTime/(60*60*1000)-day*24);
		minute = ((countTime/(60*1000))-day*24*60-hour*60);
		second = (countTime/1000-day*24*60*60-hour*60*60-minute*60);

	}

	public long getDay() {
		return day;
	}

	public long getHour() {
		return hour;
	}

	public long getMinute() {
		return minute;
	}

	public long getSecond() {
		return second;
	}
}
