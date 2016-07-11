package com.example.android.myapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    boolean isTopping = false;
    boolean isChocTopping = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View view) {
        int price  = calcPrice(quantity);

        String priceMessage = createOrderSummary(price);

        //displayMessage(priceMessage);
        String subject = "My Coffee Order";
        Intent mailIntent = new Intent(Intent.ACTION_SENDTO);
        mailIntent.setData(Uri.parse("mailto:"));
        //mailIntent.setType("text/plain");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, "roopa.nadig12@gmail.com");
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mailIntent.putExtra(Intent.EXTRA_TEXT,priceMessage);

        //mailIntent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (mailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mailIntent);
        }
    }

    private int calcPrice(int quantity)
    {
        return quantity * 5;
    }

    private String createOrderSummary(int price)
    {

        EditText name = (EditText)findViewById(R.id.name_id);
        Editable customerNamme = name.getText();
        String priceMessage = "Name : " + customerNamme;
        priceMessage += "\nQuantity : " + quantity;
        priceMessage += "\nAdd whipped cream?  " + isTopping;
        priceMessage += "\nAdd Chocolate?  " + isChocTopping;
        if (isTopping)
            price += 1;
        if (isChocTopping)
            price += 2;
        priceMessage += "\nTotal: $" + price + "\nThank you!";

        return priceMessage;

    }

    public boolean onCheckBoxClick(View view)
    {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.toppings_check_box);
        isTopping = whippedCreamCheckBox.isChecked();

        return isTopping;

    }

    public boolean onCheckBoxChoc(View view)
    {
        CheckBox chocCheckBox = (CheckBox) findViewById(R.id.choctoppings_check_box);
        isChocTopping = chocCheckBox.isChecked();

        return isChocTopping;

    }
    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(MainActivity.this, "You cannot order more than 100 coffees!!",
                    Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity + 1;
        display(quantity);

    }

    public void decrement(View view) {

        if(quantity == 1) {
            Toast.makeText(MainActivity.this, "You cannot order less than 1 coffee!!",
                    Toast.LENGTH_SHORT).show();

            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }


    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    private void displayMessage(String message){
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);

    }

}
