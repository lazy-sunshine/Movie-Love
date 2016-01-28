package com.example.dell.movielove;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by DeLL on 12/9/2015.
 */
public class AnimationsUtils {

    public static void animate(PhotoAlbumAdapter.ViewHolder holder) {
        YoYo.with(Techniques.ZoomIn)
                .duration(500)
                .playOn(holder.itemView);
    }


   }
