package com.buildyourevent.buildyourevent.ui.userproducts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import com.buildyourevent.buildyourevent.R;
import com.buildyourevent.buildyourevent.model.constants.Codes;
import com.buildyourevent.buildyourevent.model.data.order.OrderRequest;
import com.buildyourevent.buildyourevent.model.data.order.OrderResponse;
import com.buildyourevent.buildyourevent.paymenthelper.FlipAnimator;
import com.buildyourevent.buildyourevent.paymenthelper.Utils;
import com.buildyourevent.buildyourevent.utils.SharedPrefMethods;
import com.buildyourevent.buildyourevent.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import co.paystack.android.PaystackSdk;
import co.paystack.android.model.Card;

public class PaymentActivity extends AppCompatActivity
{
    private static String TAG = "MainAkt";
    private Calendar calendar;
    private ImageView cardTypeView;
    private EditText dateEdit;
    private EditText nameEdit;
    private EditText numberEdit;
    private EditText cvvEdit;
    private TextView cardNumView;
    private TextSwitcher nameView,numberView,cvvView,dateView;
    private EditText[] editTexts;
    private TextSwitcher[] textSwitchers;
    private DatePickerDialog.OnDateSetListener date;
    private ViewGroup transitionsContainer;
    private ConstraintLayout cardFront,cardBack;
    private String cardNumber;
    private String cvv;
    private int expiryMonth;
    private int expiryYear;
    private static String MASTER = "master";
    private static String VERVE = "verve";
    private static String VISA = "visa";
    private String currentType = VISA;
    private int count = 0;

    OrderRequest orderRequest;
    UserViewModel userViewModel;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        SharedPrefMethods prefMethods = new SharedPrefMethods(this);
        Locale locale = new Locale(prefMethods.getUserLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.bind(this);
        //initialize the Paystack SDK
        PaystackSdk.initialize(this);

        orderRequest = (OrderRequest) getIntent().getSerializableExtra(Codes.ORDER_REQUEST);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getResources().getString(R.string.pay));*/
        //setTitle(getResources().getString(R.string.app_name));

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        setTitle(Html.fromHtml("<font color='#104BC2'>" + getResources().getString(R.string.app_name) + "</font>"));

        calendar = Calendar.getInstance();
        dateEdit = findViewById(R.id.date);
        nameEdit = findViewById(R.id.name);
        numberEdit = findViewById(R.id.number);
        cvvEdit = findViewById(R.id.cvv);

        cvvEdit.setOnFocusChangeListener(cvvFocus);

        cardFront = findViewById(R.id.card_front);
        cardBack = findViewById(R.id.card_back);

        cardNumView = findViewById(R.id.card_value);
        cardTypeView = findViewById(R.id.card_type);
        nameView = findViewById(R.id.card_holder);
        numberView = findViewById(R.id.card_number);
        cvvView = findViewById(R.id.card_cvv);
        dateView = findViewById(R.id.card_date);

        editTexts = new EditText[] {nameEdit,numberEdit,cvvEdit,dateEdit};
        textSwitchers = new TextSwitcher[] {nameView,numberView,cvvView,dateView};

        transitionsContainer = findViewById(R.id.card_section);

        //Add TextChangeListener to all the input fields
        for(EditText editText : editTexts){
            editText.addTextChangedListener(textWatcher(editText));
        }


        //Set Animation to all the text switchers in the card
        for(TextSwitcher textSwitcher : textSwitchers){
            textSwitcher.setInAnimation(this, R.anim.slide_up);
            textSwitcher.setOutAnimation(this, R.anim.slide_down);
        }

        //getDatePicker().findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                //view.findViewById(Resources.getSystem().getIdentifier("year", "id", "android")).setVisibility(View.GONE);

