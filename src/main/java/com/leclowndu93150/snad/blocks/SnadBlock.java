package com.leclowndu93150.snad.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.IPlantable;
import net.neoforged.neoforge.common.PlantType;
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
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable.getPlantType(level, pos).equals(PlantType.DESERT)) {
            return true;
        } else if (plantable.getPlantType(level, pos).equals(PlantType.BEACH)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                boolean isWater = level.getFluidState(pos.relative(direction)).is(FluidTags.WATER);
                boolean isFrostedIce = level.getBlockState(pos.relative(direction)).is(Blocks.FROSTED_ICE);
                if (!isWater && !isFrostedIce) {
                    continue;
                }
                return true;
            }
        }
        return super.canSustainPlant(state, level, pos, facing, plantable);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return super.canConnectRedstone(state, level, pos, direction);
    }

    @Override
    public void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.grow(pState,pLevel,pPos,pRandom);
        boolean canRun;
    }

    public void grow(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        final Block blockAbove = pLevel.getBlockState(pPos.above()).getBlock();
        if () {
            boolean isSameBlockType = true;
            int height = 1;
            while (isSameBlockType) {
                if (pPos.above(height).getY() < pLevel.getMaxBuildHeight()) {
                    final Block nextBlock = pLevel.getBlockState(pPos.above(height)).getBlock();

                    if (nextBlock.getClass() == blockAbove.getClass()) {
                        for (int growthAttempts = 0; growthAttempts < 8; growthAttempts++) {
                            pLevel.getBlockState(pPos.above(height)).randomTick(pLevel, pPos.above(height), pRandom);
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

