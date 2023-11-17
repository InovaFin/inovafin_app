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

public final class ValorGuardadoItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView exibirNomeGuardado;

  @NonNull
  public final TextView exibirValorGuardado;

  private ValorGuardadoItemBinding(@NonNull LinearLayout rootView,
      @NonNull TextView exibirNomeGuardado, @NonNull TextView exibirValorGuardado) {
    this.rootView = rootView;
    this.exibirNomeGuardado = exibirNomeGuardado;
    this.exibirValorGuardado = exibirValorGuardado;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ValorGuardadoItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ValorGuardadoItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.valor_guardado_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ValorGuardadoItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.exibirNomeGuardado;
      TextView exibirNomeGuardado = ViewBindings.findChildViewById(rootView, id);
      if (exibirNomeGuardado == null) {
        break missingId;
      }

      id = R.id.exibirValorGuardado;
      TextView exibirValorGuardado = ViewBindings.findChildViewById(rootView, id);
      if (exibirValorGuardado == null) {
        break missingId;
      }

      return new ValorGuardadoItemBinding((LinearLayout) rootView, exibirNomeGuardado,
          exibirValorGuardado);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}