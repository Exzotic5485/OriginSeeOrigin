package net.exzotic.phantomsseephantoms.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.power.OriginsPowerTypes;
import io.github.apace100.origins.registry.ModComponents;
import net.exzotic.phantomsseephantoms.PhantomsSeePhantoms;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    public void phantomseephantoms$isInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> cir){
        Entity entity = (Entity)(Object)this;
        if(entity instanceof PlayerEntity) {

            Identifier layerId = new Identifier("origins:origin");
            Identifier originId = new Identifier("origins:phantom");

            Collection<OriginLayer> layers = OriginLayers.getLayers();

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
