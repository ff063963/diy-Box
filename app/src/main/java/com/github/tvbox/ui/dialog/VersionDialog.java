package com.github.tvbox.ui.dialog;

import android.content.Context;

import androidx.annotation.NonNull;

import com.github.tvbox.R;

import org.jetbrains.annotations.NotNull;

public class VersionDialog extends BaseDialog {

    public VersionDialog(@NonNull @NotNull Context context) {
        super(context);
        setContentView(R.layout.dialog_version);
    }
}
