package com.example.myprofile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setContentView(R.layout.layout_landscape);
        } else {
            setContentView(R.layout.activity_main);
        }
        super.onConfigurationChanged(newConfig);
    }

    public void onClickPhoneNumber(View view) {
        String phoneNumber = ((Button)view).getText().toString();
        phoneNumber = standardizePhoneNumber(phoneNumber);
        Log.d("Phone Number", phoneNumber);
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    private String standardizePhoneNumber(String phoneNumber) {
        // Remove the prefix "Phone Number: "
        if (phoneNumber.startsWith("Phone Number: ")) {
            phoneNumber = phoneNumber.substring("Phone Number: ".length());
        }

        if (phoneNumber.startsWith("+84")) {
            phoneNumber = phoneNumber.replace("+84", "0");
        }

        // Remove any spaces in the phone number
        phoneNumber = phoneNumber.replace(" ", "");
        // Remove any hyphens in the phone number
        phoneNumber = phoneNumber.replace("-", "");

        return phoneNumber;

    }

    public void onClickPersonalEmail(View view) {
        String emailAddress = ((Button)view).getText().toString();
        emailAddress = standardizeEmailAddress(emailAddress);
//        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + emailAddress));
//        startActivity(intent);
        composeEmail(emailAddress, "Hello there!", null);
    }

    private String standardizeEmailAddress(String emailAddress) {
        // Remove the prefix "Email Address: "
        if (emailAddress.startsWith("Personal Email: ")) {
            emailAddress = emailAddress.substring("Personal Email: ".length());
        }
        emailAddress = emailAddress.replace(" ", "");
        return emailAddress;
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void composeEmail(String addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:" + addresses)); // Only email apps handle this.
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        if (attachment != null) {
            intent.putExtra(Intent.EXTRA_STREAM, attachment);
        }
        startActivity(intent);
        // Why resolveActivity didn't work?
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void onClickAddress(View view) {
        String address = ((Button)view).getText().toString();
        address = standardizeAddress(address);
        Log.d("Address", address);
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q=" + address));
        startActivity(intent);
    }

    public String standardizeAddress(String address) {
        // Remove the prefix "Address: "
        if (address.startsWith("Address: ")) {
            address = address.substring("Address: ".length());
        }
        address = address.replace(" ", "+");
        return address;
    }

    public void onClickClassIDToPortal(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://portal2.hcmus.edu.vn/Login.aspx?ReturnUrl=%2f"));
        startActivity(intent);
    }

    public void onClickAvatarToFacebookAccount(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/HonSam19999/"));
        startActivity(intent);
    }

    public void onClickDisplayExtraInfo(View view) {
        Button buttonDisplayView = (Button)findViewById(R.id.buttonDisplayExtraInfo);
        Spinner spinner = findViewById(R.id.spinnerInfo);
        String infoType = spinner.getSelectedItem().toString();
        String infoDetail = getInfo(infoType);
        buttonDisplayView.setText(infoDetail);
        switch (infoType) {
            case "ADDRESS":
                buttonDisplayView.setOnClickListener(this::onClickAddress);
                break;
            case "PHONE NUMBER":
                buttonDisplayView.setOnClickListener(this::onClickPhoneNumber);
                break;
            case "PERSONAL EMAIL":
                buttonDisplayView.setOnClickListener(this::onClickPersonalEmail);
                break;
            default:
                // No click listener for other info types
                break;
        }
        // getOnClickListener(infoType, buttonDisplayView);
    }

    private void getOnClickListener(String infoType, Button buttonDisplayView) {
        switch (infoType) {
            case "ADDRESS":
                buttonDisplayView.setOnClickListener(this::onClickAddress);
                break;
            case "PHONE NUMBER":
                buttonDisplayView.setOnClickListener(this::onClickPhoneNumber);
                break;
            case "PERSONAL EMAIL":
                buttonDisplayView.setOnClickListener(this::onClickPersonalEmail);
                break;
            default:
                // No click listener for other info types
                break;
        }
    }

    private String getInfo(@NonNull String infoType) {
        String infoDetail = "";
        switch (infoType) {
            case "DATE OF BIRTH":
                infoDetail = getString(R.string.date_of_birth);
                break;
            case "ADDRESS":
                infoDetail = getString(R.string.address);
                break;
            case "PHONE NUMBER":
                infoDetail = getString(R.string.phone_number);
                break;
            case "PERSONAL EMAIL":
                infoDetail = getString(R.string.personal_email);
                break;
        }
        return infoDetail;
    }
}