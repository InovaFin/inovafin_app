// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public final class ValorPagarItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView exibirDataPagar;

  @NonNull
  public final TextView exibirNomePagar;

  @NonNull
  public final TextView exibirValorPagar;

  private ValorPagarItemBinding(@NonNull LinearLayout rootView, @NonNull TextView exibirDataPagar,
      @NonNull TextView exibirNomePagar, @NonNull TextView exibirValorPagar) {
    this.rootView = rootView;
    this.exibirDataPagar = exibirDataPagar;
    this.exibirNomePagar = exibirNomePagar;
    this.exibirValorPagar = exibirValorPagar;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ValorPagarItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ValorPagarItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.valor_pagar_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ValorPagarItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.exibirDataPagar;
      TextView exibirDataPagar = ViewBindings.findChildViewById(rootView, id);
      if (exibirDataPagar == null) {
        break missingId;
      }

      id = R.id.exibirNomePagar;
      TextView exibirNomePagar = ViewBindings.findChildViewById(rootView, id);
      if (exibirNomePagar == null) {
        break missingId;
      }

      id = R.id.exibirValorPagar;
      TextView exibirValorPagar = ViewBindings.findChildViewById(rootView, id);
      if (exibirValorPagar == null) {
        break missingId;
      }

      return new ValorPagarItemBinding((LinearLayout) rootView, exibirDataPagar, exibirNomePagar,
          exibirValorPagar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}