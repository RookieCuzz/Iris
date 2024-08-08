package com.volmit.iris.core.link;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.*;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.structure.Mirror;
import org.bukkit.block.structure.StructureRotation;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import org.bukkit.Material;
import org.bukkit.SoundGroup;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.bukkit.block.data.type.PistonHead;
import org.bukkit.block.BlockSupport;
public class VirtualBlockData implements BlockData {

    private Material type;

    public VirtualBlockData(Material material) {
        this.type = material;
    }

    @NotNull
    @Override
    public Material getMaterial() {
        return this.type;
    }

    @NotNull
    @Override
    public String getAsString() {
        // Assuming we return the material type as string for simplicity
        return "minecraft:" + this.type.name().toLowerCase();
    }

    @NotNull
    @Override
    public String getAsString(boolean hideUnspecified) {
        // You could extend this to include more properties if needed
        return getAsString();
    }

    @NotNull
    @Override
    public BlockData merge(@NotNull BlockData data) {
        // Assuming we just return a clone of the current data for simplicity
        return this.clone();
    }

    @Override
    public boolean matches(@Nullable BlockData data) {
        // Check if the type matches
        if (data == null) return false;
        return this.type == data.getMaterial();
    }

    @NotNull
    @Override
    public BlockData clone() {
        return new VirtualBlockData(this.type);
    }

    @NotNull
    @Override
    public SoundGroup getSoundGroup() {
        // Assuming a default sound group, or return a specific one based on `type`
        return type.createBlockData().getSoundGroup();
    }

    @Override
    public int getLightEmission() {
        return type.createBlockData().getLightEmission();
    }

    @Override
    public boolean isOccluding() {
        return type.createBlockData().isOccluding();
    }

    @Override
    public boolean requiresCorrectToolForDrops() {
        return type.createBlockData().requiresCorrectToolForDrops();
    }

    @Override
    public boolean isPreferredTool(@NotNull ItemStack tool) {
        return type.createBlockData().isPreferredTool(tool);
    }

    @NotNull
    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return type.createBlockData().getPistonMoveReaction();
    }

    @Override
    public boolean isSupported(@NotNull Block block) {
        return type.createBlockData().isSupported(block);
    }

    @Override
    public boolean isSupported(@NotNull Location location) {
        return type.createBlockData().isSupported(location);
    }

    @Override
    public boolean isFaceSturdy(@NotNull BlockFace face, @NotNull BlockSupport support) {
        return type.createBlockData().isFaceSturdy(face, support);
    }

    @NotNull
    @Override
    public Material getPlacementMaterial() {
        return this.type;
    }

    @Override
    public void rotate(@NotNull StructureRotation rotation) {
        // Rotating the underlying type's BlockData
        type.createBlockData().rotate(rotation);
    }

    @Override
    public void mirror(@NotNull Mirror mirror) {
        // Mirroring the underlying type's BlockData
        type.createBlockData().mirror(mirror);
    }

    @NotNull
    @Override
    public BlockState createBlockState() {
        // Assuming we can use the default block state creation
        return type.createBlockData().createBlockState();
    }
}
