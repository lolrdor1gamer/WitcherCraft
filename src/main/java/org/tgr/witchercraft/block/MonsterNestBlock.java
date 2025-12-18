package org.tgr.witchercraft.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import org.tgr.witchercraft.registry.ModEntities;

/**
 * Monster nest block that spawns monsters periodically.
 * Can be destroyed with bombs for bonus loot.
 */
public class MonsterNestBlock extends Block {
    
    public static final MapCodec<MonsterNestBlock> CODEC = simpleCodec(MonsterNestBlock::new);
    
    // Nest growth level (0-3), affects spawn rate and difficulty
    public static final IntegerProperty GROWTH = IntegerProperty.create("growth", 0, 3);
    
    private static final VoxelShape SHAPE = Block.box(2, 0, 2, 14, 12, 14);
    
    public enum NestType {
        DROWNER(ModEntities.DROWNED_CORPSE, 3),
        NEKKER(ModEntities.NEKKER, 4),
        GHOUL(ModEntities.GHOUL, 2),
        WRAITH(ModEntities.WRAITH, 1);
        
        private final EntityType<? extends Mob> entityType;
        private final int maxSpawns;
        
        NestType(EntityType<? extends Mob> entityType, int maxSpawns) {
            this.entityType = entityType;
            this.maxSpawns = maxSpawns;
        }
        
        public EntityType<? extends Mob> getEntityType() {
            return entityType;
        }
        
        public int getMaxSpawns() {
            return maxSpawns;
        }
    }
    
    private final NestType nestType;
    
    public MonsterNestBlock(Properties properties) {
        this(properties, NestType.NEKKER);
    }
    
    public MonsterNestBlock(Properties properties, NestType type) {
        super(properties);
        this.nestType = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(GROWTH, 0));
    }
    
    @Override
    protected MapCodec<? extends MonsterNestBlock> codec() {
        return CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GROWTH);
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, 
                                                Player player, BlockHitResult hitResult) {
        if (!level.isClientSide()) {
            // Check if player is holding a bomb
            ItemStack mainHand = player.getMainHandItem();
            ItemStack offHand = player.getOffhandItem();
            
            // Check for Grapeshot or other explosive items
            boolean hasBomb = isExplosiveItem(mainHand) || isExplosiveItem(offHand);
            
            if (hasBomb) {
                destroyNestWithBomb(state, level, pos, player);
                return InteractionResult.SUCCESS;
            }
        }
        
        return InteractionResult.PASS;
    }
    
    private boolean isExplosiveItem(ItemStack stack) {
        // Check for our bombs or vanilla TNT
        if (stack.isEmpty()) return false;
        String itemId = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
        return itemId.contains("bomb") || stack.is(Items.TNT);
    }
    
    private void destroyNestWithBomb(BlockState state, Level level, BlockPos pos, Player player) {
        int growth = state.getValue(GROWTH);
        
        // Play destruction effects
        level.playSound(null, pos, SoundEvents.GENERIC_EXPLODE.value(), SoundSource.BLOCKS, 1.0F, 1.0F);
        
        // Drop loot based on growth level
        dropNestLoot(level, pos, growth);
        
        // Remove the nest
        level.destroyBlock(pos, false);
        
        // Award XP
        if (level instanceof ServerLevel serverLevel) {
            // Could add XP reward here
        }
    }
    
    private void dropNestLoot(Level level, BlockPos pos, int growth) {
        // Drop monster parts based on nest type and growth
        // Higher growth = more loot
        RandomSource random = level.getRandom();
        int lootMultiplier = 1 + growth;
        
        // Drop items at pos - for now just basic drops
        // Would be expanded with proper loot tables
    }
    
    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int growth = state.getValue(GROWTH);
        
        // Chance to grow
        if (growth < 3 && random.nextFloat() < 0.1f) {
            level.setBlock(pos, state.setValue(GROWTH, growth + 1), 3);
        }
        
        // Chance to spawn monsters (higher growth = more spawns)
        int spawnChance = 5 + (growth * 5); // 5%, 10%, 15%, 20%
        if (random.nextInt(100) < spawnChance) {
            spawnMonsterFromNest(level, pos, growth, random);
        }
    }
    
    private void spawnMonsterFromNest(ServerLevel level, BlockPos pos, int growth, RandomSource random) {
        // Count existing nearby monsters of this type
        int existingCount = level.getEntitiesOfClass(
            net.minecraft.world.entity.LivingEntity.class,
            new net.minecraft.world.phys.AABB(pos).inflate(20),
            e -> e.getType() == nestType.getEntityType()
        ).size();
        
        int maxSpawns = nestType.getMaxSpawns() + growth;
        if (existingCount >= maxSpawns) {
            return;
        }
        
        // Find spawn position
        BlockPos spawnPos = pos.offset(
            random.nextIntBetweenInclusive(-3, 3),
            0,
            random.nextIntBetweenInclusive(-3, 3)
        );
        
        // Find surface
        while (!level.getBlockState(spawnPos).isAir() && spawnPos.getY() < pos.getY() + 5) {
            spawnPos = spawnPos.above();
        }
        
        if (!level.getBlockState(spawnPos).isAir() || !level.getBlockState(spawnPos.below()).isSolid()) {
            return;
        }
        
        // Spawn monster
        Mob entity = nestType.getEntityType().create(level, EntitySpawnReason.SPAWNER);
        if (entity != null) {
            entity.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);
            level.addFreshEntity(entity);
        }
    }
    
    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moved) {
        // Nest was destroyed - spawn angry monsters
        RandomSource random = level.getRandom();
        int growth = state.getValue(GROWTH);
        
        // Spawn some monsters when nest is destroyed
        for (int i = 0; i < 1 + growth; i++) {
            spawnMonsterFromNest(level, pos, growth, random);
        }
        super.affectNeighborsAfterRemoval(state, level, pos, moved);
    }
    
    public NestType getNestType() {
        return nestType;
    }
}
