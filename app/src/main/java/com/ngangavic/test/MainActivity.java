package com.ngangavic.test;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
//import com.ngangavic.test.conextmenutest.TestActivity;
import com.ngangavic.test.bottomnav.BottomNavigationActivity;
import com.ngangavic.test.contextmenu.ContextActivity;
import com.ngangavic.test.download.PDFDownloadActivity;
import com.ngangavic.test.fragment.FragmentActivity;
import com.ngangavic.test.fragment.ScannerDialog;
import com.ngangavic.test.fragment.ScannerFragment;
import com.ngangavic.test.fancyprogress.LightProgressActivity;
import com.ngangavic.test.jobscheduler.JobSchedulerActivity;
import com.ngangavic.test.maps.MapsActivity;
import com.ngangavic.test.notifications.NotificationActivity;
import com.ngangavic.test.recorder.RecorderActivity;
import com.ngangavic.test.rv.RVActivity;
import com.ngangavic.test.service.ServicesActivity;
import com.ngangavic.test.sharedprefs.SharedPrefsActivity;
import com.ngangavic.test.toast.ToastActivity;
import com.ngangavic.test.webview.WebViewActivity;
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
    Button buttonConnection;
    Button buttonWebView;
    Button buttonPesaPal;
    ConstraintLayout ac_main;
    Button buttonRecorder;
    Button buttonService;
    Button buttonTheme;
    Button buttonMaps;
    Button buttonDownloadPdf;
    Button buttonContextMenu;
    Button buttonLightProgress;
    Button buttonToast;
    Button buttonNotifications;
    Button buttonBottomNav;
    Button buttonJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
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
        buttonBinary = findViewById(R.id.buttonBinary);
        button_rv = findViewById(R.id.button_rv);
        buttonSharedPreference = findViewById(R.id.buttonSharedPreference);
        buttonConnection = findViewById(R.id.buttonConnection);
        buttonWebView = findViewById(R.id.buttonWebView);
        buttonPesaPal = findViewById(R.id.buttonPesaPal);
        buttonRecorder = findViewById(R.id.buttonRecorder);
        buttonService = findViewById(R.id.buttonService);
        buttonTheme = findViewById(R.id.buttonTheme);
        ac_main = findViewById(R.id.ac_main);
        buttonMaps = findViewById(R.id.buttonMaps);
        buttonDownloadPdf = findViewById(R.id.buttonDownloadPdf);
        buttonContextMenu = findViewById(R.id.buttonContextMenu);
        buttonLightProgress = findViewById(R.id.buttonLightProgress);
        buttonToast = findViewById(R.id.buttonToast);
        buttonNotifications = findViewById(R.id.buttonNotifications);
        buttonBottomNav = findViewById(R.id.buttonBottomNav);
        buttonJobScheduler = findViewById(R.id.buttonJobScheduler);

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

        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager ConnectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = ConnectionManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected() == true) {
                    Snackbar.make(ac_main, "Connected to Internet", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                } else {
                    Snackbar.make(ac_main, "Not connected to Internet", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                }
            }
        });

        buttonWebView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, WebViewActivity.class));
            }
        });

        buttonPesaPal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.0.101/www.android.com/pesapal/action.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("PESAPAL", response);
                                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(response));
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setPackage("com.android.chrome");
                                try {
                                    startActivity(i);
                                } catch (ActivityNotFoundException e) {
                                    i.setPackage(null);
                                    startActivity(i);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

                queue.add(stringRequest);
            }
        });

        buttonRecorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RecorderActivity.class));
            }
        });

        buttonService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ServicesActivity.class));
            }
        });

        buttonTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
            }
        });

        buttonMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });

        buttonDownloadPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PDFDownloadActivity.class));
            }
        });

        buttonContextMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ContextActivity.class));
            }
        });

        buttonLightProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LightProgressActivity.class));
            }
        });

        buttonToast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ToastActivity.class));
            }
        });

        buttonNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NotificationActivity.class));
            }
        });

        buttonBottomNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, BottomNavigationActivity.class));
            }
        });

        buttonJobScheduler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, JobSchedulerActivity.class));
            }
        });

    }

    private void openScanner() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        Fragment fragmentPrev = getSupportFragmentManager().findFragmentById(R.id.scanner_layout);
        // Fragment fragmentPrev = getFragmentManager().findFragmentById(R.id.scanner_layout);
        if (fragmentPrev != null) {
            fragmentTransaction.remove(fragmentPrev);
        }
        ScannerDialog scannerDialog = new ScannerDialog();
        scannerDialog.show(fragmentTransaction, "scanner");

    }

    private ScannerFragment bCodeReader() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        ScannerFragment scannerFragment = new ScannerFragment();
        fragmentTransaction.replace(R.id.ac_main, scannerFragment);
        fragmentTransaction.commit();
        return scannerFragment;

    }

    private void mpesa() {
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
            if (verification().equals("0")) {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
