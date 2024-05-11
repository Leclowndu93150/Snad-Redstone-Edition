package com.leclowndu93150.snad.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnadBlock extends FallingBlock {

    public SnadBlock(Properties pProperties) {
        super(pProperties);
    }

    public SnadBlock() {
        super(Block.Properties.copy(Blocks.SAND));
    }


    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter world, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable.getPlantType(world,pos) == PlantType.BEACH) {
            return true;
        }
        return false;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return super.canConnectRedstone(state, level, pos, direction);
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void tick(@NotNull BlockState pState, ServerLevel pLevel, BlockPos pPos, @NotNull RandomSource pRandom) {
        final Block blockAbove = pLevel.getBlockState(pPos.above()).getBlock();
        for(Direction direction : Direction.values()) {
            BlockPos offsetPos = pPos.relative(direction);
            BlockState blockState = pLevel.getBlockState(offsetPos);
            if (blockAbove instanceof IPlantable && (blockState.hasProperty(BlockStateProperties.POWER)) && (blockState.getValue(BlockStateProperties.POWER) > 0)) {
                boolean isSameBlockType = true;
                int height = 1;
                while (isSameBlockType) {
                    if (pPos.above(height).getY() < pLevel.getMaxBuildHeight()) {
                        final Block nextBlock = pLevel.getBlockState(pPos.above(height)).getBlock();

                        if (nextBlock.getClass() == blockAbove.getClass()) {
                            for (int growthAttempts = 0; growthAttempts < 8; growthAttempts++) {
                                nextBlock.randomTick(pLevel.getBlockState(pPos.above(height)), pLevel, pPos.above(height), pRandom);
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
}

