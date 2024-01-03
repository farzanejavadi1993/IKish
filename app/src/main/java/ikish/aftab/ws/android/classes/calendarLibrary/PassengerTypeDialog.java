package ikish.aftab.ws.android.classes.calendarLibrary;




import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import ikish.aftab.ws.android.R;


public class PassengerTypeDialog extends Dialog {
//    private CustomTextView tvHeaderTitle, tvDialogDone, tvDialogCancel;

    //    private String mTitle;
    private int oldCount=0;
    private int youngCount=0;
    private int babyCount=0;
    private String nameDetach="";
    private Boolean click=false;
    private MaterialButton btnOk;
    private ImageView btnClose;
    private ImageView maxOld;
    private ImageView minOld;
    private TextView tvOldCount;

    private ImageView maxYoung;
    private ImageView minYoung;
    private TextView tvYoungCount;


    private ImageView maxbaby;
    private ImageView minbaby;
    private TextView tvbabyCount;

    /* private TimePicker timePicker;*/

    public interface DialogCallback {
        void onClickSelected(String name);

        void onCancel();
    }

    private DialogCallback onClickListener;

    public PassengerTypeDialog(Context context, String title, DialogCallback timePickerCallback) {
        super(context);
//        mContext = context;
        // mTitle = title;
        this.onClickListener = timePickerCallback;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(false);

        initView();

        setListeners();

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void initView() {
        setContentView(R.layout.passenger_type_dialog);

        btnOk = findViewById(R.id.btn_ok);
        btnClose = findViewById(R.id.btn_close_dialog);
        maxOld = findViewById(R.id.iv_max_room);
        minOld = findViewById(R.id.iv_min_room);
        tvOldCount = findViewById(R.id.tv_count_room);

        maxYoung = findViewById(R.id.iv_max_young);
        minYoung = findViewById(R.id.iv_min_young);
        tvYoungCount = findViewById(R.id.tv_count_young);

        maxbaby = findViewById(R.id.iv_max_baby);
        minbaby = findViewById(R.id.iv_min_baby);
        tvbabyCount = findViewById(R.id.tv_count_baby);
        // tvDialogDone = findViewById(R.id.tvHeaderDone);
        // tvDialogCancel = findViewById(R.id.tvHeaderCancel);

        //  timePicker = findViewById(R.id.timePicker);

//        btnOk.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
//            @Override
//            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
//                hours = hourOfDay;
//                minutes = minute;
//            }
//        });
//
//        tvHeaderTitle.setText(mTitle);
    }

    private void setListeners() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {

                    onClickListener.onClickSelected("تعداد مسافران");
                }

                PassengerTypeDialog.this.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgroundButton();
                if (click){
                    if (onClickListener != null) {

                        onClickListener.onClickSelected(nameDetach);
                    }

                    PassengerTypeDialog.this.dismiss();
                }else {

                    Toast.makeText(getContext(), "تعداد مسافر را انتخاب کنید.", Toast.LENGTH_SHORT).show();
                }

            }
        });


        maxOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                oldCount++;
                tvOldCount.setText(String.valueOf(oldCount));
                backgroundButton();

            }
        });
        minOld.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (oldCount>=1){
                    oldCount--;
                    tvOldCount.setText(String.valueOf(oldCount));
                    backgroundButton();
                }

            }
        });

        maxYoung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                youngCount++;
                tvYoungCount.setText(String.valueOf(youngCount));
                backgroundButton();

            }
        });
        minYoung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (youngCount>=1){
                    youngCount--;
                    tvYoungCount.setText(String.valueOf(youngCount));
                    backgroundButton();
                }

            }
        });


        maxbaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                babyCount++;
                tvbabyCount.setText(String.valueOf(babyCount));
                backgroundButton();

            }
        });
        minbaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (babyCount>=1){
                    babyCount--;
                    tvbabyCount.setText(String.valueOf(babyCount));
                    backgroundButton();
                }

            }
        });


   /*     tvDialogDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {
                    onClickListener.onClickSelected(hours, minutes);
                }
                PassengerTypeDialog.this.dismiss();
            }
        });*/
    }

    public void showDialog() {
        this.show();
    }


    private void backgroundButton(){

        if (oldCount>0 || youngCount>0 || babyCount>0){
            click=true;
            btnOk.setBackgroundColor(Color.parseColor("#13b6ad"));
        }else {
            click=false;
            btnOk.setBackgroundColor(Color.parseColor("#c4c4c4"));
        }
        nameDetach="";
        if (oldCount>0){
            nameDetach= oldCount + " بزرگسال" ;
        }if (youngCount>0){
            nameDetach = youngCount + " کودک" ;
        }if (babyCount>0){
            nameDetach = babyCount + " خردسال" ;
        }if (oldCount>0 && youngCount>0 ){
            nameDetach= oldCount + " بزرگسال" +"/" + youngCount + " کودک" ;
        }if (youngCount>0 && babyCount>0){
            nameDetach=  youngCount + " کودک" +"/" +babyCount + " خردسال";
        }if (oldCount>0 && babyCount>0){
            nameDetach= oldCount + " بزرگسال" +"/" +babyCount + " خردسال";
        }
        if (oldCount>0 && youngCount>0 && babyCount>0){
            nameDetach= oldCount + " بزرگسال" +"/" + youngCount + " کودک" +"/" +babyCount + " خردسال";
        }
    }
}