                // TODO Auto-generated method stub
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                //calendar.set(1, dayOfMonth);
                updateLabel();
            }

        };

        //new DatePickerDialog(getActivity(),android.R.style.Theme_Holo_Dialog, this, "20",month, day);
        //dialog.getDatePicker().findViewById(getResources().getIdentifier("day","id","android")).setVisibility(View.GONE);

        dateEdit.setOnClickListener(popUpCalender);

    }


    @OnClick(R.id.cardSubmit_button)
    void orderNow(View v)
    {
        userViewModel.confirmOrder(orderRequest).observe(this, new Observer<OrderResponse>()
        {
            @Override
            public void onChanged(OrderResponse orderResponse)
            {
                if (orderResponse.getStatus() == 200)
                {
                    Toast.makeText(PaymentActivity.this, "Order Done", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                    intent.putExtra(Codes.ORDER_REQUEST, orderRequest);
                    startActivity(intent);
                    finish();
                    Toast.makeText(PaymentActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

   /* @OnClick(R.id.cardcancel_button)
    void cancelOrder(View v)
    {
        Intent intent = new Intent(getApplicationContext(), CartsActivity.class);
        startActivity(intent);
        finish();
    }*/

    View.OnClickListener popUpCalender = new View.OnClickListener()
    {

        @Override
        public void onClick(View v)
        {
            // TODO Auto-generated method stub
            /*new DatePickerDialog(MainActivity.this, getDate(), getCalendar()
                    .get(Calendar.YEAR), getCalendar().get(Calendar.MONTH),
                    getCalendar().get(Calendar.DAY_OF_MONTH)).show();*/

            //if the cvv edit text currently has focus remove the focus, so that the card can turn to its front
            cvvEdit.clearFocus();

            new DatePickerDialog(PaymentActivity.this, getDate(), getCalendar()
                    .get(Calendar.YEAR), getCalendar().get(Calendar.MONTH),
                    getCalendar().get(Calendar.DAY_OF_MONTH)).show();
        }
    };


    private void updateLabel() {

        String myFormat = "MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateEdit.setText(sdf.format(calendar.getTime()));
    }

    /**
     * Declare the onFocus listener for the cvv input
     */
    View.OnFocusChangeListener cvvFocus = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View view, boolean hasFocus) {

            rotateCard(hasFocus);

        }
    };

    /**
     * method to turn the card if user is about to enter the cvv and vice-versa
     * @param isBackTurn set to TRUE if cvv has focus and vice-versa
     */
    private void rotateCard(boolean isBackTurn){
        /*if(isBackTurn){
            Log.d(TAG,"Got the focus");
        } else {
            Log.d(TAG,"Lost the focus");
        }*/

        if (isBackTurn) {
            //cardFront.setVisibility(View.GONE);
            //resetIconYAxis(holder.iconBack);
            //cardBack.setVisibility(View.VISIBLE);
            cardBack.setAlpha(1);
            FlipAnimator.flipView(getApplicationContext(), cardBack, cardFront, true);
            /*if (currentSelectedIndex == position) {
                FlipAnimator.flipView(getApplicationContext(), cardBack, cardFront, true);
                resetCurrentIndex();
            }*/
        } else {
            //cardBack.setVisibility(View.GONE);
            //resetIconYAxis(holder.iconFront);
            //cardFront.setVisibility(View.VISIBLE);
            cardFront.setAlpha(1);

            FlipAnimator.flipView(getApplicationContext(), cardBack, cardFront, false);

            /*if ((reverseAllAnimations && animationItemsIndex.get(position, false)) || currentSelectedIndex == position) {
                FlipAnimator.flipView(getApplicationContext(), cardBack, cardFront, false);
                resetCurrentIndex();
            }*/
        }

    }

    private TextWatcher textWatcher(final EditText editText){
        return new TextWatcher()
        {
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

                if(editText==numberEdit)
                {
                    setCardNumber();

                    TextView tv = (TextView) numberView.getCurrentView();

                    Log.d(TAG,"card val: "+tv.getText().toString());

                    numberView.setText(String.format("%s",s.length() > 0 ? s.toString() : getResources().getString(R.string.default_card_no)));

                    setCardTypeImg();

                }else if(editText==dateEdit)
                {
                    dateView.setText(String.format("%s",s.length() > 0 ? s : getResources().getString(R.string.date_hint)));
                    if(s.length() > 0)
                    {
                        String[] mthYr = s.toString().split("/");
                        setExpiryMonth(Integer.valueOf(mthYr[0]));
                        setExpiryYear(Integer.valueOf(mthYr[1]));
                    }


                }else if(editText==nameEdit){

                    nameView.setText(String.format("%s",s.length() > 0 ? s : getResources().getString(R.string.card_holder)));


                }else if(editText==cvvEdit){
                    cvvView.setText(String.format("%s",s.length() > 0 ? s : getResources().getString(R.string.cvv_hint)));
                    setCvv(s.toString());
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

                if(editText==numberEdit){
                    if (count <= editText.getText().toString().length()
                            &&(editText.getText().toString().length()==4
                            ||editText.getText().toString().length()==9
                            ||editText.getText().toString().length()==14
                            ||editText.getText().toString().length()==19)){
                        editText.setText(String.format("%s ",editText.getText().toString()));
                        int pos = editText.getText().length();
                        editText.setSelection(pos);
                    }else if (count >= editText.getText().toString().length()
                            &&(editText.getText().toString().length()==4
                            ||editText.getText().toString().length()==9
                            ||editText.getText().toString().length()==14
                            ||editText.getText().toString().length()==19)){
                        editText.setText(editText.getText().toString().substring(0,editText.getText().toString().length()-1));
                        int pos = editText.getText().length();
                        editText.setSelection(pos);
                    }
                    count = editText.getText().toString().length();
                }

            }
        };
    }

    /**
     * method to check card type
     * @return "master", "visa" or "verve"
     */
    private String checkCardType(){

        Card card = new Card(getCardNumber(), getExpiryMonth(), getExpiryYear(), getCvv());
        Log.d(TAG,"Type: "+card.getType()+" card no: "+getCardNumber());

        return card.getType();

    }

    private void setCardTypeImg(){
        if(checkCardType()!=null){
            String lowerCaseCardType = checkCardType().toLowerCase();
            if(lowerCaseCardType.contains(VISA)){
                Log.d(TAG,"visa");
                if(!currentType.equals(VISA)){
                    Utils.ImageViewAnimatedChange(getApplicationContext(),cardTypeView,getResources().getDrawable(R.drawable.visa_logo));
                }
                currentType = VISA;
            }else if(lowerCaseCardType.contains(MASTER)){
                Log.d(TAG,"master");


                if(!currentType.equals(MASTER)){
                    Utils.ImageViewAnimatedChange(getApplicationContext(),cardTypeView,getResources().getDrawable(R.drawable.mastercard_logo));
                }
                currentType = MASTER;
                //cardTypeView.setImageDrawable(getResources().getDrawable(R.drawable.mastercard_logo));
            }else if(lowerCaseCardType.contains(VERVE)){
                Log.d(TAG,"verve");
                //cardTypeView.setImageDrawable(getResources().getDrawable(R.drawable.verve_logo));
                if(!currentType.equals(VERVE)){
                    Utils.ImageViewAnimatedChange(getApplicationContext(),cardTypeView,getResources().getDrawable(R.drawable.verve_logo));
                }
                currentType = VERVE;

            }else{
                Log.d(TAG,"visa");
                if(!currentType.equals(VISA)){
                    Utils.ImageViewAnimatedChange(getApplicationContext(),cardTypeView,getResources().getDrawable(R.drawable.visa_logo));
                }
                currentType = VISA;
            }
        }
    }


    public Calendar getCalendar() {
        return calendar;
    }

    public DatePickerDialog.OnDateSetListener getDate() {
        return date;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber() {

        String cardNo = numberEdit.getText().toString().replace(" ", "");

        Log.d(TAG,"CN: "+cardNo);

        //make sure the card number maintains minimum of 16 digits length
        this.cardNumber = String.format("%-16s", cardNo ).replace(' ', '0');
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }
}
