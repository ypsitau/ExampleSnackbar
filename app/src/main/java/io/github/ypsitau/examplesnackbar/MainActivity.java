package io.github.ypsitau.examplesnackbar;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private ConstraintLayout rootLayout;
	private EditText editText_text;
	private RadioGroup radioGroup_type;
	private RadioGroup radioGroup_duration;
	private CheckBox checkBox_action;
	private EditText editText_action;
	private RadioGroup radioGroup_gravityH;
	private RadioGroup radioGroup_gravityV;
	private FloatingActionButton floatingActionButton_popup;
	private ViewGroup layout_settingsForSnackbar;
	private ViewGroup layout_settingsForToast;
	private EditText editText_log;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		final Activity activity = this;
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		rootLayout = findViewById(R.id.rootLayout);
		editText_text = findViewById(R.id.editText_text);
		radioGroup_type = findViewById(R.id.radioGroup_type);
		radioGroup_duration = findViewById(R.id.radioGroup_duration);
		checkBox_action = findViewById(R.id.checkBox_action);
		editText_action = findViewById(R.id.editText_action);
		radioGroup_gravityH = findViewById(R.id.radioGroup_gravityH);
		radioGroup_gravityV = findViewById(R.id.radioGroup_gravityV);
		floatingActionButton_popup = findViewById(R.id.button_popup);
		layout_settingsForSnackbar = findViewById(R.id.layout_settingsForSnackbar);
		layout_settingsForToast = findViewById(R.id.layout_settingsForToast);
		editText_log = findViewById(R.id.editText_log);
		LayoutTransition layoutTransition = new LayoutTransition();
		rootLayout.setLayoutTransition(layoutTransition);
		radioGroup_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				updateViewStatus();
			}
		});
		checkBox_action.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				updateViewStatus();
			}
		});
		editText_log.setOnLongClickListener(new View.OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				editText_log.setText("");
				return true;
			}
		});
		floatingActionButton_popup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				int id = radioGroup_type.getCheckedRadioButtonId();
				if (id == R.id.radioButton_type_snackbar) {
					popupSnackbar();
				} else { // if (id == R.id.radioButton_type_toast)
					popupToast();
				}
			}
		});
		updateViewStatus();
	}

	private void updateViewStatus() {
		int id = radioGroup_type.getCheckedRadioButtonId();
		if (id == R.id.radioButton_type_snackbar) {
			layout_settingsForToast.setVisibility(View.GONE);
			layout_settingsForSnackbar.setVisibility(View.VISIBLE);
		} else { // if (id == R.id.radioButton_type_toast)
			layout_settingsForSnackbar.setVisibility(View.GONE);
			layout_settingsForToast.setVisibility(View.VISIBLE);
		}
		editText_action.setEnabled(checkBox_action.isChecked());
	}
	private static void setEnabledOnChildren(View view, boolean enabled) {
		if (view instanceof ViewGroup) {
			ViewGroup viewGroup = (ViewGroup)view;
			for (int i = 0; i < viewGroup.getChildCount(); i++) {
				setEnabledOnChildren(viewGroup.getChildAt(i), enabled);
			}
		} else {
			view.setEnabled(enabled);
		}
	}
	private void popupSnackbar() {
		String text = editText_text.getText().toString();
		int duration;
		int id = radioGroup_duration.getCheckedRadioButtonId();
		if (id == R.id.radioButton_duration_long) {
			duration = Snackbar.LENGTH_LONG;
		} else { // if (id == R.id.radioButton_duration_short)
			duration = Snackbar.LENGTH_SHORT;
		}
		Snackbar snackbar = Snackbar.make(rootLayout, text, duration);
		if (checkBox_action.isChecked()) {
			String textAction = editText_action.getText().toString();
			snackbar.setAction(textAction, new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					printf("action clicked\n");
				}
			});
		}
		snackbar.show();
	}

	private void popupToast() {
		String text = editText_text.getText().toString();
		int duration;
		int id = radioGroup_duration.getCheckedRadioButtonId();
		if (id == R.id.radioButton_duration_long) {
			duration = Toast.LENGTH_LONG;
		} else { // if (id == R.id.radioButton_duration_short)
			duration = Toast.LENGTH_SHORT;
		}
		int gravity = 0;
		id = radioGroup_gravityH.getCheckedRadioButtonId();
		if (id == R.id.radioButton_gravityH_left) {
			gravity |= Gravity.LEFT;
		} else if (id == R.id.radioButton_gravityH_right) {
			gravity |= Gravity.RIGHT;
		} else { // if (id == R.id.radioButton_gravityH.center)
			// nothing to do
		}
		id = radioGroup_gravityV.getCheckedRadioButtonId();
		if (id == R.id.radioButton_gravityV_top) {
			gravity |= Gravity.TOP;
		} else if (id == R.id.radioButton_gravityV_bottom) {
			gravity |= Gravity.BOTTOM;
		} else { // if (id == R.id.radioButton_gravityV.center)
			// nothing to do
		}
		Toast toast = Toast.makeText(getApplicationContext(), text, duration);
		toast.setGravity(gravity, 0, 0);
		toast.show();
	}

	public void printf(String format, Object... args) {
		editText_log.append(String.format(format, args));
		editText_log.setSelection(editText_log.getText().length());
	}
}
