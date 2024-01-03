package ikish.aftab.ws.android.classes.calendarLibrary;



import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import ikish.aftab.ws.android.R;


public class DateRangeCalendarView extends LinearLayout {
    //region Fields
    private Context mContext;
    public static String type="0";
    private AttributeSet attrs;
    private LinearLayout llDaysContainer;
    private LinearLayout llTitleWeekContainer;
    private TextView imgVNavLeft, imgVNavRight;
    private Locale locale;
    private PersianCalendar currentCalendarMonth, minSelectedDate, maxSelectedDate;
    private ArrayList<Integer> selectedDatesRange = new ArrayList<>();
    private Typeface typeface;
    // private RelativeLayout rlHeaderCalendar;

    private static final int STRIP_TYPE_NONE = 0;
    private static final int STRIP_TYPE_LEFT = 1;
    private static final int STRIP_TYPE_RIGHT = 2;
    private int headerBackgroundColor, weekColor, rangeStripColor, selectedDateCircleColor, selectedDateColor, defaultDateColor, disableDateColor, rangeDateColor, holidayColor, todayColor;
    private boolean shouldEnabledTime = false;
    private float textSizeTitle, textSizeWeek, textSizeDate;
    private PersianCalendar selectedCal, date;
    private boolean isHideHeader = false;

    //endregion

    //region Enum
    public enum SelectionMode {
        Single(1),
        Range(2),
        None(3);

        private final int value;

        SelectionMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    //endregion

    //region Constructor
    public DateRangeCalendarView(Context context) {
        super(context);
        this.mContext = context;
        this.attrs = null;


        initView();
    }

    public DateRangeCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.mContext = context;
        this.attrs = attrs;

        initView();
    }

    private void initView() {
        typeface = FontUtils.Default(mContext);
        locale = mContext.getResources().getConfiguration().locale;

        setDefaultValues();

        setAttributes();

        init();
    }

    private void setDefaultValues() {

        textSizeTitle = 10.0f;
        textSizeWeek = 12.0f;
        textSizeDate = 13.0f;

        headerBackgroundColor = ContextCompat.getColor(mContext, R.color.headerBackgroundColor);

        weekColor = ContextCompat.getColor(mContext, R.color.week_color);
        selectedDateCircleColor = ContextCompat.getColor(mContext, R.color.selected_date_circle_color);
        selectedDateColor = ContextCompat.getColor(mContext, R.color.black);
        defaultDateColor = ContextCompat.getColor(mContext, R.color.default_date_color);
        rangeDateColor = ContextCompat.getColor(mContext, R.color.range_date_color);
        disableDateColor = ContextCompat.getColor(mContext, R.color.disable_date_color);

        rangeStripColor = ContextCompat.getColor(mContext, R.color.range_bg_color);
        holidayColor = ContextCompat.getColor(mContext, R.color.default_date_color);
        todayColor = ContextCompat.getColor(mContext, R.color.today_date_color);
    }







    public void setAttributes() {
        TypedArray ta = mContext.obtainStyledAttributes(attrs, R.styleable.DateRangeCalendarView, 0, 0);
        try {
            shouldEnabledTime = ta.getBoolean(R.styleable.DateRangeCalendarView_enable_time_selection, shouldEnabledTime);

            //text size
            textSizeTitle = ta.getDimension(R.styleable.DateRangeCalendarView_text_size_title, textSizeTitle);
            textSizeWeek = ta.getDimension(R.styleable.DateRangeCalendarView_text_size_week, textSizeWeek);
            textSizeDate = ta.getDimension(R.styleable.DateRangeCalendarView_text_size_date, textSizeDate);

            //header color
            headerBackgroundColor = ta.getColor(R.styleable.DateRangeCalendarView_header_background_color, headerBackgroundColor);

            //weekColor
            weekColor = ta.getColor(R.styleable.DateRangeCalendarView_week_color, weekColor);
            selectedDateCircleColor = ta.getColor(R.styleable.DateRangeCalendarView_selected_date_circle_color, selectedDateCircleColor);
            selectedDateColor = ta.getColor(R.styleable.DateRangeCalendarView_selected_date_color, selectedDateColor);
            defaultDateColor = ta.getColor(R.styleable.DateRangeCalendarView_default_date_color, defaultDateColor);
            rangeDateColor = ta.getColor(R.styleable.DateRangeCalendarView_range_date_color, rangeDateColor);
            disableDateColor = ta.getColor(R.styleable.DateRangeCalendarView_disable_date_color, disableDateColor);
            rangeStripColor = ta.getColor(R.styleable.DateRangeCalendarView_range_color, rangeStripColor);
            holidayColor = ta.getColor(R.styleable.DateRangeCalendarView_holidayColor, holidayColor);
            todayColor = ta.getColor(R.styleable.DateRangeCalendarView_todayColor, todayColor);

            selectionMode = ta.getInt(R.styleable.DateRangeCalendarView_selectionMode, selectionMode);
        } catch (Exception e) {
            Log.e("setAttributes", e.getMessage());
        } finally {
            ta.recycle();
        }
    }



