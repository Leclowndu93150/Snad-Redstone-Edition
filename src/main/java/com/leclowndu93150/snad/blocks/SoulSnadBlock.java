package com.leclowndu93150.snad.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.NetherWartBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.util.TriState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SoulSnadBlock extends FallingBlock {

    public SoulSnadBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return null;
    }

    @Override
    public TriState canSustainPlant(BlockState state, BlockGetter level, BlockPos soilPosition, Direction facing, BlockState plant) {
        if (plant.getBlock() instanceof NetherWartBlock) {
            return TriState.TRUE;
        }
        return TriState.FALSE;
    }

    @Override
    public boolean canConnectRedstone(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @Nullable Direction direction) {
        return true;
    }


    @Override
    public void randomTick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        this.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void tick(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        final Block blockAbove = pLevel.getBlockState(pPos.above()).getBlock();
        if (blockAbove instanceof NetherWartBlock && pLevel.hasSignal(pPos,Direction.NORTH)) {
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

