package com.dam.kiddo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.beautycoder.pflockscreen.PFFLockScreenConfiguration;
import com.beautycoder.pflockscreen.fragments.PFLockScreenFragment;
import com.beautycoder.pflockscreen.security.PFResult;
import com.beautycoder.pflockscreen.viewmodels.PFPinCodeViewModel;

public class PinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        showLockScreenFragment();
        //PFSecurityManager.getInstance().setPinCodeHelper(new com.dam.kiddo.TestPFPinCodeHelperImpl());
    }

    private final PFLockScreenFragment.OnPFLockScreenCodeCreateListener mCodeCreateListener =
            new PFLockScreenFragment.OnPFLockScreenCodeCreateListener() {
                @Override
                public void onCodeCreated(String encodedCode) {
                    Toast.makeText(PinActivity.this, "Code created", Toast.LENGTH_SHORT).show();
                    SharedPreferencesSettings.saveToPref(PinActivity.this, encodedCode);
                }

               //@Override
                public void onNewCodeValidationFailed() {
                    Toast.makeText(PinActivity.this, "Code validation error", Toast.LENGTH_SHORT).show();
                }
            };

    private final PFLockScreenFragment.OnPFLockScreenLoginListener mLoginListener =
            new PFLockScreenFragment.OnPFLockScreenLoginListener() {

                @Override
                public void onCodeInputSuccessful() {
                    Toast.makeText(PinActivity.this, "Code successfull", Toast.LENGTH_SHORT).show();
                    showMainFragment();
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                    startActivity(intent2);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                }

                @Override
                public void onFingerprintSuccessful() {
                    Toast.makeText(PinActivity.this, "Fingerprint successfull", Toast.LENGTH_SHORT).show();
                    showMainFragment();
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class); //REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                    startActivity(intent2);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                }

                @Override
                public void onPinLoginFailed() {
                    Toast.makeText(PinActivity.this, "Pin failed", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFingerprintLoginFailed() {
                    Toast.makeText(PinActivity.this, "Fingerprint failed", Toast.LENGTH_SHORT).show();
                }
            };

    private void showLockScreenFragment() {
        new PFPinCodeViewModel().isPinCodeEncryptionKeyExist().observe(
                this,
                new Observer<PFResult<Boolean>>() {
                    @Override
                    public void onChanged(@Nullable PFResult<Boolean> result) {
                        if (result == null) {
                            return;
                        }
                        if (result.getError() != null) {
                            Toast.makeText(PinActivity.this, "Can not get pin code info", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showLockScreenFragment(result.getResult());
                    }
                }
        );
    }

    private void showLockScreenFragment(boolean isPinExist) {
        final PFFLockScreenConfiguration.Builder builder = new PFFLockScreenConfiguration.Builder(this)
                .setTitle(isPinExist ? "Unlock with your pin code or fingerprint" : "Create Code")
                //.setTitle("Unlock")
                .setUseFingerprint(true)
                .setMode(PFFLockScreenConfiguration.MODE_AUTH)
                .setCodeLength(4); //min 4 ; max 6
                /*.setLeftButton("Can't remeber",
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                        //RECUPERAR PIN
                                Toast.makeText(PinActivity.this, "Left button pressed", Toast.LENGTH_LONG).show();
                            }
                        });*/
        final PFLockScreenFragment fragment = new PFLockScreenFragment();

       /*fragment.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PinActivity.this, "Left button pressed", Toast.LENGTH_LONG).show();
            }
        });*/

        builder.setMode(isPinExist
                ? PFFLockScreenConfiguration.MODE_AUTH
                : PFFLockScreenConfiguration.MODE_CREATE);
        if (isPinExist) {
            fragment.setEncodedPinCode(SharedPreferencesSettings.getCode(this));
            fragment.setLoginListener(mLoginListener);
        }

        fragment.setConfiguration(builder.build());
        fragment.setCodeCreateListener(mCodeCreateListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, fragment).commit();

    }

    private void showMainFragment() {
        final PinFragment fragment = new PinFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_view, fragment).commit();
    }
}