    private void init() {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        LinearLayout mainView = (LinearLayout) layoutInflater.inflate(R.layout.layout_calendar_month, this, true);
        llDaysContainer = mainView.findViewById(R.id.llDaysContainer);
        llTitleWeekContainer = mainView.findViewById(R.id.llTitleWeekContainer);

        imgVNavLeft = mainView.findViewById(R.id.imgVNavLeft);
        imgVNavRight = mainView.findViewById(R.id.imgVNavRight);


        setListeners();

        if (isInEditMode()) {
            return;
        }

        build();
    }

    //endregion



    //region Core
    //region NavigationClickListener & dayClickListener
    private void nextMonth() {
        currentCalendarMonth.setPersianMonth(currentCalendarMonth.getPersianMonth() + 1);
        currentCalendarMonth.setPersianDay(1);

        drawCalendarForMonth(currentCalendarMonth);
    }

    private void prevMonth() {
        if (currentCalendarMonth.getPersianMonth() == 0) {
            currentCalendarMonth.setPersianYear(currentCalendarMonth.getPersianYear() - 1);
            currentCalendarMonth.setPersianMonth(11);
        } else {
            currentCalendarMonth.setPersianMonth(currentCalendarMonth.getPersianMonth() - 1);
        }

        currentCalendarMonth.setPersianDay(1);

        drawCalendarForMonth(currentCalendarMonth);
    }

