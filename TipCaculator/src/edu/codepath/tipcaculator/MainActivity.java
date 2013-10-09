package edu.codepath.tipcaculator;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;
/*********************************************************************
 * <p>
 * TipCalcalator compute the tips from user input bill and percentage.
 * It has three short cut buttons <bold>(10%, 15%, 20%) </bold>
 * for quick calculator.I user can also specify the percentage of tip 
 * by using the SeekBar (slide bar) to dynamically see the computed
 * tip changes. The tip amount will also change dynamically according to
 * the bill.
 * 
 * @author Eric
 **********************************************************************/

public class MainActivity extends Activity {

	//private static String TAG = "TipCalculator";
	// ========= instance variables ===============
	/**
	 * The EditText for user to enter the bill amount to compute the tip.
	 */
	private EditText etBill = null;
	/**
	 * Slide to indicate the percentage of tip (1-20%)
	 */
	private SeekBar seekBarTip = null;
	/**
	 * EditText to display the tip amount. It is not editable.
	 */
	private EditText etTip = null;
	/**
	 * The TextView to display the tip in percentage enter by user.
	 */
	private TextView tvTipPercentage = null;
	// ============================================
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	//------------------------------------------------
	/**
	 * Handle button events from xml file.
	 * @param v
	 */
	public void handleEvents(View v)
	{
		int tipP = 0;
		switch(v.getId())
		{
			case R.id.btnP10:
				tipP =10;
				break;
			case R.id.btnP15:
				tipP =15;
				break;
			case R.id.btnP20:
				tipP =20;
				break;
		}	// switch(..)
		this.seekBarTip.setProgress(tipP);
	
	}	// handle event
	
	//=========================== Private ======================
	/**
	 * Retrieve the bill and Calculate the total tip.
	 * Update the tip EditText and and TextView tip percentage.
	 * 
	 * @param tip is the tip percentage to be used for calculation.
	 * @return returns true if successful compute and update the view components else return false.
	 */
	private boolean calcTip(int tip)
	{
		float bill = 0;
		try
		{
			bill = Float.parseFloat(this.etBill.getText().toString());			
		}
		catch(NumberFormatException e)
		{
			CharSequence text = this.getString(R.string.erEnterBill);
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(this.getApplicationContext(), text, duration);
			toast.show();
			return false;
			
		}
		float totalTip = bill*((float)tip/100);
		this.etTip.setText(String.format("$%.2f", totalTip));
		this.tvTipPercentage.setText(String.format("%d%%", tip));
		return true;
		
	}	// calcTip
	
	/**
	 * Initialized component. call by onCreate(..)
	 */
	private void init()
	{
		this.etBill = (EditText) this.findViewById(R.id.etBill);
		this.seekBarTip = (SeekBar) this.findViewById(R.id.seekBarTip);
		this.etTip = (EditText) this.findViewById(R.id.etTip);
		this.tvTipPercentage = (TextView) this.findViewById(R.id.tvTipPerc);
		
		// ==== init view values 
		this.etTip.setText("$0.00");
		this.tvTipPercentage.setText("0.00%");
	
		// ---- listeners =================
		// listen for SeekBar change
		this.seekBarTip.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				calcTip(progress);
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}});
		// ======= listen for bill change by user
			this.etBill.addTextChangedListener(new TextWatcher(){

				@Override
				public void afterTextChanged(Editable s) {
					// only interest in after text change
					calcTip(seekBarTip.getProgress());
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					
				}});
	}	// init()
	
}	// activity
