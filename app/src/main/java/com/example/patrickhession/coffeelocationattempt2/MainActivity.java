package com.example.patrickhession.coffeelocationattempt2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    int quantity = 0;

    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
        displayPrice(quantity * 5);
    }

    public void decrement(View view) {
        if (quantity < 1) {
            display(quantity);
            displayPrice(quantity);
        } else {
            quantity = quantity - 1;
            display(quantity);
            displayPrice(quantity * 5);
        }
    }

    private int calculatePrice(boolean addChocolate, boolean addWhippedCream){
        int basePrice = 5;

        if(addChocolate){
            basePrice = basePrice + 1;
        }

        if(addWhippedCream){
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    public String createOrderSummary(boolean addChocolate, boolean addWhippedCream) {
        TextView strName, secondName;
        String secondName1, strName1, priceMessage;

        strName = (TextView) findViewById(R.id.edit_message);
        secondName = (TextView) findViewById(R.id.edit_message1);

        strName1 = strName.getText().toString();
        secondName1 = secondName.getText().toString();

        priceMessage = "First Name: " + strName1 + "\r\nSecond Name: " + secondName1 + "\r\nTotal: $" + calculatePrice(addChocolate, addWhippedCream) + "\r\nDo you want whipped cream? " + addWhippedCream;
        priceMessage += "\r\nDo you want chocolate? " + addChocolate + "\r\nQuantity: " + quantity + "\r\nThank you!!";

        return priceMessage;
    }

    public void submitOrder(View view) {

        TextView strName, secondName;
        String secondName1, strName1;

        strName = (TextView) findViewById(R.id.edit_message);
        secondName = (TextView) findViewById(R.id.edit_message1);

        strName1 = strName.getText().toString();
        secondName1 = secondName.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedCream);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        String priceMessage = createOrderSummary(hasChocolate, hasWhippedCream);
        Intent intentFirst = new Intent(Intent.ACTION_SENDTO);
        intentFirst.setData(Uri.parse("mailto:"));
        intentFirst.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + strName1 + " " + secondName1);
        intentFirst.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intentFirst.resolveActivity(getPackageManager()) != null) {
            startActivity(intentFirst);
        }
    }

    public void location(View view){
        Intent intent = new Intent(Intent.ACTION_VIEW);

            intent.setData(Uri.parse("geo: 47.6345, -122.3345"));
            if(intent.resolveActivity(getPackageManager()) != null){
                startActivity(intent);
            }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_TextView);
        quantityTextView.setText("" + number);
    }

    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.orderSummary_TextView);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
}

