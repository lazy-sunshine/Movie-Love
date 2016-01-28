// Generated code from Butter Knife. Do not modify!
package com.example.dell.movielove;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PopupActivity$$ViewBinder<T extends com.example.dell.movielove.PopupActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492973, "field 'review1'");
    target.review1 = finder.castView(view, 2131492973, "field 'review1'");
    view = finder.findRequiredView(source, 2131492974, "field 'review2'");
    target.review2 = finder.castView(view, 2131492974, "field 'review2'");
    view = finder.findRequiredView(source, 2131492975, "field 'review3'");
    target.review3 = finder.castView(view, 2131492975, "field 'review3'");
    view = finder.findRequiredView(source, 2131492971, "method 'ontick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.ontick();
        }
      });
  }

  @Override public void unbind(T target) {
    target.review1 = null;
    target.review2 = null;
    target.review3 = null;
  }
}
