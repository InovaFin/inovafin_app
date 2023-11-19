// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SaldogGuardadoItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView checkbox;

  @NonNull
  public final ImageView checkboxSelected;

  @NonNull
  public final TextView exibirNomeReceber;

  @NonNull
  public final TextView exibirValorReceber;

  private SaldogGuardadoItemBinding(@NonNull LinearLayout rootView, @NonNull ImageView checkbox,
      @NonNull ImageView checkboxSelected, @NonNull TextView exibirNomeReceber,
      @NonNull TextView exibirValorReceber) {
    this.rootView = rootView;
    this.checkbox = checkbox;
    this.checkboxSelected = checkboxSelected;
    this.exibirNomeReceber = exibirNomeReceber;
    this.exibirValorReceber = exibirValorReceber;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SaldogGuardadoItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SaldogGuardadoItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.saldog_guardado_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SaldogGuardadoItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.checkbox;
      ImageView checkbox = ViewBindings.findChildViewById(rootView, id);
      if (checkbox == null) {
        break missingId;
      }

      id = R.id.checkbox_selected;
      ImageView checkboxSelected = ViewBindings.findChildViewById(rootView, id);
      if (checkboxSelected == null) {
        break missingId;
      }

      id = R.id.exibirNomeReceber;
      TextView exibirNomeReceber = ViewBindings.findChildViewById(rootView, id);
      if (exibirNomeReceber == null) {
        break missingId;
      }

      id = R.id.exibirValorReceber;
      TextView exibirValorReceber = ViewBindings.findChildViewById(rootView, id);
      if (exibirValorReceber == null) {
        break missingId;
      }

      return new SaldogGuardadoItemBinding((LinearLayout) rootView, checkbox, checkboxSelected,
          exibirNomeReceber, exibirValorReceber);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
