package in.basulabs.shakealarmclock.frontend;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import in.basulabs.shakealarmclock.R;
import in.basulabs.shakealarmclock.backend.ConstantsAndStatics;
import in.basulabs.shakealarmclock.backend.Service_SetAlarmsPostBoot;

public class Activity_RequestPermIntro extends AppCompatActivity {

	@RequiresApi(api = Build.VERSION_CODES.S)
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_req_alarm_perm);
		setSupportActionBar(findViewById(R.id.toolbar3));
		setTitle(R.string.grant_permission);

		TextView permGiven = findViewById(R.id.permGivenTextView);
		permGiven.setVisibility(View.GONE);

		ConstraintLayout permsRequestLayout = findViewById(R.id.constraintLayout10);

		ActivityResultLauncher<Intent> permsActLauncher = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(), (result) -> {

				if (result.getResultCode() == RESULT_CANCELED) {
					Toast.makeText(this, "Alarms won't work without those " +
						"permissions!!", Toast.LENGTH_LONG).show();
				} else {
					permsRequestLayout.setVisibility(View.GONE);
					permGiven.setVisibility(View.VISIBLE);

					Intent intent = new Intent(this, Service_SetAlarmsPostBoot.class);
					ContextCompat.startForegroundService(getApplicationContext(),
						intent);
				}

			});

		Button grantPerm = findViewById(R.id.grant_perm_btn);

		grantPerm.setOnClickListener(view -> {
			Intent intent = new Intent(this, Activity_ListReqPerm.class);
			intent.putStringArrayListExtra(ConstantsAndStatics.EXTRA_PERMS_REQUESTED,
				ConstantsAndStatics.getEssentialPerms(this));
			permsActLauncher.launch(intent);
		});
	}

}
