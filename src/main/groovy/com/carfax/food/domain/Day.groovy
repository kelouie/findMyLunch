package com.carfax.food.domain

class Day implements Comparable {
    Date date
    Calendar calendar
    List<FoodRequest> foodRequests

    int getDayOfMonth() {
        calendar.get(Calendar.DAY_OF_MONTH)
    }

    String getDayOfWeek() {
        calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH)
    }

    String getMonthYear() {
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
        int year = calendar.get(Calendar.YEAR)

        return "$month, $year"
    }

    void setDate(Date date) {
        this.date = date
        calendar = Calendar.getInstance()
        calendar.setTime(date)
    }

    @Override
    int compareTo(Object o) {
        date.time - (o as Day).date.time
    }
}
