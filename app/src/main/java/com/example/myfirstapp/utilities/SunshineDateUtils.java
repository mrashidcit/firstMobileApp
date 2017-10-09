package com.example.myfirstapp.utilities;

import java.util.TimeZone;

/**
 * Created by Rashid Saleem on 10/9/2017.
 */

// Handling date conversions that are useful for Sunshine.
public final class SunshineDateUtils {

    public static final long SECOND_IN_MILLIS = 1000;
    public static final long MINUTE_IN_MILLIS = SECOND_IN_MILLIS * 60;
    public static final long HOUR_IN_MILLIS = MINUTE_IN_MILLIS * 60;
    public static final long DAY_IN_MILLIS = HOUR_IN_MILLIS * 24;

    /*
        This method return the number of days since
        the epoch (January 01, 1970, 12:00 Midnight UTC)
        in UTC time from the current date.

        @param date A date in millisceconds in local time.

        @return The number of days in UTC time for the epoch.
     */
    public static long getDayNumber(long date){
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(date);
        return (date + gmtOffset) / DAY_IN_MILLIS;

    }

    /*
        To make it easy to query for the exact date,
        we normalize all dates that go into
        the database to start the day in UTC time.

         @param date The UTC date to normalize

         @return The UTC date at 12 midnight
     */
    public static long normalizeDate(long date) {
        // Normalize the start date to the beginning of the
        // (UTC) day in local time
        long retValNew = date / DAY_IN_MILLIS * DAY_IN_MILLIS;
        return retValNew;
    }

    /**
     * Since all dates for the database are in UTC, we must convert
     * the given date
     * (in UTC timezone) to the date in the local timezone.
     * The function performs that conversion using the
     * TimeZone offset.
     * @param utcDate The UTC datetime to convert to a local
     *                datetime, in milliseconds.
     * @return The local date
     * (the UTC datetime - the TimeZone offset) in milliseconds.
     */
    public static long getLocalDateFromUTC(long utcDate) {
        TimeZone tz = TimeZone.getDefault();
        long gmtOffset = tz.getOffset(utcDate);
        return utcDate - gmtOffset;

    }

    /**
     * Since all dates from the database are in UTC,
     * we must convert the local date to the date in
     * UTC time. This function perform that conversion
     * using the TimeZone offset.
     *
     * @param localDate The local datetime to covert
     *         to a UTC datetime, in milliseconds.
     * @return The UTC date
     * (the local datetime + the TimeZone offset ) in milliseconds
     */








}
