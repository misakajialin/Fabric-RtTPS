package xyz.lisbammisakait.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.entity.FakePlayer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import xyz.lisbammisakait.RelightTheThreePointStrategy;
import xyz.lisbammisakait.compoennt.RtTPSComponents;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanghaitulongfuItem extends  RtTPSSwordItem {
    public static final int HITNUMBER = 5;
    public final int MAX_HEALTH = 100;
    public CanghaitulongfuItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        boostMaxHealth((PlayerEntity) entity, MAX_HEALTH);
    }

    @Override
    public void specialAbility(ItemStack stack, LivingEntity target,LivingEntity attacker) {
        PlayerEntity player =(PlayerEntity)attacker;
        //生成一个猪
//        PigEntity pig =new PigEntity(EntityType.PIG,player.getServer().getOverworld());
//        pig.refreshPositionAndAngles(attacker.getX(), attacker.getY(), attacker.getZ(), 0.0F, 0.0F);
//        attacker.getServer().getOverworld().spawnNewEntityAndPassengers(pig);
//        attacker.getServer().getOverworld().sendEntityStatus(pig, (byte) 12);
        //增加攻击次数
        addHitNumber(player, target, stack);
        player.sendMessage(Text.of("攻击次数: " + stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0)), true);
        if(stack.getOrDefault(RtTPSComponents.HITNUMBER_TYPE, 0) >= HITNUMBER){
            ItemStack caoCaoBSkill = player.getInventory().getStack(7);
            caoCaoBSkill.set(RtTPSComponents.LIMITEDSKILLEXHAUSTED_TYPE, false);
            //改变纹理为技能图标
            caoCaoBSkill.set(DataComponentTypes.CUSTOM_MODEL_DATA,new CustomModelDataComponent(List.of(), List.of(true),List.of(),List.of()));
        }
    }
}

