package com.ngangavic.test;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ngangavic.test.fragment.FragmentActivity;
import com.ngangavic.test.fragment.ScannerDialog;
import com.ngangavic.test.fragment.ScannerFragment;
import com.ngangavic.test.rv.RVActivity;
import com.ngangavic.test.sharedprefs.SharedPrefsActivity;
import com.ngangavictor.mpesa.stkpush.Settings;

import org.json.JSONException;

import java.io.IOException;

import static com.ngangavictor.mpesa.stkpush.Mpesa.verification;

public class MainActivity extends AppCompatActivity {
    Button buttonPolo;
    Button buttonScanner;
    Button buttonFragments;
    Button buttonStkPush;
    Button buttonBinary;
    Button button_rv;
    Button buttonSharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        buttonPolo = findViewById(R.id.buttonPolo);
        buttonScanner = findViewById(R.id.buttonScanner);
        buttonFragments = findViewById(R.id.buttonFragments);
        buttonStkPush = findViewById(R.id.buttonStkPush);
        buttonBinary=findViewById(R.id.buttonBinary);
        button_rv=findViewById(R.id.button_rv);
        buttonSharedPreference=findViewById(R.id.buttonSharedPreference);
        buttonPolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PoloActivity.class));
            }
        });

        buttonFragments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FragmentActivity.class));

            }
        });

        buttonStkPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
mpesa();
            }
        });

        buttonBinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, BinaryActivity.class));
            }
        });

        button_rv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RVActivity.class));
            }
        });
        buttonSharedPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SharedPrefsActivity.class));
            }
        });

        buttonScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openScanner();
//                bCodeReader();
            }

        });

    }

    private void openScanner() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
       Fragment fragmentPrev = getSupportFragmentManager().findFragmentById(R.id.scanner_layout);
       // Fragment fragmentPrev = getFragmentManager().findFragmentById(R.id.scanner_layout);
        if (fragmentPrev != null){
            fragmentTransaction.remove(fragmentPrev);
        }
        ScannerDialog scannerDialog = new ScannerDialog();
        scannerDialog.show(fragmentTransaction,"scanner");

    }

    private ScannerFragment bCodeReader() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ScannerFragment scannerFragment = new ScannerFragment();
        fragmentTransaction.replace(R.id.ac_main,scannerFragment);
        fragmentTransaction.commit();
        return scannerFragment;

    }

    private void mpesa(){
        Settings.setBusiness_short_code("174379");
        Settings.setCallback_url("http://www.yourcallbackurl.com");
        Settings.setConsumer_key("AL4cs1jYio03B97Bvri5SWaPsQ1upawY");
        Settings.setConsumer_secret("tIO5wyY43Gobzt6C");
        Settings.setPhone("254708374149");
        Settings.setStk_push_url("https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest");
        Settings.setTimeout_url("http://smartforex.co.ke/android/mpesa/timeout.php");
        Settings.setTransaction_desc("testing my api");
        Settings.setTransaction_type("CustomerPayBillOnline");
        Settings.setAmount("1");
        Settings.setAccess_token_url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials");
        Settings.setAccount_reference("vic10020");
        Settings.setPasskey("bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919");
        try {
            if (verification().equals("0")){
                Toast.makeText(MainActivity.this,"Success",Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
