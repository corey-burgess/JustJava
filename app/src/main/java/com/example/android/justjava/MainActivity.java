/**
 * IMPORTANT: Make sure you are using the correct package name. 
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.android.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    private int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameEditText = (EditText) findViewById(R.id.name_edit_text);
        String name = nameEditText.getText().toString();
        CheckBox whippedCheckBox = (CheckBox) findViewById(R.id.whipped_check_box);
        Boolean whipped = whippedCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_check_box);
        Boolean chocolate = chocolateCheckBox.isChecked();
        int price = calculatePrice(whipped,chocolate);
        String orderSummary = createOrderSummary(name,price,whipped,chocolate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for" + name );
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method is called to generate an order summary in text
     *
     * @param name of customer entered
     * @param price of order
     * @param whipped is whether or not the customer wants whipped cream
     * @param chocolate is whether or not the customer wants chocolate
     * @return text of order summary
     */
    private String createOrderSummary(String name,int price, boolean whipped, boolean chocolate) {
        String orderSummary = "Name: " + name
                + "\nAdd whipped cream? " + whipped
                + "\nAdd chocolate? " + chocolate
                + "\nQuantity: " + quantity
                + "\nTotal: $" + price
                + "\nThank you!";
        return orderSummary;
    }

    /**
     * This method increments quantity by 1
     */
    public void increment(View view) {
        if(quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        this.quantity++;
        displayQuantity(quantity);
    }

    /**
     * This method increments quantity by 1
     */
    public void decrement(View view) {
        if(quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        this.quantity--;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * Calculates the price of the order based on the current quantity.
     * @param whipped is whether or not the customer wants whipped cream
     * @param chocolate is whether or not the customer wants chocolate
     * @return the price
     */
    private int calculatePrice(Boolean whipped, Boolean chocolate) {
        int basePrice = 5;
        if(whipped){
            basePrice++;
        }
        if(chocolate){
            basePrice+=2;
        }
        int cost = basePrice * quantity;
        return cost;
    }
}