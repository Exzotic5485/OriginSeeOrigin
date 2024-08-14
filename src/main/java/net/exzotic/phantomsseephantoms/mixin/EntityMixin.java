package net.exzotic.phantomsseephantoms.mixin;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.origin.Origin;
import io.github.apace100.origins.origin.OriginLayer;
import io.github.apace100.origins.origin.OriginLayers;
import io.github.apace100.origins.registry.ModComponents;
import net.exzotic.phantomsseephantoms.PhantomsSeePhantoms;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Unique
    private static final Identifier PHANTOM_ORIGIN_ID = Identifier.of(Origins.MODID, "phantom");

    @Inject(method = "isInvisibleTo", at = @At("HEAD"), cancellable = true)
    public void phantomseephantoms$isInvisibleTo(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if((Entity) (Object) this instanceof PlayerEntity playerEntity) {
            OriginLayer layer = OriginLayers.getNullableLayer(Identifier.of(Origins.MODID, "origin"));

            if (layer == null) {
                return;
            }

            OriginComponent component = ModComponents.ORIGIN.get(playerEntity);
            Origin origin = component.getOrigin(layer);

            OriginComponent tComponent = ModComponents.ORIGIN.get(player);
            Origin tOrigin = tComponent.getOrigin(layer);

            if (tOrigin != null && origin != null) {
                Identifier entityOrigin = origin.getIdentifier();
                Identifier playerOrigin = tOrigin.getIdentifier();

                if (entityOrigin.equals(PHANTOM_ORIGIN_ID) && playerOrigin.equals(PHANTOM_ORIGIN_ID)) {
                    cir.setReturnValue(false);
                    return;
                }
            }

            if (PhantomsSeePhantoms.SEE_INVISIBLE.isActive(playerEntity) && PhantomsSeePhantoms.SEE_INVISIBLE.isActive(player)) {
                cir.setReturnValue(false);
            }
        }
    }
}
