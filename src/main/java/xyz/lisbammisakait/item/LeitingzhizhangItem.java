package xyz.lisbammisakait.item;

import net.minecraft.block.Portal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

public class LeitingzhizhangItem extends  RtTPSSwordItem {
    private final int SPEED_DURATION = 5;
    private final int SPEED_AMPLIFIER = 1;
    public LeitingzhizhangItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker){
        stack.set(RtTPSComponents.HITNUMBER_TYPE,stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE,0)+1);
        if(stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0) == 3){
            attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, SPEED_DURATION*20, SPEED_AMPLIFIER));
            stack.set(RtTPSComponents.HITNUMBER_TYPE,0);
        }
        return super.postHit(stack, target, attacker);
    }



    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {


        return ActionResult.SUCCESS;
    }
}
