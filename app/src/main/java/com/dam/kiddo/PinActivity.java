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
                    Toast.makeText(PinActivity.this, getString(R.string.pin_defined), Toast.LENGTH_SHORT).show();
                    SharedPreferencesSettings.saveToPref(PinActivity.this, encodedCode);
                    showLockScreenFragment();
                }

               //@Override
                public void onNewCodeValidationFailed() {
                    Toast.makeText(PinActivity.this, getString(R.string.code_definition_error), Toast.LENGTH_SHORT).show();
                }
            };

    private final PFLockScreenFragment.OnPFLockScreenLoginListener mLoginListener =
            new PFLockScreenFragment.OnPFLockScreenLoginListener() {

                @Override
                public void onCodeInputSuccessful() {
                    Toast.makeText(PinActivity.this, getString(R.string.code_success_welcome), Toast.LENGTH_SHORT).show();
                    showMainFragment();
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                    startActivity(intent2);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                }

                @Override
                public void onFingerprintSuccessful() {
                    Toast.makeText(PinActivity.this, getString(R.string.fingerprint_welcome), Toast.LENGTH_SHORT).show();
                    showMainFragment();
                    Intent intent2 = new Intent(getApplicationContext(), MainActivity.class); //REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                    startActivity(intent2);//REMOVER DEPOIS DE IMPLEMENTAR LAUNCHER
                }

                @Override
                public void onPinLoginFailed() {
                    Toast.makeText(PinActivity.this, getString(R.string.pin_failed), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFingerprintLoginFailed() {
                    Toast.makeText(PinActivity.this, getString(R.string.fingerprint_failed), Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(PinActivity.this, getString(R.string.can_not_get_pin_info), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        showLockScreenFragment(result.getResult());
                    }
                }
        );
    }

    private void showLockScreenFragment(boolean isPinExist) {
        final PFFLockScreenConfiguration.Builder builder = new PFFLockScreenConfiguration.Builder(this)
                .setTitle(isPinExist ? "Desloquear com PIN ou Impressão digital" : "Definir PIN")
                //.setTitle("Desbloquear")
                .setUseFingerprint(true)
                .setMode(PFFLockScreenConfiguration.MODE_AUTH)
                .setCodeLength(4) //min 4 ; max 6
                .setLeftButton(getString(R.string.recover_PIN));
        final PFLockScreenFragment fragment = new PFLockScreenFragment();

       fragment.setOnLeftButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PinActivity.this, "Left button pressed", Toast.LENGTH_LONG).show();
                //ABRIR ACTIVITY PARA RECUPERAR PIN através de SMS
            }
        });

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

    @Override
    public void onBackPressed(){}

}
