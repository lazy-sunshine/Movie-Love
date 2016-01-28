// Generated code from Butter Knife. Do not modify!
package com.example.dell.movielove;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class PhotoAlbumAdapter$ViewHolder$$ViewBinder<T extends com.example.dell.movielove.PhotoAlbumAdapter.ViewHolder> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493001, "field 'img'");
    target.img = finder.castView(view, 2131493001, "field 'img'");
  }

  @Override public void unbind(T target) {
    target.img = null;
  }
}
