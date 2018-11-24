package FormatFa.ApkRun;
import android.app.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.view.*;
import android.widget.*;
import android.widget.LinearLayout.*;
public class Fdialog extends Dialog {
	public Fdialog(Context context, int theme) {
		super(context, theme);
	}
	public Fdialog(Context context) {
		super(context);
	}

	
	public static class Builder  {
		private Context context;
		private String title;
		private String message;
		private String positiveButtonText;
		private String negativeButtonText;
		private String neutralButtonText;
		private View contentView;
		private View view;
		private Drawable icon;
		private Bitmap iconBitmap;
		private int iconResId = -1;
		private DialogInterface.OnClickListener
		positiveButtonClickListener,
		negativeButtonClickListener,
		neutralButtonClickListener;
		

		
		public Builder(Context context) {
		
			this.context = context;
			title = null;
			message = null;
			positiveButtonText = null;
			negativeButtonText = null;
			neutralButtonText = null;
			contentView = null;
			view = null;
			icon = null;
			iconBitmap = null;
			iconResId = -1;
			positiveButtonClickListener = null;
			negativeButtonClickListener = null;
			neutralButtonClickListener = null;
		}
		public Builder setMessage(String message) {
			this.message = message;
			return this;
		}
		public Builder setMessage(int message) {
			this.message = (String) context.getText(message);
			return this;
		}
		public Builder setTitle(int title) {
			this.title = (String) context.getText(title);
			return this;
		}
		public Builder setView(View view) {
			this.view = view;
			return this;
		}
		public Builder setIcon(Drawable icon) {
			this.icon = icon;
			return this;
		}
		public Builder setIcon(Bitmap iconBitmap) {
			this.iconBitmap = iconBitmap;
			return this;
		}
		public Builder setIcon(int iconResId) {
			this.iconResId = iconResId;
			return this;
		}
		public Builder setTitle(String title) {
			this.title = title;
			return this;
		}
		public Builder setContentView(View v) {
			this.contentView = v;
			return this;
		}
		public Builder setPositiveButton(int positiveButtonText,
										 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = (String) context
				.getText(positiveButtonText);
			this.positiveButtonClickListener = listener;
			return this;
		}
		public Builder setPositiveButton(String positiveButtonText,
										 DialogInterface.OnClickListener listener) {
			this.positiveButtonText = positiveButtonText;
			this.positiveButtonClickListener = listener;
			return this;
		}
		public Builder setNegativeButton(int negativeButtonText,
										 DialogInterface.OnClickListener listener) {
			this.negativeButtonText = (String) context
				.getText(negativeButtonText);
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setNegativeButton(String negativeButtonText,
										 DialogInterface.OnClickListener listener) {
			this.negativeButtonText = negativeButtonText;
			this.negativeButtonClickListener = listener;
			return this;
		}
		public Builder setNeutralButton(String negativeButtonText,
										DialogInterface.OnClickListener listener) {
			this.neutralButtonText = negativeButtonText;
			this.neutralButtonClickListener = listener;
			return this;
		}
		
		
		public Fdialog create() {
			LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
// instantiate the dialog with the custom Theme
			final Fdialog dialog = new Fdialog(context,
														 R.style.fdialog);
			View layout = inflater.inflate(R.layout.fdialog, null);
			dialog.addContentView(layout, new LayoutParams(
									  LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
									  //关闭
									  
									  Button close=(Button)layout.findViewById(R.id.fdialog_close);
			close.setOnClickListener(new View.OnClickListener(){

					@Override
					public void onClick(View p1)
					{
						dialog.cancel();
						// TODO: Implement this method
					}
				});
// set the dialog title
			((TextView) layout.findViewById(R.id.fdialog_title)).setText(title);
//set icon
			if(icon!=null){
				//((ImageView)(layout.findViewById(R.id.fdialog_icon))).setBackgroundDrawable(icon);
			}
			if(iconBitmap!=null){
				//((ImageView)(layout.findViewById(R.id.fdialog_icon))).setImageBitmap(iconBitmap);
			}
			if(iconResId!=-1){
				//((ImageView)(layout.findViewById(R.id.fdialog_icon))).setBackgroundResource(iconResId);
			}
// set the confirm button
			if (positiveButtonText != null) {
				((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
				
				
				if (positiveButtonClickListener != null) 
					{
					((Button) layout.findViewById(R.id.positiveButton)).setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								positiveButtonClickListener.onClick(
									dialog,
									DialogInterface.BUTTON_POSITIVE);
								dialog.cancel();
							}
						});
				}
				
				else{
					((Button) layout.findViewById(R.id.positiveButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								dialog.cancel();
							}
						});
				}
			} else {
// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.positiveButton).setVisibility(
					View.GONE);
			}
// set the cancel button
			if (negativeButtonText != null) {
				((Button) layout.findViewById(R.id.negativeButton))
					.setText(negativeButtonText);
				if (negativeButtonClickListener != null) {
					((Button) layout.findViewById(R.id.negativeButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								negativeButtonClickListener.onClick(
									dialog,
									DialogInterface.BUTTON_NEGATIVE);
								dialog.cancel();
							}
						});
				}else{
					((Button) layout.findViewById(R.id.negativeButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								dialog.cancel();
							}
						});
				}
			} else {
// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.negativeButton).setVisibility(
					View.GONE);
			}
// set the neutralButton button
			if ( neutralButtonText != null) {
				((Button) layout.findViewById(R.id.neutralButton))
					.setText(neutralButtonText);
				if (neutralButtonClickListener != null) {
					((Button) layout.findViewById(R.id.neutralButton))
						.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								neutralButtonClickListener.onClick(
									dialog,
									DialogInterface.BUTTON_NEGATIVE);
								dialog.cancel();
							}
						});
				}else{
					((Button) layout.findViewById(R.id.neutralButton))
						.setOnClickListener(new View.OnClickListener() { public void onClick(View v) {
								dialog.cancel();
							}
						});
				}
			} else {
// if no confirm button just set the visibility to GONE
				layout.findViewById(R.id.neutralButton).setVisibility(
					View.GONE);
			}
// set the content message
			if (message != null) {
				((LinearLayout)layout.findViewById(R.id.fdialog_content)).setVisibility(View.VISIBLE);
				( (TextView)( layout.findViewById(R.id.fdialog_message))).setText(message);
				((TextView)layout.findViewById(R.id.fdialog_message)).setVisibility(View.VISIBLE);
			} else{
				((TextView)layout.findViewById(R.id.fdialog_message)).setVisibility(View.GONE);
			}
//set view
			if(view!=null){
				((LinearLayout)layout.findViewById(R.id.fdialog_content)).setVisibility(View.VISIBLE);
				((LinearLayout)layout.findViewById(R.id.fdialog_content)).addView(view);
			}else if(message ==null){
				((LinearLayout)layout.findViewById(R.id.fdialog_content)).setVisibility(View.GONE);
			}
			if (contentView != null) {
// if no message set
// add the contentView to the dialog body
				((LinearLayout) layout.findViewById(R.id.fdialog_content))
					.removeAllViews();
				((LinearLayout) layout.findViewById(R.id.fdialog_content))
					.addView(contentView,
							 new LayoutParams(
								 LayoutParams.MATCH_PARENT,
								 LayoutParams.MATCH_PARENT));
			}
			dialog.setContentView(layout);
			return dialog;
		}
	}

	
	}
