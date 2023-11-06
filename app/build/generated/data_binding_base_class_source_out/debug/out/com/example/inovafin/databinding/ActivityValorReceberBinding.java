// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityValorReceberBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout btAdicionar;

  @NonNull
  public final LinearLayout btExcluir;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final RecyclerView listaReceber;

  private ActivityValorReceberBinding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout btAdicionar, @NonNull LinearLayout btExcluir,
      @NonNull ImageView icFechar, @NonNull RecyclerView listaReceber) {
    this.rootView = rootView;
    this.btAdicionar = btAdicionar;
    this.btExcluir = btExcluir;
    this.icFechar = icFechar;
    this.listaReceber = listaReceber;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityValorReceberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityValorReceberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_valor_receber, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityValorReceberBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAdicionar;
      LinearLayout btAdicionar = ViewBindings.findChildViewById(rootView, id);
      if (btAdicionar == null) {
        break missingId;
      }

      id = R.id.btExcluir;
      LinearLayout btExcluir = ViewBindings.findChildViewById(rootView, id);
      if (btExcluir == null) {
        break missingId;
      }

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.listaReceber;
      RecyclerView listaReceber = ViewBindings.findChildViewById(rootView, id);
      if (listaReceber == null) {
        break missingId;
      }

      return new ActivityValorReceberBinding((LinearLayout) rootView, btAdicionar, btExcluir,
          icFechar, listaReceber);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