    private void setListeners() {
        //region imgVNavLeft.setOnClickListener
        imgVNavLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                nextMonth();
            }
        });
        //endregion

        //region imgVNavRight.setOnClickListener
        imgVNavRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                prevMonth();
            }
        });
        //endregion
    }

    private final OnClickListener dayClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            DayContainer container = new DayContainer((RelativeLayout) view);
            int key = (int) view.getTag();

            selectedCal = new PersianCalendar();
            date = DayContainer.GetDateFromKey(String.valueOf(key));
            selectedCal.setPersianDate(date.getPersianYear(), date.getPersianMonth(), date.getPersianDay());

            if (selectionMode == SelectionMode.Single.getValue()) {
                //region SelectionMode.Single
                resetAllSelectedViews();
                makeAsSelectedDate(container, 0);


                minSelectedDate = selectedCal;
                maxSelectedDate = null;

                makeAsSelectedDate(container, 0);
                if (shouldEnabledTime) {
                    Log.w("TAG", "DateRangeCalendarView_onClick_296-> :");

                    PersianCalendar p = (PersianCalendar) selectedCal.clone();
                    selectedCal.setPersianDate(p.getPersianYear(), p.getPersianMonth(), p.getPersianDay());

                    if (calendarListener != null) {
                        calendarListener.onDateRangeSelected(minSelectedDate, maxSelectedDate);

                    }


//                   //region shouldEnabledTime
//                    TimePickerDialog awesomeTimePickerDialog = new TimePickerDialog(mContext, mContext.getString(R.string.select_time), new TimePickerDialog.TimePickerCallback() {
//                        @Override
//                        public void onTimeSelected(int hours, int mins) {
//                            PersianCalendar p = (PersianCalendar) selectedCal.clone();
//
//                            selectedCal.set(Calendar.HOUR, hours);
//                            selectedCal.set(Calendar.MINUTE, mins);
//                            selectedCal.set(Calendar.SECOND, 0);
//                            selectedCal.set(Calendar.MILLISECOND, 0);
//                            selectedCal.setPersianDate(p.getPersianYear(), p.getPersianMonth(), p.getPersianDay());
//
//                            if (calendarListener != null && minSelectedDate != null && maxSelectedDate != null) {
//                                calendarListener.onDateRangeSelected(minSelectedDate, maxSelectedDate);
//                            }
//                        }
//
//                        @Override
//                        public void onCancel() {
//                            resetAllSelectedViews();
//                        }
//                    });
//                    awesomeTimePickerDialog.showDialog();
//                    //endregion
                } else {
                    //region SelectDateWithoutTime
                    if (calendarListener != null) {
                        calendarListener.onDateSelected(selectedCal);
                    }
                    //endregion
                }
                //endregion
            }
            else if (selectionMode == SelectionMode.Range.getValue()) {
                //region SelectionMode.Range

                if (minSelectedDate != null) {
                    if (maxSelectedDate == null) {
                        maxSelectedDate = selectedCal;
                        drawSelectedDateRange(minSelectedDate, maxSelectedDate);
                    } else {
                        if (!TextUtils.isEmpty(getMessageEnterEndDate())) {

                            Toast.makeText(mContext, getMessageEnterEndDate(), Toast.LENGTH_SHORT).show();
                        }

                        resetAllSelectedViews();

                        minSelectedDate = selectedCal;
                        maxSelectedDate = null;

                        makeAsSelectedDate(container, 0);
                    }
                } else {
                    if (!TextUtils.isEmpty(getMessageEnterEndDate())) {

                        Toast.makeText(mContext, getMessageEnterEndDate(), Toast.LENGTH_SHORT).show();
                    }

                    minSelectedDate = selectedCal;
                    maxSelectedDate = null;

                    makeAsSelectedDate(container, 0);
                }

                if (shouldEnabledTime) {
                    Log.w("TAG", "DateRangeCalendarView_onClick_296-> :");

                    PersianCalendar p = (PersianCalendar) selectedCal.clone();
                    selectedCal.setPersianDate(p.getPersianYear(), p.getPersianMonth(), p.getPersianDay());

                    if (calendarListener != null) {
                        calendarListener.onDateRangeSelected(minSelectedDate, maxSelectedDate);

                    }


//                   //region shouldEnabledTime
//                    TimePickerDialog awesomeTimePickerDialog = new TimePickerDialog(mContext, mContext.getString(R.string.select_time), new TimePickerDialog.TimePickerCallback() {
//                        @Override
//                        public void onTimeSelected(int hours, int mins) {
//                            PersianCalendar p = (PersianCalendar) selectedCal.clone();
//
//                            selectedCal.set(Calendar.HOUR, hours);
//                            selectedCal.set(Calendar.MINUTE, mins);
//                            selectedCal.set(Calendar.SECOND, 0);
//                            selectedCal.set(Calendar.MILLISECOND, 0);
//                            selectedCal.setPersianDate(p.getPersianYear(), p.getPersianMonth(), p.getPersianDay());
//
//                            if (calendarListener != null && minSelectedDate != null && maxSelectedDate != null) {
//                                calendarListener.onDateRangeSelected(minSelectedDate, maxSelectedDate);
//                            }
//                        }
//
//                        @Override
//                        public void onCancel() {
//                            resetAllSelectedViews();
//                        }
//                    });
//                    awesomeTimePickerDialog.showDialog();
//                    //endregion
                } else {
                    //region SelectDateWithoutTime
                    if (calendarListener != null && minSelectedDate != null && maxSelectedDate != null) {
                        calendarListener.onDateRangeSelected(minSelectedDate, maxSelectedDate);
                    }
                    //endregion
                }
                //endregion
            }
            else {
                Toast.makeText(mContext, "SHOW BOTTOMSHEET", Toast.LENGTH_SHORT).show();

            }
        }
    };
    //endregion

    private PersianCalendar getCurrentMonth(PersianCalendar calendar) {
        PersianCalendar current = (PersianCalendar) calendar.clone();
        current.setPersianDay(1);
        return current;
    }


    private void drawCalendarForMonth(PersianCalendar month) {


        imgVNavRight.setText(String.format(locale, "%s %d", month.getPersianMonthName(), month.getPersianYear()));
        imgVNavLeft.setText(String.format(locale, "%s %d", PersianCalendarConstants.persianMonthNames[month.getPersianMonth() + 1], month.getPersianYear()));


        currentCalendarMonth = (PersianCalendar) month.clone();

        int startDay = month.get(Calendar.DAY_OF_WEEK);
        int persianDay = month.getPersianDay();

        if (startDay != 7) {
            month.setPersianDay(persianDay - startDay);
        }

        for (int i = 0; i < llDaysContainer.getChildCount(); i++) {
            LinearLayout weekRow = (LinearLayout) llDaysContainer.getChildAt(i);

            for (int j = 0; j < 7; j++) {
                RelativeLayout rlDayContainer = (RelativeLayout) weekRow.getChildAt(j);
                DayContainer container = new DayContainer(rlDayContainer);

                if (typeface != null) {
                    container.tvDate.setTypeface(typeface);
                }

                container.tvDate.setTextSize(textSizeDate);

                drawDayContainer(container, month);

                month.setPersianDay(month.getPersianDay() + 1);
            }
        }
    }


    private void drawDayContainer(DayContainer container, PersianCalendar calendar) {
        int date = calendar.getPersianDay();
        // int dateGR = calendar.get(Calendar.DATE);


        if (currentCalendarMonth.getPersianMonth() != calendar.getPersianMonth()) {
            hideDayContainer(container);
        } else if (isDisableDaysAgo && getCurrentDate().after(calendar) && (getCurrentDate().get(Calendar.DAY_OF_YEAR) != calendar.get(Calendar.DAY_OF_YEAR))) {
            disableDayContainer(container);
//            container.tvDate.setText(String.valueOf(date));


            setToday(container, calendar);


        } else {
            int key = DayContainer.GetContainerKey(calendar);

            setToday(container, calendar);
            if (!type.equals("0")){

                setPrice(container);
            }
            if (selectedDatesRange.indexOf(key) == 0) {
                makeAsSelectedDate(container, STRIP_TYPE_LEFT);
            } else if (selectedDatesRange.size() != 0 && selectedDatesRange.indexOf(key) == selectedDatesRange.size() - 1) {
                makeAsSelectedDate(container, STRIP_TYPE_RIGHT);
            } else if (selectedDatesRange.contains(key)) {
                makeAsRangeDate(container);
            } else {
                enabledDayContainer(container);
                selectHolidayContainer(container, calendar);
            }


            if ((selectionMode == SelectionMode.Single.getValue()) && (selectedCal != null && calendar.getPersianShortDate().equals(selectedCal.getPersianShortDate()))) {
                makeAsSelectedDate(container, STRIP_TYPE_LEFT);
            } else if ((selectionMode == SelectionMode.Range.getValue()) && (minSelectedDate != null && calendar.getPersianShortDate().equals(minSelectedDate.getPersianShortDate()))) {
                makeAsSelectedDate(container, STRIP_TYPE_LEFT);
            }


            if ((getMaxDate() != null) && (getMaxDate().before(calendar))) {
                disableDayContainer(container);
//                container.tvDate.setText(String.valueOf(date));
            }
        }

        container.tvDate.setText(String.valueOf(date));
        // container.tvDateGeorgian.setText(String.valueOf(dateGR));
        //  container.tvDateGeorgian.setVisibility(showGregorianDate ? VISIBLE : GONE);
        container.rootView.setTag(DayContainer.GetContainerKey(calendar));
    }


    private void setToday(DayContainer container, PersianCalendar persianCalendar) {
        if (getCurrentDate().getPersianShortDate().compareTo(persianCalendar.getPersianShortDate()) == 0) {
            container.imgEvent.setVisibility(VISIBLE);
            container.imgEvent.setColorFilter(todayColor, android.graphics.PorterDuff.Mode.SRC_IN);
            container.tvDate.setTypeface(typeface, Typeface.BOLD);

        } else {
            container.imgEvent.setVisibility(GONE);
            container.tvDate.setTypeface(typeface, Typeface.NORMAL);

        }
    }


    private void setPrice(DayContainer container) {

        container.imgEvent.setVisibility(GONE);
        container.tvDateGeorgian.setText("0");//add me

    }

    private void hideDayContainer(DayContainer container) {
        container.tvDate.setText("");
        container.tvDate.setBackgroundColor(Color.TRANSPARENT);
        container.strip.setBackgroundColor(Color.TRANSPARENT);
        container.rootView.setBackgroundColor(Color.TRANSPARENT);
        container.rootView.setVisibility(INVISIBLE);
        container.rootView.setOnClickListener(null);
    }

    private void disableDayContainer(DayContainer container) {
        container.tvDate.setBackgroundColor(Color.TRANSPARENT);
        container.strip.setBackgroundColor(Color.TRANSPARENT);
        container.rootView.setBackgroundColor(Color.TRANSPARENT);
        container.tvDate.setTextColor(disableDateColor);
        container.rootView.setVisibility(VISIBLE);
        container.rootView.setOnClickListener(null);
    }


    private void enabledDayContainer(DayContainer container) {
        container.tvDate.setBackgroundColor(Color.TRANSPARENT);
        container.strip.setBackgroundColor(Color.TRANSPARENT);
        container.rootView.setBackgroundColor(Color.TRANSPARENT);
        container.tvDate.setTextColor(defaultDateColor);

        container.rootView.setVisibility(VISIBLE);
        //farzane change
        container.rootView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background));
        container.rootView.setOnClickListener(dayClickListener);
    }


    private void selectHolidayContainer(DayContainer container, PersianCalendar _calendar) {
        if (_calendar.get(Calendar.DAY_OF_WEEK) == 6) {
            container.tvDate.setTextColor(holidayColor);
        }

        if (_calendar.getPersianMonth() + 1 == 1) {
            if (_calendar.getPersianDay() == 1 ||
                    _calendar.getPersianDay() == 2 ||
                    _calendar.getPersianDay() == 3 ||
                    _calendar.getPersianDay() == 4 ||
                    _calendar.getPersianDay() == 13) {
                container.tvDate.setTextColor(holidayColor);
            }
        }

        if (_calendar.getPersianMonth() + 1 == 12) {
            if (_calendar.getPersianDay() == 29 || _calendar.getPersianDay() == 30) {
                container.tvDate.setTextColor(holidayColor);
            }
        }
    }



    private void makeAsSelectedDate(DayContainer container, int stripType) {

        GradientDrawable mDrawable1 = null;
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) container.strip.getLayoutParams();
        if (stripType == STRIP_TYPE_LEFT) {
            mDrawable1 = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.shape_rect1);
            GradientDrawable mDrawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.range_bg_left);
            mDrawable.setColor(rangeStripColor);
            container.strip.setBackground(mDrawable);
            layoutParams.setMargins(0, 0, 0, 0);
        } else if (stripType == STRIP_TYPE_RIGHT) {
            mDrawable1 = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.shape_rect);
            GradientDrawable mDrawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.range_bg_right);
            mDrawable.setColor(rangeStripColor);
            container.strip.setBackground(mDrawable);
            layoutParams.setMargins(0, 0, 0, 0);
        } else {
            mDrawable1 = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.shape_circular_range);
            container.strip.setBackgroundColor(Color.TRANSPARENT);
            layoutParams.setMargins(0, 0, 0, 0);
        }


        container.strip.setLayoutParams(layoutParams);

        mDrawable1.setColor(selectedDateCircleColor);
        container.tvDate.setBackground(mDrawable1);
        container.rootView.setBackgroundColor(Color.TRANSPARENT);
        container.tvDate.setTextColor(selectedDateColor);
        container.rootView.setVisibility(VISIBLE);
    }

    /**
     * To draw date as middle date
     *
     * @param container - Container
     */
    private void makeAsRangeDate(DayContainer container) {
        container.tvDate.setBackgroundColor(Color.TRANSPARENT);
        GradientDrawable mDrawable = (GradientDrawable) ContextCompat.getDrawable(mContext, R.drawable.background1);
        mDrawable.setColor(rangeStripColor);
        container.strip.setBackground(mDrawable);
        container.rootView.setBackground(ContextCompat.getDrawable(mContext, R.drawable.background1));

        container.tvDate.setTextColor(rangeDateColor);

        container.rootView.setVisibility(VISIBLE);

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) container.strip.getLayoutParams();
        layoutParams.setMargins(0, 0, 0, 0);
        container.strip.setLayoutParams(layoutParams);
    }

    /**
     * To draw selected date range.
     *
     * @param startDate - Start date
     * @param lastDate  - End date
     */
    private void drawSelectedDateRange(PersianCalendar startDate, PersianCalendar lastDate) {
        selectedDatesRange.clear();
        int startDateKey = DayContainer.GetContainerKey(startDate);
        int lastDateKey = DayContainer.GetContainerKey(lastDate);

        PersianCalendar temp = (PersianCalendar) startDate.clone();

        if (startDateKey == lastDateKey) {

        } else if (startDateKey > lastDateKey) {
            maxSelectedDate = startDate;
            minSelectedDate = lastDate;

            int tempXyz = startDateKey;
            startDateKey = lastDateKey;
            lastDateKey = tempXyz;

            temp = (PersianCalendar) lastDate.clone();
        }

        RelativeLayout rlDayContainer = llDaysContainer.findViewWithTag(startDateKey);

        if (rlDayContainer != null && minSelectedDate.getPersianMonth() == currentCalendarMonth.getPersianMonth()) {
            DayContainer container = new DayContainer(rlDayContainer);
            int stripType = STRIP_TYPE_LEFT;
            if (startDateKey == lastDateKey) {
                stripType = STRIP_TYPE_NONE;
            }

            makeAsSelectedDate(container, stripType);
        }

        rlDayContainer = llDaysContainer.findViewWithTag(lastDateKey);
        if (rlDayContainer != null && maxSelectedDate.getPersianMonth() == currentCalendarMonth.getPersianMonth()) {
            DayContainer container = new DayContainer(rlDayContainer);
            int stripType = STRIP_TYPE_RIGHT;
            if (startDateKey == lastDateKey) {
                stripType = STRIP_TYPE_NONE;
            }
            makeAsSelectedDate(container, stripType);
        }

        selectedDatesRange.add(startDateKey);
        temp.setPersianDate(temp.getPersianYear(), temp.getPersianMonth(), temp.getPersianDay() + 1);

        int nextDateKey = DayContainer.GetContainerKey(temp);
        while (lastDateKey > nextDateKey) {
            if (temp.getPersianMonth() == currentCalendarMonth.getPersianMonth()) {
                rlDayContainer = llDaysContainer.findViewWithTag(nextDateKey);
                if (rlDayContainer != null) {
                    DayContainer container = new DayContainer(rlDayContainer);

                    makeAsRangeDate(container);
                }
            }
            selectedDatesRange.add(nextDateKey);
            temp.setPersianDate(temp.getPersianYear(), temp.getPersianMonth(), temp.getPersianDay() + 1);

            nextDateKey = DayContainer.GetContainerKey(temp);
        }
        selectedDatesRange.add(lastDateKey);
    }


    public void resetAllSelectedViews() {
        selectedDatesRange.clear();

        minSelectedDate = null;
        maxSelectedDate = null;

        drawCalendarForMonth(currentCalendarMonth);

        if (calendarListener != null) {
            calendarListener.onCancel();
        }
    }

    private void setWeekTitleColor() {
        for (int i = 0; i < llTitleWeekContainer.getChildCount(); i++) {
            CustomTextView textView = (CustomTextView) llTitleWeekContainer.getChildAt(i);
            textView.setTextColor(weekColor);
            textView.setTextSize(textSizeWeek);
        }
    }


    //endregion

    //region Properties
    public void build() {
        drawCalendarForMonth(getCurrentMonth(currentDate));
        setWeekTitleColor();

    }

    //region selectionMode -> Getter/Setter
    private int selectionMode = SelectionMode.Range.getValue();

    public int getSelectionMode() {
        return selectionMode;
    }

    public void setSelectionMode(int selectionMode) {
        this.selectionMode = selectionMode;
    }
    //endregion

    //region currentDate -> Getter/Setter
    private PersianCalendar currentDate = new PersianCalendar();

    public PersianCalendar getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(PersianCalendar currentDate) {
        this.currentDate = currentDate;


    }
    //endregion

    //region MinDate -> Getter/Setter
    private PersianCalendar minDate = new PersianCalendar();

    public PersianCalendar getMinDate() {
        return minDate;
    }

    public void setMinDate(PersianCalendar minDate) {
        this.minDate = minDate;
    }
    //endregion

    //region MaxDate -> Getter/Setter
    private PersianCalendar maxDate;

    public PersianCalendar getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(PersianCalendar maxDate) {
        this.maxDate = maxDate;
    }
    //endregion

    //region isDisableDaysAgo -> Getter/Setter
    private boolean isDisableDaysAgo = true;

    public boolean isDisableDaysAgo() {
        return isDisableDaysAgo;
    }

    public void setDisableDaysAgo(boolean disableDaysAgo) {
        isDisableDaysAgo = disableDaysAgo;
    }
    //endregion

    //region showGregorianDate -> Default = false
    private boolean showGregorianDate = false;

    public boolean isShowGregorianDate() {
        return showGregorianDate;
    }

    public void setShowGregorianDate(boolean showGregorianDate) {
        this.showGregorianDate = showGregorianDate;
    }
    //endregion

    //region messageEnterEndDate
    String messageEnterEndDate;

    public String getMessageEnterEndDate() {
        return messageEnterEndDate;
    }

    public void setMessageEnterEndDate(String messageEnterEndDate) {
        this.messageEnterEndDate = messageEnterEndDate;
    }

    //endregion

    public boolean isShouldEnabledTime() {
        return shouldEnabledTime;
    }

    public void setShouldEnabledTime(boolean shouldEnabledTime) {
        this.shouldEnabledTime = shouldEnabledTime;
    }

    //--------------------------------------------------------------------------------------------
    //region theme
    public int getHeaderBackgroundColor() {
        return headerBackgroundColor;
    }

    public void setHeaderBackgroundColor(int headerBackgroundColor) {
        this.headerBackgroundColor = headerBackgroundColor != 0 ? headerBackgroundColor : this.headerBackgroundColor;
    }

    public int getWeekColor() {
        return weekColor;
    }

    public void setWeekColor(int weekColor) {
        this.weekColor = weekColor != 0 ? weekColor : this.weekColor;
    }

    public int getRangeStripColor() {
        return rangeStripColor;
    }

    public void setRangeStripColor(int rangeStripColor) {
        this.rangeStripColor = rangeStripColor != 0 ? rangeStripColor : this.rangeStripColor;
    }

    public int getSelectedDateCircleColor() {
        return selectedDateCircleColor;
    }

    public void setSelectedDateCircleColor(int selectedDateCircleColor) {
        this.selectedDateCircleColor = selectedDateCircleColor != 0 ? selectedDateCircleColor : this.selectedDateCircleColor;
    }

    public int getSelectedDateColor() {
        return selectedDateColor;
    }

    public void setSelectedDateColor(int selectedDateColor) {
        this.selectedDateColor = selectedDateColor != 0 ? selectedDateColor : this.selectedDateColor;
    }

    public int getDefaultDateColor() {
        return defaultDateColor;
    }

    public void setDefaultDateColor(int defaultDateColor) {
        this.defaultDateColor = defaultDateColor != 0 ? defaultDateColor : this.defaultDateColor;
    }

    public int getDisableDateColor() {
        return disableDateColor;
    }

    public void setDisableDateColor(int disableDateColor) {
        this.disableDateColor = disableDateColor != 0 ? disableDateColor : this.disableDateColor;
    }

    public int getRangeDateColor() {
        return rangeDateColor;
    }

    public void setRangeDateColor(int rangeDateColor) {
        this.rangeDateColor = rangeDateColor != 0 ? rangeDateColor : this.rangeDateColor;
    }

    public int getHolidayColor() {
        return holidayColor;
    }

    public void setHolidayColor(int holidayColor) {
        this.holidayColor = holidayColor != 0 ? holidayColor : this.holidayColor;
    }

    public int getTodayColor() {
        return todayColor;
    }

    public void setTodayColor(int todayColor) {
        this.todayColor = todayColor != 0 ? todayColor : this.todayColor;
    }


    public float getTextSizeTitle() {
        return textSizeTitle;
    }

    public void setTextSizeTitle(float textSizeTitle) {
        this.textSizeTitle = textSizeTitle != 0 ? textSizeTitle : this.textSizeTitle;
    }

    public float getTextSizeWeek() {
        return textSizeWeek;
    }

    public void setTextSizeWeek(float textSizeWeek) {
        this.textSizeWeek = textSizeWeek != 0 ? textSizeWeek : this.textSizeWeek;


    }

    public float getTextSizeDate() {
        return textSizeDate;
    }

    public void setTextSizeDate(float textSizeDate) {
        this.textSizeDate = textSizeDate != 0 ? textSizeDate : this.textSizeDate;
    }

    //endregion
    //--------------------------------------------------------------------------------------------
    //region Listener -> Getter/Setter
    private CalendarListener calendarListener;

    public CalendarListener getCalendarListener() {
        return calendarListener;
    }

    public void setCalendarListener(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }
    //endregion
    //endregion
    //--------------------------------------------------------------------------------------------

    public interface CalendarListener {
        void onDateSelected(PersianCalendar date);

        void onDateRangeSelected(PersianCalendar startDate, PersianCalendar endDate);

        void onCancel();
    }
}

