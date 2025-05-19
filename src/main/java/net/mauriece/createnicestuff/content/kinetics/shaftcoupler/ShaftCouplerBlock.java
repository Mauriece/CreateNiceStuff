package net.mauriece.createnicestuff.content.kinetics.shaftcoupler;

import com.simibubi.create.content.kinetics.base.RotatedPillarKineticBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShaftCouplerBlock extends RotatedPillarKineticBlock {
    // Add a FACING property (all 6 directions)
    public static final DirectionProperty FACING = DirectionProperty.create("facing");
    // Define the shape for the Y axis as default
    protected static final VoxelShape SHAPE_Y = Block.box(3, 4, 12, 16, 12, 16);

    public ShaftCouplerBlock(Properties properties) {
        super(properties);
        // Set default state with axis and facing
        this.registerDefaultState(this.defaultBlockState()
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(FACING, Direction.UP));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING); // Add facing to the properties
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction face = context.getClickedFace();
        // Set AXIS to match the face's axis, FACING to the face clicked
        return this.defaultBlockState()
                .setValue(AXIS, face.getAxis())
                .setValue(FACING, face);
    }

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        System.out.println("hasShaftTowards called: " + face + " axis: " + state.getValue(AXIS));
        return face.getAxis() == state.getValue(AXIS);
    }

    @Override
    public Direction.Axis getRotationAxis(BlockState state) {
        return state.getValue(AXIS);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        Direction.Axis axis = state.getValue(AXIS);
        if (axis == Direction.Axis.Y) return SHAPE_Y;
        if (axis == Direction.Axis.X) return rotateShape(SHAPE_Y, Direction.Axis.X);
        if (axis == Direction.Axis.Z) return rotateShape(SHAPE_Y, Direction.Axis.Z);
        return SHAPE_Y;
    }

    // Utility to rotate the shape
    private static VoxelShape rotateShape(VoxelShape shape, Direction.Axis axis) {
        // For simple shapes, you can swap coordinates manually.
        // For more complex: use VoxelShapeUtils from Create, or do your own.
        // Here's a basic example for a box:
        // This assumes shape is a single box. For multiple boxes, iterate.
        if (!(shape instanceof net.minecraft.world.phys.shapes.ArrayVoxelShape)) {
            // fallback for simple single-box shape
            net.minecraft.world.phys.AABB box = shape.bounds();
            if (axis == Direction.Axis.X) {
                // Rotate Y→X: (x, y, z) -> (y, x, z)
                return Block.box(
                        box.minY, box.minX, box.minZ,
                        box.maxY, box.maxX, box.maxZ
                );
            } else if (axis == Direction.Axis.Z) {
                // Rotate Y→Z: (x, y, z) -> (z, y, x)
                return Block.box(
                        box.minZ, box.minY, box.minX,
                        box.maxZ, box.maxY, box.maxX
                );
            }
        }
        // For complex shapes, use a more robust rotation utility.
        return shape;
    }

    @Override
    public boolean isOcclusionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
        return true;
    }
}