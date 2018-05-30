package io.github.ypsitau.examplesnackbar;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private Button button_popup;
	private EditText editText_text;
	private RadioGroup radioGroup_type;
	private RadioGroup radioGroup_duration;
	private RadioGroup radioGroup_gravityH;
	private RadioGroup radioGroup_gravityV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Activity activity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button_popup = findViewById(R.id.button_popup);
		editText_text = findViewById(R.id.editText_text);
		radioGroup_type = findViewById(R.id.radioGroup_type);
		radioGroup_duration = findViewById(R.id.radioGroup_duration);
		radioGroup_gravityH = findViewById(R.id.radioGroup_gravityH);
		radioGroup_gravityV = findViewById(R.id.radioGroup_gravityV);
		button_popup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (radioGroup_type.getCheckedRadioButtonId() == R.id.radioButton_type_snackbar) {
					popupSnackbar();
				} else {
					popupToast();
				}
			}
		});
	}

	private void popupSnackbar() {
		String text = editText_text.getText().toString();
		int duration = Snackbar.LENGTH_SHORT;
		if (radioGroup_duration.getCheckedRadioButtonId() == R.id.radioButton_duration_long) {
			duration = Snackbar.LENGTH_LONG;
		}
		Snackbar snackbar = Snackbar.make(findViewById(R.id.rootLayout), text, duration);

		snackbar.setAction("Accept", new View.OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});
		snackbar.show();
	}

	private void popupToast() {
		String text = editText_text.getText().toString();
		int duration = Toast.LENGTH_SHORT;
		int gravity = 0;
		if (radioGroup_duration.getCheckedRadioButtonId() == R.id.radioButton_duration_long) {
			duration = Toast.LENGTH_LONG;
		}
		if (radioGroup_gravityH.getCheckedRadioButtonId() == R.id.radioButton_gravityH_left) {
			gravity |= Gravity.LEFT;
		} else if (radioGroup_gravityH.getCheckedRadioButtonId() == R.id.radioButton_gravityH_right) {
			gravity |= Gravity.RIGHT;
		}
		if (radioGroup_gravityV.getCheckedRadioButtonId() == R.id.radioButton_gravityV_top) {
			gravity |= Gravity.TOP;
		} else if (radioGroup_gravityV.getCheckedRadioButtonId() == R.id.radioButton_gravityV_bottom) {
			gravity |= Gravity.BOTTOM;
		}
		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}
}
