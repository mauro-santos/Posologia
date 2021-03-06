package br.com.maurosantos.android.posologia.app;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by Mauro on 12/11/2016.
 */

public class MessageBox {
    public static void showInfo(Context context, String title, String message) {
        show(context, title, message, android.R.drawable.ic_dialog_info);
    }

    public static void showAlert(Context context, String title, String message) {
        show(context, title, message, android.R.drawable.ic_dialog_alert);
    }

    public static void show(Context context, String title, String message, int iconId) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setIcon(iconId);
        dlg.setTitle(title);
        dlg.setMessage(message);
        dlg.setNeutralButton("OK", null);
        dlg.show();
    }
}
