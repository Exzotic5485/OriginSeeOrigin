package net.exzotic.phantomsseephantoms.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.registry.ModComponents;
import net.exzotic.phantomsseephantoms.PhantomsSeePhantoms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    public void phantomseephantoms$isInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        Entity entity = (Entity)(Object)this;

        if(entity instanceof PlayerEntity) {

            Identifier layerId = new Identifier("origins:origin");
            Identifier originId = new Identifier("origins:phantom");

            ArrayList<Identifier> layers = OriginLayers.getLayers().stream().map((OriginLayer::getIdentifier)).collect(Collectors.toCollection(ArrayList::new));

            if(!layers.contains(layerId)) return;

            OriginLayer layer = OriginLayers.getLayer(layerId);

            OriginComponent component = ModComponents.ORIGIN.get(entity);
            Origin origin = component.getOrigin(layer);

            OriginComponent tComponent = ModComponents.ORIGIN.get(player);
            Origin tOrigin = tComponent.getOrigin(layer);

            if(tOrigin != null && origin != null){
                Identifier entityOrigin = origin.getIdentifier();
                Identifier playerOrigin = tOrigin.getIdentifier();

                if (entityOrigin.equals(originId) && playerOrigin.equals(originId)) {
                    cir.setReturnValue(false);
                    return;
                }
            }

            if(PhantomsSeePhantoms.SEE_INVISIBLE.isActive(entity) && PhantomsSeePhantoms.SEE_INVISIBLE.isActive(player)){
                cir.setReturnValue(false);
            }
        }
    }
}
