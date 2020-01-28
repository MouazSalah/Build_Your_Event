package com.buildyourevent.buildyourevent.paymenthelper;

import android.content.Context;

public class CopyToClipboard {

    private String label;
    private String text;
    private String success;
    private Context context;

    public CopyToClipboard label(String label){
        this.label = label;
        return this;
    }

    public CopyToClipboard text(String text){
        this.text = text;
        return this;
    }

    public CopyToClipboard successMessage(String success){
        this.success = success;
        return this;
    }

    /*
    public void copy(){
        Context context = MySearch.getInstance().getApplicationContext();
        // Gets a handle to the clipboard service.
        ClipboardManager clipboard = (ClipboardManager)
                context.getSystemService(Context.CLIPBOARD_SERVICE);
        // Creates a new text clip to put on the clipboard
        ClipData clip = ClipData.newPlainText(label, text);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clip);
            Toast.makeText(context,success,Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public String getCopiedText(){
        String textToPaste = "";

        Context context = MySearch.getInstance().getApplicationContext();
        ClipboardManager clipboard = (ClipboardManager)context.getSystemService(Context.CLIPBOARD_SERVICE);

        if (clipboard.hasPrimaryClip()) {
            ClipData clip = clipboard.getPrimaryClip();

            // if you need text data only, use:
            if (clip.getDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))
                // WARNING: The item could cantain URI that points to the text data.
                // In this case the getText() returns null and this code fails!
                textToPaste = clip.getItemAt(0).getText().toString();

            // or you may coerce the data to the text representation:
            textToPaste = clip.getItemAt(0).coerceToText(context).toString();
        }

        return textToPaste;
    }
*/

}
