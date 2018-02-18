package lu.kremi151.sushilist.util;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.EditText;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import lu.kremi151.sushilist.Callback;
import lu.kremi151.sushilist.R;
import lu.kremi151.sushilist.adapter.SushiReferenceAdapter;

/**
 * Created by michm on 18.02.2018.
 */

public class DialogHelper {

    public static AlertDialog buildLoadDialog(Context context, final List<SushiListReference> referenceList, final Callback<SushiListReference> listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.dialogTitleLoadList);
        builder.setAdapter(
                new SushiReferenceAdapter((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE), referenceList),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.callback(referenceList.get(i));
                        dialogInterface.dismiss();
                    }
                });
        builder.setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    public static AlertDialog buildMessageDialog(Context context, int titleRes, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes);
        builder.setMessage(message);
        builder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    public static AlertDialog buildErrorDialog(Context context, Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);
        return buildMessageDialog(context, R.string.dialogTitleError, sw.toString());
    }

    public static AlertDialog buildInputDialog(Context context, int titleRes, final Callback<String> listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes);
        final EditText textView = new EditText(context);
        builder.setView(textView);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.callback(textView.getText().toString());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

}
