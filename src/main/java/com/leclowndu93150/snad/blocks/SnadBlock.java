package com.leclowndu93150.snad.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
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

    //List of Allowed Plants (Cactus and Sugar Cane)

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {

        if (plant.getBlock() instanceof CactusBlock) {
            return TriState.TRUE;
        }
        if (plant.getBlock() instanceof SugarCaneBlock) {
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
        this.grow(pState,pLevel,pPos,pRandom);
        boolean canRun;
    }

    public void grow(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        final Block blockAbove = pLevel.getBlockState(pPos.above()).getBlock();
        if ((blockAbove == Blocks.SUGAR_CANE || blockAbove == Blocks.CACTUS) && pLevel.hasSignal(pPos,Direction.NORTH)) {
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

