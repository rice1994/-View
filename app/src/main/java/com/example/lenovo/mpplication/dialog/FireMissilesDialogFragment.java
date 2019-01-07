package com.example.lenovo.mpplication.dialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.Toast;
import com.example.lenovo.mpplication.R;

/**
 * Created by fan on 2018/8/17.
 */
public class FireMissilesDialogFragment extends DialogFragment {

	@NonNull
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		builder.setTitle("今日主题")
			//	.setMessage("2018/08/17 情人节快乐！！")
				.setItems(R.array.colors_array, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				})
				.setMultiChoiceItems(R.array.colors_array, null,
						new DialogInterface.OnMultiChoiceClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which,
									boolean isChecked) {
//								if (isChecked) {
//									// If the user checked the item, add it to the selected items
//									mSelectedItems.add(which);
//								} else if (mSelectedItems.contains(which)) {
//									// Else, if the item is already in the array, remove it
//									mSelectedItems.remove(Integer.valueOf(which));
//								}
							}
						})
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getContext(), "确定按钮点击啦！", Toast.LENGTH_SHORT).show();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getContext(), "取消按钮点击啦！", Toast.LENGTH_SHORT).show();
					}
				})
				.setNeutralButton("中立", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(getContext(), "取消中立点击啦！", Toast.LENGTH_SHORT).show();
					}
				});
		return builder.create();

	}
}
