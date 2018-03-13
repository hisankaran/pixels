package in.sankarank.pixels.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import in.sankarank.pixels.R;
import in.sankarank.pixels.misc.Utils;

public class CreatePasscodeActivity extends AppCompatActivity implements TextView.OnEditorActionListener {
    private static String TAG = "CreatePasscodeActivity";
    public static String INTENT_EXTRA_PASSCODE = "passcode";

    private EditText mPhoneNumberEntry;
    private long mPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_passcode);

        mPhoneNumberEntry = findViewById(R.id.editext_phone_number);
        mPhoneNumberEntry.setOnEditorActionListener(this);

        mPasscode = getPasscode();
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if(v.getId() == R.id.editext_phone_number && actionId == EditorInfo.IME_ACTION_DONE) {
            String phoneNumber = v.getText().toString().replaceAll("[^\\d]", "");
            if(phoneNumber.length() != 10) {
                Toast.makeText(this, R.string.invalid_phone_number, Toast.LENGTH_LONG).show();
            } else {
                showPasscode();
            }
            return true;
        }
        return false;
    }

    public void onNext(View v) {
        Intent intent = new Intent(this, PasscodeEntryActivity.class);
        intent.putExtra(INTENT_EXTRA_PASSCODE, Utils.md5(mPasscode));
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_leave);
        finish();
    }

    private long getPasscode() {
        Random r = new Random(System.currentTimeMillis());
        return ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
    }

    private void showPasscode() {
        TextView mPasscodeView = findViewById(R.id.passcode);
        View mPasscodeWrapper = findViewById(R.id.wrapper_passcode);

        mPhoneNumberEntry.setEnabled(false);
        mPasscodeView.setText(String.valueOf(mPasscode));
        mPasscodeWrapper.setVisibility(View.VISIBLE);
    }
}
