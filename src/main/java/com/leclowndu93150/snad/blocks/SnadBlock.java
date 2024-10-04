package com.leclowndu93150.snad.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnadBlock extends FallingBlock {

    public SnadBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return null;
    }

    @Override
    protected void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (isFree(level.getBlockState(pos.below()))) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
    }

    @Override
    public @NotNull TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        if (level.getBlockState(soilPosition.relative(Direction.DOWN)).isAir() || level.getBlockState(soilPosition.above()).is(Blocks.KELP_PLANT)) {
            return TriState.FALSE;
        }
        if (plant.getBlock() instanceof CactusBlock || plant.getBlock() instanceof SugarCaneBlock || plant.getBlock() instanceof BambooSaplingBlock) {
            if (plant.getBlock() instanceof BambooSaplingBlock || plant.getBlock() instanceof CactusBlock) {
                return TriState.TRUE;
            }

            for (Direction direction : Direction.Plane.HORIZONTAL) {
                BlockPos neighborPos = soilPosition.relative(direction);
                boolean isWater = level.getFluidState(neighborPos).is(FluidTags.WATER);
                boolean isFrostedIce = level.getBlockState(neighborPos).is(Blocks.FROSTED_ICE);
                if (isWater || isFrostedIce) {
                    return TriState.TRUE;
                }
            }
        }
        return super.canSustainPlant(state, level, soilPosition, facing, plant);
    }

    @Override
    public boolean canConnectRedstone(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(pLevel, pPos, pState);
            this.falling(fallingBlockEntity);
        } else {
            this.grow(pState, pLevel, pPos, pRandom);
        }
    }

    public void grow(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        Block blockAbove = pLevel.getBlockState(pPos.above()).getBlock();

        if (pLevel.hasNeighborSignal(pPos)) {
            boolean isSameBlockType = true;
            int height = 1;

            while (isSameBlockType) {
                BlockPos currentPos = pPos.above(height);
                if (currentPos.getY() < pLevel.getMaxBuildHeight()) {
                    Block nextBlock = pLevel.getBlockState(currentPos).getBlock();

                    int growthAttemptsLimit = (nextBlock instanceof KelpBlock) ? 1 : 6;

                    if (nextBlock.getClass() == blockAbove.getClass()) {
                        for (int growthAttempts = 0; growthAttempts < growthAttemptsLimit; growthAttempts++) {
                            pLevel.getBlockState(currentPos).randomTick(pLevel, currentPos, pRandom);
                        }
                        height++;
                    } else {
                        isSameBlockType = false;
                    }
                } else {
                    isSameBlockType = false;
                }
            }
        }
    }
}
