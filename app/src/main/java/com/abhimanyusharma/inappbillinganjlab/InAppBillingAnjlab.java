package com.abhimanyusharma.inappbillinganjlab;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;

public class InAppBillingAnjlab extends AppCompatActivity implements BillingProcessor.IBillingHandler{


    private static final String TAG = "InAppBilling";

    static final String ITEM_SKU = "android.test.purchased";
    //static final String ITEM_SKU = "com.inappbillinganjlab.pro";
    //final String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqJUNhWfqOdIW+isT3MlF/xMCyij5ie+P9drmsGZT53AtdT/+jywTeYQ1KZ3KT75xWpO9IXx5BhhrS3rMk8VyiMcPAhXmRcKimt2wuKN0T/6MmRE/rnPmdjFcjAaXCF5mmCWfFSFFfTc9NGwkG4COr8Y+xAumlJUs6hMHF1xFPjjTE1tMn0qAIIt27iu/hdP1LrZX/kTd3bsYKRpfIEhteaYmODWn3R0yR84C/spEebzPFZ05Ym673++Y5cUnI6vpA237EnpnziKlrB7YlhQbsuKR3Bqu0/n2USxi5igQLpyn6kUp6xgBZaL3q/bocuaflkj7kaI1xNDzczKxvDWWqQIDAQAB";
    final String base64EncodedPublicKey = "null";

    private Button clickButton;
    private Button buyButton;
    private Button button;
    BillingProcessor bp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_app_billing_anjlab);

        buyButton = (Button)findViewById(R.id.buyButton);
        clickButton = (Button)findViewById(R.id.clickButton);
        button = (Button)findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "IN APP PURCHASE ANJLAB", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), InAppBillingActivity.class));
                //finish();
            }
        });

        //String base64EncodedPublicKey = "<place your public key here>";

        clickButton.setEnabled(false);

        //bp.loadOwnedPurchasesFromGoogle();

        //bp = new BillingProcessor(this, "YOUR LICENSE KEY FROM GOOGLE PLAY CONSOLE HERE", this);
        bp = new BillingProcessor(this, base64EncodedPublicKey, this);
        //bp = new BillingProcessor(this, null, this);



    }

    public void buttonClicked (View view)
    {

        Toast.makeText(getApplicationContext(), "PURCHASE COMPLETE", Toast.LENGTH_SHORT).show();

    }

    public void buyClick(View view) {

        Toast.makeText(getApplicationContext(), "PURCHASE START", Toast.LENGTH_SHORT).show();
        bp.purchase(InAppBillingAnjlab.this, ITEM_SKU);
    }

    @Override
    public void onProductPurchased(@NonNull String productId, @Nullable TransactionDetails details) {

        Toast.makeText(getApplicationContext(), "PURCHASE COMPLETE", Toast.LENGTH_SHORT).show();
        clickButton.setEnabled(true);
        buyButton.setEnabled(false);

    }

    @Override
    public void onPurchaseHistoryRestored() {
        clickButton.setEnabled(true);
        //buyButton.setEnabled(false);
    }

    @Override
    public void onBillingError(int errorCode, @Nullable Throwable error) {

        Toast.makeText(getApplicationContext(), "PURCHASE ERROR", Toast.LENGTH_SHORT).show();
        clickButton.setEnabled(false);
        buyButton.setEnabled(true);
    }

    @Override
    public void onBillingInitialized() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!bp.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (bp != null) {
            bp.release();
        }
        super.onDestroy();
    }
}
