package zh.qiushui.mod.zakuro.modules.mixin.fixZombieReinforcement;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

import java.util.Objects;
import java.util.UUID;

@Mixin(ZombieEntity.class)
public abstract class MixinZombieEntity extends HostileEntity {
    @Unique
    private UUID reinforcementCallerChargeModifierId = null;

    protected MixinZombieEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    /**
     * @author QiuShui1012
     * @reason Fix the Zombie Reinforcement, need to change a lot of things.
     */
    @Overwrite
    public boolean damage(DamageSource source, float amount) {
        if (!super.damage(source, amount)) {
            return false;
        } else if (this.getWorld() instanceof ServerWorld serverWorld) {
            LivingEntity livingEntity = this.getTarget();
            if (livingEntity == null && source.getAttacker() instanceof LivingEntity) {
                livingEntity = (LivingEntity)source.getAttacker();
            }

            if (livingEntity != null
                    && this.getWorld().getDifficulty() == Difficulty.HARD
                    && (double)this.random.nextFloat() < this.getAttributeValue(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                    && this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING)) {
                int i = MathHelper.floor(this.getX());
                int j = MathHelper.floor(this.getY());
                int k = MathHelper.floor(this.getZ());
                EntityType<? extends ZombieEntity> entityType = this.getType();
                ZombieEntity zombieEntity = entityType.create(this.getWorld());
                if (zombieEntity == null) {
                    return true;
                }

                for (int l = 0; l < 50; l++) {
                    int m = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int n = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    int o = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
                    BlockPos blockPos = new BlockPos(m, n, o);
                    if (SpawnHelper.canSpawn(SpawnRestriction.getLocation(entityType), this.getWorld(), blockPos, entityType)
                            && SpawnRestriction.canSpawn(entityType, serverWorld, SpawnReason.REINFORCEMENT, blockPos, this.getWorld().random)) {
                        zombieEntity.setPosition(m, n, o);
                        if (!this.getWorld().isPlayerInRange(m, n, o, 7.0)
                                && this.getWorld().doesNotIntersectEntities(zombieEntity)
                                && this.getWorld().isSpaceEmpty(zombieEntity)
                                && !this.getWorld().containsFluid(zombieEntity.getBoundingBox())) {
                            zombieEntity.setTarget(livingEntity);
                            zombieEntity.initialize(serverWorld, this.getWorld().getLocalDifficulty(zombieEntity.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
                            serverWorld.spawnEntityAndPassengers(zombieEntity);
                            EntityAttributeModifier entityAttributeModifier;
                            if (reinforcementCallerChargeModifierId != null) {
                                EntityAttributeInstance entityAttributeInstance = this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);
                                EntityAttributeModifier entityAttributeModifier1 = entityAttributeInstance.getModifier(reinforcementCallerChargeModifierId);
                                double d = entityAttributeModifier1 != null ? entityAttributeModifier1.getValue() : 0.0;
                                entityAttributeInstance.removeModifier(reinforcementCallerChargeModifierId);
                                entityAttributeModifier = new EntityAttributeModifier("Zombie reinforcement caller charge", d - 0.05, EntityAttributeModifier.Operation.ADDITION);
                            } else {
                                entityAttributeModifier = new EntityAttributeModifier("Zombie reinforcement caller charge", -0.05F, EntityAttributeModifier.Operation.ADDITION);
                            }
                            this.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS).addPersistentModifier(entityAttributeModifier);
                            reinforcementCallerChargeModifierId = entityAttributeModifier.getId();

                            zombieEntity.getAttributeInstance(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                                    .addPersistentModifier(new EntityAttributeModifier("Zombie reinforcement callee charge", -0.05F, EntityAttributeModifier.Operation.ADDITION));
                            break;
                        }
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public EntityType<? extends ZombieEntity> getType() {
        return (EntityType<? extends ZombieEntity>) super.getType();
    }
}
