package net.exzotic.phantomsseephantoms;


import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhantomsSeePhantoms implements ModInitializer {


    public static final String MODID = "phantomsseephantoms";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    public static final PowerType<Power> SEE_INVISIBLE = new PowerTypeReference<>(new Identifier(MODID, "see_invisible"));

    @Override
    public void onInitialize() {

        LOGGER.info("Phantoms now see phantoms!");


    }
}