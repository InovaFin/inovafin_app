// Generated by view binder compiler. Do not edit!
package com.example.inovafin.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityContaPagarBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout btAdicionar;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final RecyclerView listaPagar;

  @NonNull
  public final TextView valorTotalPagar;

  private ActivityContaPagarBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout btAdicionar, @NonNull ImageView icFechar,
      @NonNull RecyclerView listaPagar, @NonNull TextView valorTotalPagar) {
    this.rootView = rootView;
    this.btAdicionar = btAdicionar;
    this.icFechar = icFechar;
    this.listaPagar = listaPagar;
    this.valorTotalPagar = valorTotalPagar;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityContaPagarBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityContaPagarBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_conta_pagar, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityContaPagarBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAdicionar;
      LinearLayout btAdicionar = ViewBindings.findChildViewById(rootView, id);
      if (btAdicionar == null) {
        break missingId;
      }

      id = R.id.icFechar;
      ImageView icFechar = ViewBindings.findChildViewById(rootView, id);
      if (icFechar == null) {
        break missingId;
      }

      id = R.id.listaPagar;
      RecyclerView listaPagar = ViewBindings.findChildViewById(rootView, id);
      if (listaPagar == null) {
        break missingId;
      }

      id = R.id.valorTotalPagar;
      TextView valorTotalPagar = ViewBindings.findChildViewById(rootView, id);
      if (valorTotalPagar == null) {
        break missingId;
      }

      return new ActivityContaPagarBinding((RelativeLayout) rootView, btAdicionar, icFechar,
          listaPagar, valorTotalPagar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
