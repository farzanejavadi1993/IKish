package ikish.aftab.ws.android.classes.calendarLibrary;






import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import ikish.aftab.ws.android.R;


public class ExtraditionDialog extends Dialog {

    private int oldCount=0;
    private int youngCount=0;
    private int babyCount=0;
    private String nameDetach="";
    private Boolean click=false;

    private MaterialButton btnOk;
    private ImageView btnClose;
    private CheckBox checkBoxCredit;
    private CheckBox checkBoxAccouningBank;
    private Boolean isCheckButton=false;



    public interface DialogCallback {
        void onClickSelected(String name);

        void onCancel();
    }

    private DialogCallback onClickListener;

    public ExtraditionDialog(Context context, String title, DialogCallback callback) {
        super(context);

        this.onClickListener = callback;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        getWindow().setGravity(Gravity.BOTTOM);
        setCanceledOnTouchOutside(false);

        initView();

        setListeners();


        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = this.getWindow();
        lp.copyFrom(window.getAttributes());

        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    private void initView() {
        setContentView(R.layout.extradition_dialog);

        btnOk = findViewById(R.id.btn_ok);
        btnClose = findViewById(R.id.btn_close_dialog_extradition);
        checkBoxCredit = findViewById(R.id.checkCredit);
        checkBoxAccouningBank = findViewById(R.id.checkAccountingBank);

    }

    private void setListeners() {
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickListener != null) {

                    onClickListener.onClickSelected("نحوه دریافت مبلغ استرداد بلیط");
                }

                ExtraditionDialog.this.dismiss();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isCheckButton){
                    if (onClickListener != null) {

                        onClickListener.onClickSelected(nameDetach);
                    }

                    ExtraditionDialog.this.dismiss();
                }else {

                    Toast.makeText(getContext(), "روش دریافت وجه را مشخص کنید.", Toast.LENGTH_SHORT).show();
                }

            }
        });

        checkBoxCredit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                checkBoxAccouningBank.setChecked(false);
                backgroundButton(isChecked);
            }
        });

        checkBoxAccouningBank.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                checkBoxCredit.setChecked(false);
                backgroundButton(isChecked);
            }
        });




    }

    public void showDialog() {
        this.show();
    }


    private void backgroundButton(boolean isCheck){
        if (isCheck){
            isCheckButton=true;
            btnOk.setBackgroundColor(getContext().getResources().getColor(R.color.topaz));
        }else {
            btnOk.setBackgroundColor(getContext().getResources().getColor(R.color.very_light_pink));
        }

    }


}


