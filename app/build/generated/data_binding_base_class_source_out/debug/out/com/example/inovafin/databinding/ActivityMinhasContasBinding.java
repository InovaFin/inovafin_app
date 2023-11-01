// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMinhasContasBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout btAddContas;

  @NonNull
  public final LinearLayout btExcluirConta;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final RecyclerView listaContas;

  private ActivityMinhasContasBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout btAddContas, @NonNull LinearLayout btExcluirConta,
      @NonNull ImageView icFechar, @NonNull RecyclerView listaContas) {
    this.rootView = rootView;
    this.btAddContas = btAddContas;
    this.btExcluirConta = btExcluirConta;
    this.icFechar = icFechar;
    this.listaContas = listaContas;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMinhasContasBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMinhasContasBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_minhas_contas, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMinhasContasBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAddContas;
      LinearLayout btAddContas = ViewBindings.findChildViewById(rootView, id);
      if (btAddContas == null) {
        break missingId;
      }

      id = R.id.btExcluirConta;
      LinearLayout btExcluirConta = ViewBindings.findChildViewById(rootView, id);
      if (btExcluirConta == null) {
        break missingId;
      }

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.listaContas;
      RecyclerView listaContas = ViewBindings.findChildViewById(rootView, id);
      if (listaContas == null) {
        break missingId;
      }

      return new ActivityMinhasContasBinding((RelativeLayout) rootView, btAddContas, btExcluirConta,
          icFechar, listaContas);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
