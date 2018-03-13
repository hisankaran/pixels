package in.sankarank.pixels.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import in.sankarank.pixels.R;
import in.sankarank.pixels.misc.Utils;
import in.sankarank.pixels.views.pinentry.PinEntryView;

import static in.sankarank.pixels.activities.CreatePasscodeActivity.INTENT_EXTRA_PASSCODE;

public class PasscodeEntryActivity extends AppCompatActivity implements PinEntryView.OnPinEnteredListener {
    private static String TAG = "CreatePasscodeActivity";

    private PinEntryView mPasscodeEntry;
    private String mPasscode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode_entry);

        mPasscode = getIntent().getStringExtra(INTENT_EXTRA_PASSCODE);
        if(mPasscode == null) {
            finish();
        }

        mPasscodeEntry = findViewById(R.id.passcode_entry);
        mPasscodeEntry.setOnPinEnteredListener(this);

        TextView mForgotPasscode = findViewById(R.id.label_forgot_passcode);
        mForgotPasscode.setPaintFlags(mForgotPasscode.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void onNext(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_leave);
        finish();
    }

    public void onForgotPasscode(View v) {
        Intent intent = new Intent(this, CreatePasscodeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_enter, R.anim.activity_leave);
        finish();
    }

    @Override
    public void onPinEntered(String pin) {
        String pinHash = Utils.md5(pin);
        if(pinHash == null) {
            Toast.makeText(this, R.string.passcode_verification_failure, Toast.LENGTH_LONG).show();
        } else if(pinHash.equals(mPasscode)) {
            onNext(null);
        } else {
            Toast.makeText(this, R.string.incorrect_passcode, Toast.LENGTH_LONG).show();
            mPasscodeEntry.setText("");
        }
    }
}
