package com.androidcamp.tripmileage.models;

import java.util.Date;

public class TripRecord {
   TripLocation start;
   TripLocation end;
   Date startTime;
   Date endTime;
   String imgUrl_start;
   String imgUrl_end;
   long mileage_start;
   long mileage_end;
   long mileage_ontrip;
   String email_address_sent;
}
