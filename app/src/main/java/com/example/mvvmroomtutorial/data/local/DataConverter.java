package com.example.mvvmroomtutorial.data.local;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Created by debasish on 20/9/19.
 */

public class DataConverter {
    @TypeConverter
    public static Date toDate(Long timestamp){
        return timestamp == null?null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date){
        return date == null?null :date.getTime();
    }
}
