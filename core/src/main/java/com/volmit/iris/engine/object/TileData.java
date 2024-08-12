/*
 * Iris is a World Generator for Minecraft Bukkit Servers
 * Copyright (c) 2022 Arcane Arts (Volmit Software)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.volmit.iris.engine.object;

import com.volmit.iris.Iris;
import com.volmit.iris.util.collection.KList;
import com.volmit.iris.util.nbt.tag.CompoundTag;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.TileState;
import org.bukkit.block.data.BlockData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@SuppressWarnings("ALL")
public interface TileData<T extends TileState> extends Cloneable {

    static final KList<TileData<? extends TileState>> registry = setup();

    static KList<TileData<? extends TileState>> setup() {
        KList<TileData<? extends TileState>> registry = new KList<>();

        registry.add(new TileSign());
        registry.add(new TileSpawner());
        registry.add(new TileBanner());

        return registry;
    }

    static TileData<? extends TileState> read(DataInputStream s) throws IOException {
        try {
            int id = s.readShort();
            @SuppressWarnings("unchecked") TileData<? extends TileState> d = registry.get(id).getClass().getConstructor().newInstance();
            d.fromBinary(s);
            return d;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new IOException("Failed to create TileData instance due to missing type registrar!");
        }
    }

    static boolean setTileState(Block block, TileData<? extends TileState> data) {
        if (block.getState() instanceof TileState && data.isApplicable(block.getBlockData()))
            return data.toBukkitTry(block.getState());
        return false;
    }


    //may need to be supplemented.
    static boolean hasTileData(Block block){
        if (block == null) {
            return false; // Null check
        }

        Material material = block.getType();

        // 判断该方块是否具有Tile Data
        switch (material) {
            case CHEST:
            case TRAPPED_CHEST:
            case HOPPER:
            case DISPENSER:
            case DROPPER:
            case FURNACE:
            case BREWING_STAND:
            case COMMAND_BLOCK:
            case END_PORTAL_FRAME:
            case STRUCTURE_BLOCK:
            case ITEM_FRAME:
            case FLOWER_POT:
            case LECTERN:
            case SMOKER:
            case BLAST_FURNACE:
            case ENDER_CHEST:
            case BEACON:
            case PAINTING:
            case ARMOR_STAND:
            case OAK_SIGN:
            case OAK_WALL_SIGN:
            case SPRUCE_SIGN:
            case SPRUCE_WALL_SIGN:
            case BIRCH_SIGN:
            case BIRCH_WALL_SIGN:
            case ACACIA_SIGN:
            case ACACIA_WALL_SIGN:
            case JUNGLE_SIGN:
            case JUNGLE_WALL_SIGN:
            case DARK_OAK_SIGN:
            case DARK_OAK_WALL_SIGN:
            case CRIMSON_SIGN:
            case CRIMSON_WALL_SIGN:
            case WARPED_SIGN:
            case WARPED_WALL_SIGN:
            case MANGROVE_SIGN:
            case MANGROVE_WALL_SIGN:
            case BAMBOO_SIGN:
            case BAMBOO_WALL_SIGN:
            case OAK_HANGING_SIGN:
            case SPRUCE_HANGING_SIGN:
            case BIRCH_HANGING_SIGN:
            case ACACIA_HANGING_SIGN:
            case JUNGLE_HANGING_SIGN:
            case DARK_OAK_HANGING_SIGN:
            case CRIMSON_HANGING_SIGN:
            case WARPED_HANGING_SIGN:
            case MANGROVE_HANGING_SIGN:
            case BAMBOO_HANGING_SIGN:
            case BARREL:
                return true;
            default:
                return false; // 其他方块不具有Tile Data
        }

    }
    static TileData<? extends TileState> getTileState(Block block) {
        for (TileData<? extends TileState> i : registry) {
            BlockData data = block.getBlockData();

            if (i.isApplicable(data)) {
                try {
                    @SuppressWarnings("unchecked") TileData<? extends TileState> s = i.getClass().getConstructor().newInstance();
                    s.fromBukkitTry(block.getState());
                    return s;
                } catch (Throwable e) {
                    Iris.reportError(e);
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    String getTileId();

    boolean isApplicable(BlockData data);

    void toBukkit(T t);

    void fromBukkit(T t);

    default boolean toBukkitTry(BlockState t) {
        try {
            //noinspection unchecked
            toBukkit((T) t);
            t.update();
            return true;
        } catch (Throwable e) {
            Iris.reportError(e);

        }

        return false;
    }

    default boolean fromBukkitTry(BlockState t) {
        try {
            //noinspection unchecked
            fromBukkit((T) t);
            return true;
        } catch (Throwable e) {
            Iris.reportError(e);

        }

        return false;
    }

    CompoundTag toNBT(CompoundTag parent);

    void toBinary(DataOutputStream out) throws IOException;

    void fromBinary(DataInputStream in) throws IOException;

    TileData<T> clone();
}
