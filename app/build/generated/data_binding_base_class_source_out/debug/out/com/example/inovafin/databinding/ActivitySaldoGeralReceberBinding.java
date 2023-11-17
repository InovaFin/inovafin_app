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
import com.airbnb.lottie.LottieAnimationView;
import com.example.inovafin.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySaldoGeralReceberBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final LinearLayout btAdicionar;

  @NonNull
  public final LottieAnimationView btAnimacao;

  @NonNull
  public final LinearLayout btText;

  @NonNull
  public final ImageView icFechar;

  @NonNull
  public final RecyclerView listaReceber;

  private ActivitySaldoGeralReceberBinding(@NonNull RelativeLayout rootView,
      @NonNull LinearLayout btAdicionar, @NonNull LottieAnimationView btAnimacao,
      @NonNull LinearLayout btText, @NonNull ImageView icFechar,
      @NonNull RecyclerView listaReceber) {
    this.rootView = rootView;
    this.btAdicionar = btAdicionar;
    this.btAnimacao = btAnimacao;
    this.btText = btText;
    this.icFechar = icFechar;
    this.listaReceber = listaReceber;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySaldoGeralReceberBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySaldoGeralReceberBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_saldo_geral_receber, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySaldoGeralReceberBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btAdicionar;
      LinearLayout btAdicionar = ViewBindings.findChildViewById(rootView, id);
      if (btAdicionar == null) {
        break missingId;
      }

      id = R.id.btAnimacao;
      LottieAnimationView btAnimacao = ViewBindings.findChildViewById(rootView, id);
      if (btAnimacao == null) {
        break missingId;
      }

      id = R.id.btText;
      LinearLayout btText = ViewBindings.findChildViewById(rootView, id);
      if (btText == null) {
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

      return new ActivitySaldoGeralReceberBinding((RelativeLayout) rootView, btAdicionar,
          btAnimacao, btText, icFechar, listaReceber);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
