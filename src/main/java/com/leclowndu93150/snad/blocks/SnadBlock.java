package com.leclowndu93150.snad.blocks;

import com.leclowndu93150.snad.Config;
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
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SnadBlock extends FallingBlock {

    public SnadBlock() {
        super(Block.Properties.copy(Blocks.SAND));
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
        super.onPlace(state, level, pos, oldState, isMoving);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block neighborBlock, BlockPos neighborPos, boolean movedByPiston) {
        if (isFree(level.getBlockState(pos.below()))) {
            level.scheduleTick(pos, this, this.getDelayAfterPlace());
        }
    }

    @Override
    public boolean canSustainPlant(BlockState state, BlockGetter level, BlockPos pos, Direction facing, IPlantable plantable) {
        if (plantable.getPlantType(level, pos).equals(PlantType.DESERT)) {
            return true;
        }
        else if (plantable.getPlantType(level, pos).equals(PlantType.BEACH)) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                boolean isWater = level.getFluidState(pos.relative(direction)).is(FluidTags.WATER);
                boolean isFrostedIce = level.getBlockState(pos.relative(direction)).is(Blocks.FROSTED_ICE);
                if (isWater || isFrostedIce) {
                    return true;
                }
            }
        }
        return super.canSustainPlant(state, level, pos, facing, plantable);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction) {
        return true;
    }

    @Override
    public void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom) {
        this.tick(pState, pLevel, pPos, pRandom);
    }

    @Override
    public void tick(@NotNull BlockState pState, @NotNull ServerLevel pLevel, @NotNull BlockPos pPos, @NotNull RandomSource pRandom) {
        if (isFree(pLevel.getBlockState(pPos.below())) && pPos.getY() >= pLevel.getMinBuildHeight()) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(pLevel, pPos, pState);
            this.falling(fallingBlockEntity);
        }
        else {
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

                    int growthAttemptsLimit = ((nextBlock instanceof BambooStalkBlock ) || (nextBlock instanceof BambooSaplingBlock) || (nextBlock instanceof  KelpPlantBlock)) ? Config.magicNumber2 :Config.magicNumber;

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
