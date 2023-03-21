package com.opaa.trackrunnerultimatum.loadingdrawable.render;

import android.content.Context;
import android.util.SparseArray;

import java.lang.reflect.Constructor;

import com.opaa.trackrunnerultimatum.loadingdrawable.render.animal.FishLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.animal.GhostsEyeLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.jump.CollisionLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.jump.DanceLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.jump.GuardLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.jump.SwapLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.rotate.GearLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.rotate.LevelLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.rotate.MaterialLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.circle.rotate.WhorlLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.drivingThings.RoadWithArrowsLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.food.DonutLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.goods.BalloonLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.goods.WaterBottleLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.scenery.DayNightLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.scenery.ElectricFanLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.shapechange.CircleBroodLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.shapechange.CoolWaitLoadingRenderer;
import com.opaa.trackrunnerultimatum.loadingdrawable.render.drivingThings.SpeedometerLoadingRenderer;

public final class LoadingRendererFactory {
    private static final SparseArray<Class<? extends LoadingRenderer>> LOADING_RENDERERS = new SparseArray<>();

    static {
        //circle rotate
        LOADING_RENDERERS.put(0, MaterialLoadingRenderer.class);
        LOADING_RENDERERS.put(1, LevelLoadingRenderer.class);
        LOADING_RENDERERS.put(2, WhorlLoadingRenderer.class);
        LOADING_RENDERERS.put(3, GearLoadingRenderer.class);
        //circle jump
        LOADING_RENDERERS.put(4, SwapLoadingRenderer.class);
        LOADING_RENDERERS.put(5, GuardLoadingRenderer.class);
        LOADING_RENDERERS.put(6, DanceLoadingRenderer.class);
        LOADING_RENDERERS.put(7, CollisionLoadingRenderer.class);
        //scenery
        LOADING_RENDERERS.put(8, DayNightLoadingRenderer.class);
        LOADING_RENDERERS.put(9, ElectricFanLoadingRenderer.class);
        //animal
        LOADING_RENDERERS.put(10, FishLoadingRenderer.class);
        LOADING_RENDERERS.put(11, GhostsEyeLoadingRenderer.class);
        //goods
        LOADING_RENDERERS.put(12, BalloonLoadingRenderer.class);
        LOADING_RENDERERS.put(13, WaterBottleLoadingRenderer.class);
        //shape change
        LOADING_RENDERERS.put(14, CircleBroodLoadingRenderer.class);
        LOADING_RENDERERS.put(15, CoolWaitLoadingRenderer.class);
        //Food
        LOADING_RENDERERS.put(16, DonutLoadingRenderer.class);
        //Speedometer
        LOADING_RENDERERS.put(17, SpeedometerLoadingRenderer.class);
//        Road with arrows
        LOADING_RENDERERS.put(18, RoadWithArrowsLoadingRenderer.class);
    }

    private LoadingRendererFactory() {
    }

    public static LoadingRenderer createLoadingRenderer(Context context, int loadingRendererId) throws Exception {
        Class<?> loadingRendererClazz = LOADING_RENDERERS.get(loadingRendererId);
        Constructor<?>[] constructors = loadingRendererClazz.getDeclaredConstructors();
        for (Constructor<?> constructor : constructors) {
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            if (parameterTypes != null
                    && parameterTypes.length == 1
                    && parameterTypes[0].equals(Context.class)) {
                constructor.setAccessible(true);
                return (LoadingRenderer) constructor.newInstance(context);
            }
        }

        throw new InstantiationException();
    }
}
